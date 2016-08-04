package com.example.andrew.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrew on 7/27/16.
 */
@ParseClassName("Order")
public class Order extends ParseObject implements Parcelable {

    static final String NOTE_COL = "note";
    static final String STOREINFO_COL = "storeInfo";
    static final String DRINKORDERS_COL = "drinkOrders";


    public int total() {
        int sum = 0;

        for (DrinkOrder each : getDrinkOrders()) {
            sum += each.total();
        }

        return sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (getObjectId() == null) {

            dest.writeInt(0);

            dest.writeString(this.getNote());
            dest.writeString(this.getStoreInfo());
            dest.writeParcelableArray((Parcelable[])this.getDrinkOrders().toArray(),flags);

        } else {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }


    }

    public Order(){
        super();
    }

    protected Order(Parcel in) {
        super();

        this.setNote(in.readString());
        this.setStoreInfo(in.readString());

        this.setDrinkOrders(Arrays.asList((DrinkOrder[]) in.readArray(DrinkOrder.class.getClassLoader())));


    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            int isDraft = source.readInt();
            if (isDraft == 0) {
                return new Order(source);
            } else {
                return getOrderFromCashe(source.readString());
            }
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public static Order getOrderFromCashe(String objectID) {
        try {
            Order Order = getQuery().fromLocalDatastore().get(objectID);

            return Order;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return Order.createWithoutData(Order.class, objectID);
    }

    public String getNote() {
        return getString(NOTE_COL);
    }

    public void setNote(String note) {
        put(NOTE_COL, note);
    }

    public String getStoreInfo() {
        return getString(STOREINFO_COL);
    }

    public void setStoreInfo(String storeInfo) {
        put(STOREINFO_COL, storeInfo);
    }

    public List<DrinkOrder> getDrinkOrders() {
        return getList(DRINKORDERS_COL);
    }

    public void setDrinkOrders(List<DrinkOrder> drinkOrders) {
        put(DRINKORDERS_COL, drinkOrders);
    }

    public static ParseQuery<Order> getQuery() {
        ParseQuery<Order> query = ParseQuery.getQuery(Order.class);
        query.include(DRINKORDERS_COL);
        query.include(DRINKORDERS_COL + "." + DrinkOrder.DRINK_COL);
        return query;
    }
    public static void getOrdersFromLocalThenRemote(final FindCallback<Order> callback){
        getQuery().fromLocalDatastore().findInBackground(callback);
        getQuery().findInBackground(new FindCallback<Order>() {
            @Override
            public void done(List<Order> objects, ParseException e) {
                if(e == null){
                    pinAllInBackground("Order",objects);
                }
                callback.done(objects,e);
            }
        });
    }
}
