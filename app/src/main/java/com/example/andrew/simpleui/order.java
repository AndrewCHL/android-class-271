package com.example.andrew.simpleui;

import java.util.ArrayList;

/**
 * Created by Andrew on 7/27/16.
 */
public class Order {
    String note;
    String storeInfo;
    ArrayList<DrinkOrder> drinkOrders;

    public int total(){
        int sum = 0;

        for(DrinkOrder each: drinkOrders){
            sum += each.total();
        }

        return sum;
    }
}
