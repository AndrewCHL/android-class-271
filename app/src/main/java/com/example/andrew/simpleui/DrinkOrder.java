package com.example.andrew.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andrew on 8/1/16.
 */
public class DrinkOrder implements Parcelable {

    Drink drink;
    int mNumber = 1;
    int lNumber = 1;
    String ice = "Regular";
    String sugar = "Regular";
    String note = "None";

    public DrinkOrder(Drink drink){
        this.drink = drink;
    }

    public int total(){
        return drink.priveL*lNumber + drink.priveM* mNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.drink, flags);
        dest.writeInt(this.mNumber);
        dest.writeInt(this.lNumber);
        dest.writeString(this.ice);
        dest.writeString(this.sugar);
        dest.writeString(this.note);
    }

    protected DrinkOrder(Parcel in) {
        this.drink = in.readParcelable(Drink.class.getClassLoader());
        this.mNumber = in.readInt();
        this.lNumber = in.readInt();
        this.ice = in.readString();
        this.sugar = in.readString();
        this.note = in.readString();
    }

    public static final Parcelable.Creator<DrinkOrder> CREATOR = new Parcelable.Creator<DrinkOrder>() {
        @Override
        public DrinkOrder createFromParcel(Parcel source) {
            return new DrinkOrder(source);
        }

        @Override
        public DrinkOrder[] newArray(int size) {
            return new DrinkOrder[size];
        }
    };
}
