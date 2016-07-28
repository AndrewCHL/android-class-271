package com.example.andrew.simpleui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;

    String name = "";
    String drink = "black tea";

    TextView txView;
    EditText edtText;
    RadioGroup rGroup;
    ListView lView;
    Spinner spn;


    ArrayList<order> orders = new ArrayList<order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txView = (TextView) findViewById(R.id.textView);
        edtText = (EditText) findViewById(R.id.editText);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);
        lView = (ListView) findViewById(R.id.listView);
        spn = (Spinner) findViewById(R.id.spinner);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton btn = (RadioButton) radioGroup.findViewById(id);
                drink = btn.getText().toString();
            }
        });

        edtText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
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
                order temp = (order) adapterView.getAdapter().getItem(i);
                Toast.makeText(MainActivity.this, "You select: " + temp.note, Toast.LENGTH_SHORT).show();
                Snackbar.make(adapterView, "You select: " + temp.note, Snackbar.LENGTH_SHORT).
                        setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });

        setUpSpinner();

        Log.d("debug", "MainActivity OnCreate");

    }

    private void setUpSpinner() {
        String[] data = getResources().getStringArray(R.array.storeInfos);
        spn.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data));
    }

    public void setUpListView() {
        lView.setAdapter(new OrderAdapter(this, orders));
    }

    public void submit(View view) {

        order newOrder = new order();
        newOrder.drink = drink;
        newOrder.note = edtText.getText().toString();
        newOrder.storeInfo = (String) spn.getSelectedItem();
        orders.add(newOrder);

        setUpListView();

    }

    public void goToMenu(View view) {
        Intent intent = new Intent(); // used to call another activity process
        intent.setClass(this, DrinkMenuActivity.class); // call from this activity to another

        startActivityForResult(intent, REQUEST_CODE_DRINK_MENU_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DRINK_MENU_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
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
