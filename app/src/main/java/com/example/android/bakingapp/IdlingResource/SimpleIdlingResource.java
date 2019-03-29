package com.example.android.bakingapp.IdlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback resourceCallback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
resourceCallback = callback;
    }

    public void setIsIdleState (boolean isIdleState){
        isIdleNow.set(isIdleState);
        if (isIdleState && resourceCallback != null){
            resourceCallback.onTransitionToIdle();
        }
    }
}
