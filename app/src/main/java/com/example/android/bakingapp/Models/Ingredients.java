package com.example.android.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredients implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private Double mQuantity;
    @SerializedName("measure")
    @Expose
    private String mMeasure;
    @SerializedName("ingredient")
    @Expose
    private String mIngredient;

    public Double getQuantity(){
        return mQuantity;
    }
    public void setQuantity(Double quantity){
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
        byte quantityByte = parcel.readByte();
        if (quantityByte != 0x00){
            mQuantity = parcel.readDouble();
        }
mMeasure = parcel.readString();
mIngredient = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(mQuantity == null){
            dest.writeByte((byte) (0x00));
        }else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(mQuantity);
        }
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
