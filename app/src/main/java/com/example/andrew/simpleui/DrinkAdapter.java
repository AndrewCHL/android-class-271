package com.example.andrew.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Andrew on 7/28/16.
 */
public class DrinkAdapter extends BaseAdapter {

    List<Drink> drinkList;
    LayoutInflater inflater;

    public DrinkAdapter(Context context, List<Drink> list){
        // context is Activity

        this.drinkList = list;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_drink_item, null);

            ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
            TextView drinkName = (TextView) convertView.findViewById(R.id.textViewDrinkName);
            TextView mPrice = (TextView) convertView.findViewById(R.id.textViewMPrice);
            TextView lPrice = (TextView) convertView.findViewById(R.id.textViewLPrice);

            holder = new Holder();

            holder.image = image;
            holder.drinkName = drinkName;
            holder.mPrice = mPrice;
            holder.lPrice = lPrice;

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();

        }

        holder.drinkName.setText(drinkList.get(position).getName());
        holder.mPrice.setText(String.valueOf(drinkList.get(position).getPriceM()));
        holder.lPrice.setText(String.valueOf(drinkList.get(position).getPriceL()));

        Picasso.with(inflater.getContext()).load(drinkList.get(position).getParseFile().getUrl()).into(holder.image);


        return convertView;
    }

    public class Holder{
        ImageView image;
        TextView drinkName;
        TextView mPrice;
        TextView lPrice;
    }
}
