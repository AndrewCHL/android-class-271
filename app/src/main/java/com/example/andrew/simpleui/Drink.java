package com.example.andrew.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Andrew on 8/1/16.
 */
@ParseClassName("Drink")
public class Drink extends ParseObject implements Parcelable {

    static final String NAME_COL = "name";
    static final String MPRICE_COL = "priceM";
    static final String LPRICE_COL = "priceL";

    int imageID;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (getObjectId() == null) {
            dest.writeInt(0);

            dest.writeString(getName());
            dest.writeInt(getPriceM());
            dest.writeInt(getPriceL());
            //dest.writeInt(this.imageID);
        } else {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }


    }

    public Drink() {
    }

    protected Drink(Parcel in) {
        setName(in.readString());
        setPriceM(in.readInt());
        setPriceL(in.readInt());
        this.imageID = in.readInt();

    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel source) {
            int isDraft = source.readInt();

            if (isDraft == 0) {
                return new Drink(source);
            } else {
                return getDrinkFromCashe(source.readString());
            }


        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public static ParseQuery<Drink> getQuery() {
        return ParseQuery.getQuery(Drink.class);
    }

    public static Drink getDrinkFromCashe(String objectID) {
        try {
            Drink drink = getQuery()
                    .fromLocalDatastore().get(objectID);

            return drink;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return Drink.createWithoutData(Drink.class, objectID);
    }


    public ParseFile getParseFile(){
        return  getParseFile("image");
    }

    public String getName() {
        return getString(NAME_COL);
    }

    public void setName(String name) {
        put(NAME_COL, name);
    }

    public int getPriceM() {
        return getInt(MPRICE_COL);
    }

    public void setPriceM(int priceM) {
        put(MPRICE_COL, priceM);
    }

    public int getPriceL() {
        return getInt(LPRICE_COL);
    }

    public void setPriceL(int priceL) {
        put(LPRICE_COL, priceL);
    }


}
