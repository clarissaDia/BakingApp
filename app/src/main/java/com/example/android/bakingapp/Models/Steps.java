package com.example.android.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("shortDescription")
    @Expose
    private String mShortDescription;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("videoURL")
    @Expose
    private String mVideoUrl;
    @SerializedName("thumbnailURL")
    @Expose
    private String mTumbnailUrl;

    public int getId(){
        return mId;
    }
    public void setId (int id){
        this.mId = id;
    }

    public String getShortDescription (){
        return mShortDescription;
    }
    public void setShortDescription(String shortDescription){
        this.mShortDescription = shortDescription;
    }

    public String getDescription(){
        return mDescription;
    }
    public void setDescription(String description){
        this.mDescription = description;
    }

    public String getVideoUrl(){
        return mVideoUrl;
    }
    public void setVideoUrl (String videoUrl){
        this.mVideoUrl = videoUrl;
    }

    public String getTumbnailUrl (){
        return mTumbnailUrl;
    }
    public void setTumbnailUrl (String tumbnailUrl){
        this.mTumbnailUrl = tumbnailUrl;
    }
    protected Steps(Parcel parcel){
        mId = parcel.readInt();
        mShortDescription = parcel.readString();
        mDescription = parcel.readString();
        mVideoUrl = parcel.readString();
        mTumbnailUrl = parcel.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
        dest.writeString(mTumbnailUrl);

    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>(){

        @Override
        public Steps createFromParcel(Parcel parcel) {
            return new Steps(parcel);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
