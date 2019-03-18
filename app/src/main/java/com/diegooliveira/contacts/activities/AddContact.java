package com.diegooliveira.contacts.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.diegooliveira.contacts.R;
import com.diegooliveira.contacts.models.PhoneType;
import com.diegooliveira.contacts.util.Mask;

public class AddContact extends AppCompatActivity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        this.addMaskToPhoneInput();
        this.inflateSpinner();
    }

    protected void addMaskToPhoneInput() {
        EditText phoneText = findViewById(R.id.phoneText);
        phoneText.addTextChangedListener(Mask.insert("(##)#####-####", phoneText));
    }

    protected void inflateSpinner() {
        Spinner phoneTypeSpinner = findViewById(R.id.phoneTypeSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PhoneType.listEnumNames());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        phoneTypeSpinner.setAdapter(dataAdapter);
        phoneTypeSpinner.setOnItemSelectedListener (this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();

        Log.d("Info", "Selecionado: " + item);
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
