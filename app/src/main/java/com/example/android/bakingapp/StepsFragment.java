package com.example.android.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Adapters.DetailsAdapter;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Steps;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.MainActivity.INDEX_RECIPE;
import static com.example.android.bakingapp.MainActivity.SELECTED_INDEX;
import static com.example.android.bakingapp.MainActivity.INDEX_STEPS;


public class StepsFragment extends Fragment {
    ArrayList<Recipe> recipeArrayList;
    String mRecipeName;
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    private ArrayList<Steps> stepsArrayList = new ArrayList<>();
    private int selectedIndex;
    private DetailsAdapter.ListClickListener mListClickListener;

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView stepsTextView;
        mListClickListener = (RecipeDetailActivity) getActivity();
        recipeArrayList = new ArrayList<>();

        if (savedInstanceState != null) {
            stepsArrayList = savedInstanceState.getParcelableArrayList(INDEX_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            mRecipeName = savedInstanceState.getString("Title");

        } else {
            assert getArguments() != null;
            stepsArrayList = getArguments().getParcelableArrayList(INDEX_STEPS);
            if (stepsArrayList != null) {
                stepsArrayList = getArguments().getParcelableArrayList(INDEX_STEPS);
                selectedIndex = getArguments().getInt(SELECTED_INDEX);
                mRecipeName = getArguments().getString("Title");

            } else {
                recipeArrayList = getArguments().getParcelableArrayList(INDEX_RECIPE);
                stepsArrayList = (ArrayList<Steps>) recipeArrayList.get(0).getSteps();
                selectedIndex = 0;
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        stepsTextView = (TextView) rootView.findViewById(R.id.tv_step_text);
        stepsTextView.setText(stepsArrayList.get(selectedIndex).getDescription());
        stepsTextView.setVisibility(View.VISIBLE);
        playerView = rootView.findViewById(R.id.video_player_view);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoUrl = stepsArrayList.get(selectedIndex).getVideoUrl();

        if (rootView.findViewWithTag("sw600dp-port-fragment_steps") != null) {
            mRecipeName = ((RecipeDetailActivity) getActivity()).recipeName;
            ((RecipeDetailActivity) getActivity()).getSupportActionBar().setTitle(mRecipeName);

        }

        String imageUrl = stepsArrayList.get(selectedIndex).getTumbnailUrl();

        if (imageUrl != "") {
            Uri uri = Uri.parse(imageUrl).buildUpon().build();
            ImageView thumbImage = rootView.findViewById(R.id.iv_thumbImage);
            Picasso.get().load(uri).into(thumbImage);

        }

        if (!videoUrl.isEmpty()) {
            initializePlayer(getActivity(), Uri.parse(stepsArrayList.get(selectedIndex).getVideoUrl()));


            if (rootView.findViewWithTag("sw600dp-land-fragment_steps") != null) {
                getActivity().findViewById(R.id.fragment_container1).setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            } else if (isInLandscape(getContext())) {
                stepsTextView.setVisibility(View.GONE);
            }
        } else {
            simpleExoPlayer = null;
            Toast.makeText(getActivity(), R.string.no_video, Toast.LENGTH_SHORT).show();

        }


        Button previousButton = (Button) rootView.findViewById(R.id.previous_button);
        Button nextButton = (Button) rootView.findViewById(R.id.next_button);

        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepsArrayList.get(selectedIndex).getId() > 0) {
                    if (simpleExoPlayer != null) {
                        simpleExoPlayer.stop();
                    }
                    mListClickListener.onItemClick(stepsArrayList, stepsArrayList.get(selectedIndex).getId() - 1,
                            mRecipeName);
                } else {
                    Toast.makeText(getActivity(), R.string.first_step, Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastIndex = stepsArrayList.size() - 1;
                if (stepsArrayList.get(selectedIndex).getId() < stepsArrayList.get(lastIndex).getId()) {
                    if (simpleExoPlayer != null) {
                        simpleExoPlayer.stop();
                    }
                    mListClickListener.onItemClick(stepsArrayList, stepsArrayList.get(selectedIndex).getId() + 1,
                            mRecipeName);
                } else {
                    Toast.makeText(getContext(),R.string.last_step, Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void initializePlayer(Context context, Uri mediaUri) {
        if (simpleExoPlayer == null) {
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
            playerView.setPlayer(simpleExoPlayer);
            playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING);
            simpleExoPlayer.setPlayWhenReady(true);
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_SPEECH)
                    .build();
            simpleExoPlayer.setAudioAttributes(audioAttributes, true);
            String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultDataSourceFactory(context, userAgent)).createMediaSource(mediaUri);
            simpleExoPlayer.prepare(mediaSource);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INDEX_STEPS, stepsArrayList);
        outState.putInt(SELECTED_INDEX, selectedIndex);
        outState.putString("Title", mRecipeName);
    }

    public boolean isInLandscape(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    public interface ListClickListener {
        void onItemClick(List<Steps> stepsList, int clickedIndex, String recipeName);

    }
}