package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource (){
        idlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

    }
    @Test
public void testRecipe (){
        onView(ViewMatchers.withId(R.id.rv_recipe)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource (){
        if (idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
