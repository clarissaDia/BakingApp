package com.example.android.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {
    private int mQuantity;
    private String mMeasure;
    private String mIngredient;

    public int getQuantity(){
        return mQuantity;
    }
    public void setQuantity(int quantity){
        this.mQuantity = quantity;
    }

    public String getMeasure (){
        return mMeasure;
    }
    public void setMeasure (String measure){
        this.mMeasure = measure;
    }

    public String getIngredient(){
        return mIngredient;
    }
    public void setIngredient (String ingredient){
        this.mIngredient = ingredient;
    }

    protected Ingredients (Parcel parcel){
mQuantity = parcel.readInt();
mMeasure = parcel.readString();
mIngredient = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeInt(mQuantity);
dest.writeString(mMeasure);
dest.writeString(mIngredient);

    }
    public static final Parcelable.Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel parcel) {
            return new Ingredients(parcel);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

}
