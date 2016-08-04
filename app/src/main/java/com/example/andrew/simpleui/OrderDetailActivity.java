package com.example.andrew.simpleui;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Order order = getIntent().getParcelableExtra("Order");
        TextView noteTextView = (TextView) findViewById(R.id.noteTextView);
        TextView orderResultsTextView = (TextView) findViewById(R.id.orderResultsTextView);
        TextView storeInfoTextView = (TextView) findViewById(R.id.stroeInfoTextView);



        noteTextView.setText(order.getNote());
        storeInfoTextView.setText(order.getStoreInfo());

        String orderResultText = "";
        for(DrinkOrder each: order.getDrinkOrders()){
            String mNumber = String.valueOf(each.getmNumber());
            String lNumber = String.valueOf(each.getlNumber());
            String drinkName = each.getDrink().getName();
            orderResultText += drinkName + " M: " + mNumber + " L: " + lNumber + "\n";
        }

        orderResultsTextView.setText(orderResultText);

        ImageView staticMapImageView = (ImageView) findViewById(R.id.staticMapImageView);

        /*
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                testTextView.setText("Hello Handler");
                Log.e("Current Thread ID: ", Long.toString(Thread.currentThread().getId()));
                return false;
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testTextView.setText("Hello Handler POST DELAY");
            }
        },10000);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    //testTextView.setText("Hello Thread");
                    // Only Main Thread can change UI

                    Log.e("Thread ID: ", Long.toString(Thread.currentThread().getId()));
                    handler.sendMessage(new Message());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //thread.run();// make main thread run it
        thread.start();// make another new thread to run it
        */

        Log.e("Main Thread ID: ", Long.toString(Thread.currentThread().getId()));
        String[] address = order.getStoreInfo().split(",");
        (new GeoCodingTask(staticMapImageView)).execute(address[1]);

    }

    public static class GeoCodingTask extends AsyncTask<String, Void, Bitmap>{

        WeakReference<ImageView> imageViewWeakReference;
        // ensure thread safe

        @Override
        protected Bitmap doInBackground(String... params) {
            //Log.e("Background Thread ID: ", Long.toString(Thread.currentThread().getId()));

            double[] latlng = Utils.getLatLngFromAddress(params[0]);
            if(latlng != null){
                return Utils.getStaticMapFromLatLng(latlng);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                if(imageViewWeakReference.get() != null){
                    ImageView imageView = imageViewWeakReference.get();
                    imageView.setImageBitmap(bitmap);
                }
            }
            //Log.e("PostExecute Thread ID: ", Long.toString(Thread.currentThread().getId()));
        }

        public GeoCodingTask(ImageView imageView){
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }
    }
}
