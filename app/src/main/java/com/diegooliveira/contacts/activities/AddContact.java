package com.diegooliveira.contacts.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.diegooliveira.contacts.R;
import com.diegooliveira.contacts.models.Person;
import com.diegooliveira.contacts.models.PhoneType;
import com.diegooliveira.contacts.util.Mask;

public class AddContact extends AppCompatActivity implements OnItemSelectedListener {

    public final static int REQUEST_CODE_CONTACTS = 1;
    public final static int REQUEST_CODE_EMAIL = 2;
    public final static int REQUEST_CODE_WHATSAPP = 3;
    public final static int REQUEST_CODE_INSTAGRAM = 4;
    public final static int REQUEST_CODE_LINKEDIN = 5;

    private Person person;

    Button submitButton;
    EditText nameText, lastNameText, phoneText, emailText, instagramText, linkedinText;
    Spinner phoneTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        this.setInputReferences();
        this.setOnClickListener();
        this.addMaskToPhoneInput();
        this.inflateSpinner();
    }

    protected void setInputReferences() {
        nameText = findViewById(R.id.nameText);
        lastNameText = findViewById(R.id.lastNameText);
        phoneText = findViewById(R.id.phoneText);
        emailText = findViewById(R.id.emailText);
        instagramText = findViewById(R.id.instagramText);
        linkedinText = findViewById(R.id.linkedinText);
        phoneTypeSpinner = findViewById(R.id.phoneTypeSpinner);
        submitButton = findViewById(R.id.submitButton);
    }

    protected void setOnClickListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String phone = Mask.unmask(phoneText.getText().toString());
                String phoneTypeName = phoneTypeSpinner.getSelectedItem().toString();
                int phoneType = PhoneType.getByName(phoneTypeName).getId();
                String email = emailText.getText().toString();
                String instagram = instagramText.getText().toString();
                String linkedin = linkedinText.getText().toString();

                person = new Person(name, lastName, phone, phoneType, email, instagram, linkedin);
                openAndroidContacts();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE_CONTACTS:
                callMailIntent();
                break;
            case REQUEST_CODE_EMAIL:
                callWhatsAppIntent();
                break;
            case REQUEST_CODE_WHATSAPP:
                callInstagramIntent();
                break;
            case REQUEST_CODE_INSTAGRAM:
                callLinkedinIntent();
                break;
            case REQUEST_CODE_LINKEDIN:
                goBackToHome();
                break;
        }
    }

    protected void openAndroidContacts() {
        Intent contactsIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
        contactsIntent
            .setType(ContactsContract.RawContacts.CONTENT_TYPE);

        contactsIntent
            .putExtra(ContactsContract.Intents.Insert.NAME, person.getFullName())
            .putExtra(ContactsContract.Intents.Insert.PHONE, person.getPhone())
            .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, person.getPhoneType().getId())
            .putExtra(ContactsContract.Intents.Insert.EMAIL, person.getEmail());

        startActivityForResult(contactsIntent, 1);
    }

    protected void callMailIntent() {
        String mailto = "mailto:" + person.getEmail();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato");
        emailIntent.putExtra(Intent.EXTRA_TEXT   , "Voce foi adicionado a lista de contatos.");
        Uri uri = Uri.parse(mailto);

        callIntent(emailIntent, uri, 2);
    }

    protected void callWhatsAppIntent() {
        Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://api.whatsapp.com/send?phone=55" + person.getPhone() + "&text=" + "Ola! Seu contato foi salvo com sucesso!");
        callIntent(whatsAppIntent, uri, 3);
    }

    protected void callInstagramIntent() {
        Intent instagramIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://instagram.com/_u/" + person.getInstagram());

        callIntent(instagramIntent, uri, 4);
    }

    protected void callLinkedinIntent() {
        Intent instagramIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://www.linkedin.com/in/" + person.getLinkedin());

        callIntent(instagramIntent, uri, 5);
    }

    protected void callIntent(Intent intent, Uri uri, int requestCode) {
        try {
            intent.setData(uri);
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void goBackToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    protected void addMaskToPhoneInput() {
        phoneText.addTextChangedListener(Mask.insert("(##)#####-####", phoneText));
    }

    protected void inflateSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PhoneType.listEnumNames());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        phoneTypeSpinner.setAdapter(dataAdapter);
        phoneTypeSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();

        //Log.d("Info", "Selecionado: " + item);
        //Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {  }

    public void buttonListener() {

    }
}
