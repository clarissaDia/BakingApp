package com.example.android.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredients> mIngredients;
    @SerializedName("steps")
    @Expose
    private List<Steps> mSteps;
    @SerializedName("servings")
    @Expose
    private int mServings;
    @SerializedName("image")
    @Expose
    private String mImage;

    protected Recipe(Parcel parcel) {
        mId = parcel.readInt();
        mName = parcel.readString();
        if (parcel.readByte() == 0x01) {
            mIngredients = new ArrayList<Ingredients>();
            parcel.readList(mIngredients, Ingredients.class.getClassLoader());
        } else {
            mIngredients = null;
        }
        if (parcel.readByte() == 0x01) {
            mSteps = new ArrayList<Steps>();
            parcel.readList(mSteps, Steps.class.getClassLoader());
        } else {
            mSteps = null;
        }

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List<Ingredients> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.mIngredients = ingredients;
    }

    public List<Steps> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Steps> steps) {
        this.mSteps = steps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        this.mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        if (mIngredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x001));
            dest.writeList(mIngredients);
        }
        if (mSteps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x001));
            dest.writeList(mSteps);
        }
        dest.writeInt(mServings);
        dest.writeString(mImage);
    }
}