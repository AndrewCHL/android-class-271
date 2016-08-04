package com.example.andrew.simpleui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDrinkOrderListener} interface
 * to handle interaction events.
 * Use the {@link DrinkOrderDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkOrderDialog extends DialogFragment {

    // used as key
    private static final String DRINK_PARAM = "drink";

    DrinkOrder drinkOrderReceived;

    NumberPicker mediumNumberPicker;
    NumberPicker largeNumberPicker;
    RadioGroup iceRGroup;
    RadioGroup sugarRGroup;
    EditText noteEditText;

    private OnDrinkOrderListener mListener;

    public DrinkOrderDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrinkOrderDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkOrderDialog newInstance(DrinkOrder drinkOrder) {
        DrinkOrderDialog fragment = new DrinkOrderDialog();
        Bundle args = new Bundle();

        // must change object's format to Json or Serializable or Parcel
        args.putParcelable(DRINK_PARAM, drinkOrder);

        fragment.setArguments(args);
        return fragment;
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);
    }

    */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {

            // receive from outside
            drinkOrderReceived = getArguments().getParcelable(DRINK_PARAM);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View content = getActivity().getLayoutInflater().inflate(R.layout.fragment_drink_order_dialog, null);

        builder.setView(content)
                .setTitle(drinkOrderReceived.getDrink().getName())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drinkOrderReceived.setlNumber(largeNumberPicker.getValue());
                        drinkOrderReceived.setmNumber(mediumNumberPicker.getValue());
                        drinkOrderReceived.setIce(getSelectedItemFroRadioGroup(iceRGroup));
                        drinkOrderReceived.setSugar(getSelectedItemFroRadioGroup(sugarRGroup));
                        drinkOrderReceived.setNote(noteEditText.getText().toString());

                        if (mListener != null) {
                            mListener.onDrinkOrderFinished(drinkOrderReceived);
                            //send out to outside
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        mediumNumberPicker = (NumberPicker) content.findViewById(R.id.numberPickerM);
        largeNumberPicker = (NumberPicker) content.findViewById(R.id.numberPickerL);
        iceRGroup = (RadioGroup) content.findViewById(R.id.radioGroupIce);
        sugarRGroup = (RadioGroup) content.findViewById(R.id.radioGroupSugar);
        noteEditText = (EditText) content.findViewById(R.id.editTextNote);

        mediumNumberPicker.setMaxValue(100);
        mediumNumberPicker.setMinValue(1);
        mediumNumberPicker.setValue(drinkOrderReceived.getmNumber());
        // used to restore to previous selected value

        largeNumberPicker.setMaxValue(100);
        largeNumberPicker.setMinValue(1);
        largeNumberPicker.setValue(drinkOrderReceived.getlNumber());
        // used to restore to previous selected value

        noteEditText.setText(drinkOrderReceived.getNote());


        if (drinkOrderReceived.getIce() != null)
            setSelectedItemInRadioGroup(drinkOrderReceived.getIce(), iceRGroup);
        if (drinkOrderReceived.getSugar() != null)
            setSelectedItemInRadioGroup(drinkOrderReceived.getSugar(), sugarRGroup);


        return builder.create();
    }

    private void setSelectedItemInRadioGroup(String item, RadioGroup rGroup) {
        for (int i = 0; i < rGroup.getChildCount(); ++i) {
            View view = rGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                if (radioButton.getText().toString().equals(item)) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setChecked(false);
                }
            }
        }
    }

    private String getSelectedItemFroRadioGroup(RadioGroup rGroup) {
        int radioButtonID = rGroup.getCheckedRadioButtonId();
        RadioButton rButton = (RadioButton) rGroup.findViewById(radioButtonID);

        return rButton.getText().toString();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDrinkOrderListener) {
            mListener = (OnDrinkOrderListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDrinkOrderListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDrinkOrderListener {
        // TODO: Update argument type and name
        void onDrinkOrderFinished(DrinkOrder drinkOrder);
    }
}
