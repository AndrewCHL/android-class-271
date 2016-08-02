package com.example.andrew.simpleui;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 7/27/16.
 */
@ParseClassName("Order")
public class Order extends ParseObject {

    static final String NOTE_COL = "note";
    static final String STOREINFO_COL = "storeInfo";
    static final String DRINKORDERS_COL = "drinkOrders";


    public int total(){
        int sum = 0;

        for(DrinkOrder each: getDrinkOrders()){
            sum += each.total();
        }

        return sum;
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
        put(DRINKORDERS_COL,drinkOrders);
    }
}
