package com.example.andrew.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew on 7/27/16.
 */
public class OrderAdapter extends BaseAdapter {

    List<Order> orders;
    LayoutInflater inflater;

    public OrderAdapter(Context context, List<Order> list){
        // context is Activity

        this.orders = list;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        // list view must know how many number in the array
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;

        if(view == null){
            view = inflater.inflate(R.layout.listview_order_item, null);

            TextView note = (TextView) view.findViewById(R.id.textViewNote);
            TextView storeInfo = (TextView) view.findViewById(R.id.textViewStoreInf);
            TextView drink = (TextView) view.findViewById(R.id.textViewDrink);

            holder = new Holder();

            holder.noteTextView = note;
            holder.storeInfoTextView = storeInfo;
            holder.drinkTexiView = drink;

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();

        }

        holder.noteTextView.setText(orders.get(i).getNote());
        holder.storeInfoTextView.setText(orders.get(i).getStoreInfo());
        holder.drinkTexiView.setText(String.valueOf(orders.get(i).total()));

        return view;
    }

    public class Holder{
        TextView noteTextView;
        TextView storeInfoTextView;
        TextView drinkTexiView;
    }
}
