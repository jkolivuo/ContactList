package com.productversion.contactlist;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    Contacts contact;
    EditText phone;
    CheckBox offer;
    ImageButton call;
    TextView lastcall;
    Resources res;
    ListView calls;
    Gson gson;
    private static final String TAG = "ContactActivity";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstace) {
        super.onCreate(savedInstace);
        setContentView(R.layout.contact_info);
        sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        res = this.getResources();
        gson = new Gson();

        phone = (EditText) findViewById(R.id.phone);
        offer = (CheckBox) findViewById(R.id.offer);
        call = (ImageButton) findViewById(R.id.call);
        call.setOnClickListener(this);
        lastcall = (TextView)findViewById(R.id.last_contact);
        calls = (ListView)findViewById(R.id.listView_contact);

        String target = getIntent().getStringExtra("contact");
        final Contacts contact = gson.fromJson(target, Contacts.class);
        Log.e(TAG, contact.getLast_contact_date());
            getSupportActionBar().setTitle(contact.phone);
            phone.setText(contact.phone);
            offer.setChecked(contact.offer_left);
        lastcall.setText(res.getString(R.string.last_call, contact.getLast_contact_date()));
        Log.e(TAG, String.valueOf(contact.listOfCalls));

        LogAdapter adapter = new LogAdapter(getApplicationContext(), R.layout.calledlogrow,contact.listOfCalls);
        calls.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        offer.setChecked(sharedPreferences.getBoolean(contact.getPhone(), false));
        offer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //TODO
                    sharedPreferences.edit().putBoolean(contact.getPhone(), true).apply();
                } else {
                    sharedPreferences.edit().putBoolean(contact.getPhone(), true).apply();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v.equals(call)) {
            if (phone.getText().toString() != "") {
                String uri = "tel:" + phone.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 100);

                    return;
                }
                startActivity(intent);
            } else {
                Toast.makeText(this, "Syötä puhelinnumero", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Sovellus voi soittaa puheluita", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Sovellus ei voi soittaa puheluita", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
