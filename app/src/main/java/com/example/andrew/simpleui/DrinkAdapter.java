package com.example.andrew.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Andrew on 7/28/16.
 */
public class DrinkAdapter extends BaseAdapter {

    List<DrinkMenuActivity.Drink> drinkList;
    LayoutInflater inflater;

    public DrinkAdapter(Context context, List<DrinkMenuActivity.Drink> list){
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

        holder.image.setImageResource(drinkList.get(position).imageId);
        holder.drinkName.setText(drinkList.get(position).name);
        holder.mPrice.setText(String.valueOf(drinkList.get(position).priveM));
        holder.lPrice.setText(String.valueOf(drinkList.get(position).priveL));

        return convertView;
    }

    public class Holder{
        ImageView image;
        TextView drinkName;
        TextView mPrice;
        TextView lPrice;
    }
}
