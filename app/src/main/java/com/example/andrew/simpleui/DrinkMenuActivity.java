package com.example.andrew.simpleui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnDrinkOrderListener {

    ListView lview;
    TextView tView;

    String[] drinkName = {"Black Tea", "Green Tea", "Milk Tea", "Coke"};
    int[] drinkPriceM = {5, 5, 6, 3};
    int[] drinkPriceL = {6, 6, 7, 4};
    int[] imageId = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};

    List<Drink> drinkList = new ArrayList<>();
    ArrayList<DrinkOrder> drinkSelected = new ArrayList<>();


    @Override
    public void onDrinkOrderFinished(DrinkOrder drinkOrder) {

        for(int i = 0; i < drinkSelected.size(); ++i){
            if(drinkSelected.get(i).getDrink().getObjectId().equals(drinkOrder.getDrink().getObjectId())) {
                drinkSelected.set(i, drinkOrder);
                setupTotalTextView();
                return;
            }
        }
        drinkSelected.add(drinkOrder);
        setupTotalTextView();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        lview = (ListView) findViewById(R.id.drinkMenuListView);
        tView = (TextView) findViewById(R.id.textViewMoney);

        setDrinkList();

        drinkSelected = getIntent().getParcelableArrayListExtra("drinkOrderList");

        setupTotalTextView();


        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Drink drink = (Drink) adapterView.getAdapter().getItem(i);
                showDrinkOrderDialog(drink);
                setupTotalTextView();
            }
        });


        Log.d("debug", "DrinkMenuActivity OnCreate");
    }

    private void setupTotalTextView(){

        int sum = 0;

        for(DrinkOrder each: drinkSelected){
            sum += each.total();
        }
        tView.setText(String.valueOf(sum));
    }

    private void showDrinkOrderDialog(Drink drink){
        DrinkOrder order = new DrinkOrder(drink);

        for(DrinkOrder each: drinkSelected){
            if(each.getDrink().getObjectId().equals(drink.getObjectId())){
                order = each;

                break;
            }
        }

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance(order);

        dialog.show(ft, "DrinkOrderDialog");
    }

    private void setDrinkList() {

        Drink.getQuery().findInBackground(new FindCallback<Drink>() {
            @Override
            public void done(List<Drink> objects, ParseException e) {
                if(e == null){
                    drinkList = objects;
                    setUpListView();
                }
            }
        });

    }

    private void setUpListView() {

            lview.setAdapter(new DrinkAdapter(this, drinkList));
    }

    public void done(View view) {
        Intent intent = new Intent();
        intent.putExtra("results",drinkSelected);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug", "DrinkMenuActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug", "DrinkMenuActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug", "DrinkMenuActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug", "MainActivity OnStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("debug", "DrinkMenuActivity OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug", "DrinkMenuActivity OnDestroy");
    }
}
