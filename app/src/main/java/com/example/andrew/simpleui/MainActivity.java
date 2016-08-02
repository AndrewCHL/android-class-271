package com.example.andrew.simpleui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;

    String name = "";
    String drink = "black tea";

    TextView txView;
    EditText edtText;
    RadioGroup rGroup;
    ListView lView;
    Spinner spn;


    ArrayList<Order> orders = new ArrayList<Order>();
    ArrayList<DrinkOrder> drinkOrders = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txView = (TextView) findViewById(R.id.textView);
        edtText = (EditText) findViewById(R.id.editText);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);
        lView = (ListView) findViewById(R.id.listView);
        spn = (Spinner) findViewById(R.id.spinner);

        sharedPreferences = getSharedPreferences("UIState", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton btn = (RadioButton) radioGroup.findViewById(id);
                editor.putString("radioGroup",btn.getText().toString());
                drink = btn.getText().toString();
            }
        });

        edtText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                editor.putString("editText",edtText.getText().toString());
                editor.apply();

                if (keyCode == keyEvent.KEYCODE_ENTER &&
                        keyEvent.getAction() == keyEvent.ACTION_DOWN) {
                    submit(view);
                    return true; // Otherwise, will key in \n
                }
                return false;
            }
        });

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Order temp = (Order) adapterView.getAdapter().getItem(i);
                Toast.makeText(MainActivity.this, "You select: " + temp.getNote(), Toast.LENGTH_SHORT).show();
                Snackbar.make(adapterView, "You select: " + temp.getNote(), Snackbar.LENGTH_SHORT).
                        setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putString("spinner",String.valueOf(spn.getSelectedItemPosition()));
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editor.putString("spinner",String.valueOf(spn.getSelectedItemPosition()));
                editor.apply();
            }
        });

        //setUpOrderHistory();

        setUpSpinner();

        restoreUIState();

        setUpListView();

        /*
        ParseObject parseObject = new ParseObject("TestObject");//upload to this table or create one
        parseObject.put("foo","Andrew");
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(MainActivity.this,"success", Toast.LENGTH_SHORT).show();
                } else{
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TestObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for(ParseObject each: objects){
                    Toast.makeText(MainActivity.this, each.getString("foo"), Toast.LENGTH_SHORT).show();
                }
            }
        });
        */

        Log.d("debug", "MainActivity OnCreate");

    }

    private void setUpOrderHistory() {
        String orderData = Utils.readFile(this, "history");
        String[] orderDataArray = orderData.split("\n");
        Gson gson = new Gson();

        for(String each: orderDataArray){
            try{
                Order order = gson.fromJson(each, Order.class);
                if( order != null){
                    orders.add(order);
                }
            }catch(JsonSyntaxException e){

            }
        }
    }

    private void restoreUIState(){
        edtText.setText(sharedPreferences.getString("editText","Green Tea"));

        for(int i = 0; i < rGroup.getChildCount(); ++i){
            View view = rGroup.getChildAt(i);
            if(view instanceof RadioButton ){
                RadioButton radioButton = (RadioButton) view;
                if(radioButton.getText().toString().equals(sharedPreferences.getString("radioGroup",""))){
                    radioButton.setChecked(true);
                } else{
                    radioButton.setChecked(false);
                }
            }
        }

        spn.setSelection(Integer.valueOf(sharedPreferences.getString("spinner","")));
    }

    private void setUpSpinner() {
        String[] data = getResources().getStringArray(R.array.storeInfos);
        spn.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data));
    }

    public void setUpListView() {
        lView.setAdapter(new OrderAdapter(this, orders));
    }

    public void submit(View view) {

        Order newOrder = new Order();
        newOrder.setDrinkOrders(drinkOrders);
        newOrder.setNote(edtText.getText().toString());
        newOrder.setStoreInfo((String) spn.getSelectedItem());
        orders.add(newOrder);

        Gson gson = new Gson();
        String orderData = gson.toJson(newOrder);

        Utils.writeFile(this, "history", orderData + "\n");


        drinkOrders = new ArrayList<>();
        // Reset the list after adding into order

        setUpListView();

    }

    public void goToMenu(View view) {
        Intent intent = new Intent(); // used to call another activity process
        intent.setClass(this, DrinkMenuActivity.class); // call from this activity to another
        intent.putExtra("drinkOrderList",drinkOrders);

        startActivityForResult(intent, REQUEST_CODE_DRINK_MENU_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DRINK_MENU_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                drinkOrders = data.getParcelableArrayListExtra("results");
                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug", "MainActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug", "MainActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug", "MainActiity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug", "MainActivity OnStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("debug", "MainActivity OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug", "MainAcitvity OnDestroy");
    }
}
