package com.example.andrew.simpleui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
