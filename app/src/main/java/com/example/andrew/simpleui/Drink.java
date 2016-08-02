package com.example.andrew.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andrew on 8/1/16.
 */
public class Drink implements Parcelable {
    String name;
    int priveM;
    int priveL;
    int imageId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.priveM);
        dest.writeInt(this.priveL);
        dest.writeInt(this.imageId);
    }

    public Drink() {
    }

    protected Drink(Parcel in) {
        this.name = in.readString();
        this.priveM = in.readInt();
        this.priveL = in.readInt();
        this.imageId = in.readInt();
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel source) {
            return new Drink(source);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };
}
