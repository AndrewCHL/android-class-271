package com.example.andrew.simpleui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnDrinkOrderListener {

    int sum = 0;
    ListView lview;
    TextView tView;

    String[] drinkName = {"Black Tea", "Green Tea", "Milk Tea", "Coke"};
    int[] drinkPriceM = {5, 5, 6, 3};
    int[] drinkPriceL = {6, 6, 7, 4};
    int[] imageId = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};

    ArrayList<Drink> drinkList = new ArrayList<>();
    ArrayList<Drink> drinkSelected = new ArrayList<>();

    @Override
    public void onDrinkOrderFinished() {

    }

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

        public final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        lview = (ListView) findViewById(R.id.drinkMenuListView);
        tView = (TextView) findViewById(R.id.textViewMoney);

        setDrinkList();
        setUpListView();

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Drink drink = (Drink) adapterView.getAdapter().getItem(i);
                showDrinkOrderDialog(drink); // New!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                sum += ((Drink) adapterView.getAdapter().getItem(i)).priveM;
                tView.setText(String.valueOf(sum));
            }
        });


        Log.d("debug", "DrinkMenuActivity OnCreate");
    }

    private void showDrinkOrderDialog(Drink drink){
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance(drink);

        dialog.show(ft, "DrinkOrderDialog");
    }

    private void setDrinkList() {
        for (int i = 0; i < drinkName.length; ++i) {
            Drink drink = new Drink();
            drink.name = drinkName[i];
            drink.priveM = drinkPriceM[i];
            drink.priveL = drinkPriceL[i];
            drink.imageId = imageId[i];
            drinkList.add(drink);
        }
    }

    private void setUpListView() {

        lview.setAdapter(new DrinkAdapter(this, drinkList));
    }

    public void done(View view) {
        Intent intent = new Intent();
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
