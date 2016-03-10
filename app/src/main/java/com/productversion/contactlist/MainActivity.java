package com.productversion.contactlist;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ArrayList<Contacts> temp;
   ArrayList<Contacts> calledPersons;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    Resources res;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        list = (ListView) findViewById(R.id.listView);
        res = this.getResources();
        temp = new ArrayList<>();
        calledPersons = new ArrayList<>();
        new getCalls().execute();

        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newContact = new Intent(MainActivity.this, AddContact.class);
                startActivity(newContact);
            }
        });

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

        return super.onOptionsItemSelected(item);
    }


    private class getCalls extends AsyncTask<Void, Void, ArrayList<Contacts>> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, res.getString(R.string.Loading), res.getString(R.string.loading_log), true);
        }

        @Override
        protected ArrayList<Contacts> doInBackground(Void... voids) {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
                int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
                int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
                int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
                while (managedCursor.moveToNext())
                {
                    Contacts contacts = new Contacts();
                    contacts.setId(managedCursor.getPosition());
                    //contacts.setNumber_of_calls(1);
                    contacts.setPhone(managedCursor.getString(number));
                    if (contacts.getPhone().startsWith("+358")) {
                       String num = contacts.getPhone();
                        num = num.replace("+358", "0");
                        contacts.setPhone(num);
                    }
                    contacts.setType(managedCursor.getString(type));
                    String callDate = managedCursor.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    contacts.setLast_contact(callDayTime);
                    contacts.setDuration(managedCursor.getString(duration));
                    contacts.setOffer_left(sharedPreferences.getBoolean(contacts.getPhone(), false));

                    temp.add(contacts);
                }
                managedCursor.close();
                Log.e("Main", String.valueOf(temp.size()));
            }
            Iterator<Contacts> iterator = temp.iterator();
            while(iterator.hasNext()) {
                Contacts contacts = iterator.next();
                int dircode = Integer.parseInt(contacts.type);
               switch (dircode) {
                   case CallLog.Calls.OUTGOING_TYPE:
                       //Log.e("TAG", "Soitettu");
                       contacts.setType_text("Soitettu puhelu");
                       calledPersons.add(contacts);
                       break;
                   case CallLog.Calls.INCOMING_TYPE:
                       contacts.setType_text("Vastattu puhelu");
                       calledPersons.add(contacts);
                       break;
                   case CallLog.Calls.MISSED_TYPE:
                       contacts.setType_text("Ei vastattu");
                       break;
               }
            }
            ArrayList<Contacts> tempList;
            tempList = calledPersons;
            calledPersons = getDublicates(tempList);


            ArrayList<Contacts> list = new ArrayList<>();
            ArrayList<String> nums = new ArrayList<>();
            for (Contacts cont : tempList) {
                if (nums.contains(cont.phone)) {
                    cont.number_of_calls++;
                } else {
                    nums.add(cont.phone);
                    cont.number_of_calls++;
                    list.add(cont);
                }

            }
            for (Contacts cont : calledPersons) {
                Log.e(TAG, String.valueOf(cont.getNumber_of_calls()) + "-" + String.valueOf(cont.getListOfCalls().size()));
            }


            //calledPersons = list;
            Collections.sort(calledPersons, new ListComparator());


            return calledPersons;
        }

        @Override
        protected void onPostExecute(ArrayList<Contacts> result) {
            super.onPostExecute(result);
            list.setClickable(true);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "Clicked item in position " + position, Toast.LENGTH_LONG).show();
                    Intent contactIntent = new Intent(MainActivity.this, ContactActivity.class);
                    Gson gson = new Gson();
                    String data = gson.toJson(calledPersons.get(position));
                    contactIntent.putExtra("contact", data);
                    Log.e("TAG", String.valueOf(calledPersons.get(position).listOfCalls.size()));
                    startActivity(contactIntent);

                }
            });
            ListAdapter adapter = new ListAdapter(getApplicationContext(), R.layout.contact_list_item, calledPersons);
            Log.e("Main", "List set");

            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }


    private boolean compareLists(ArrayList<Contacts> list1, ArrayList<Contacts> list2) {

        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i <list1.size(); i++) {
            if (list1.get(i) != list2.get(i)) {
                return false;
            }
            if (!list1.get(i).phone.equals(list2.get(i).phone)) {
                return false;
            } if (list1.get(i).number_of_calls != list2.get(i).number_of_calls) {
                return false;
            }
        }

        return true;
    }

    class ListComparator implements Comparator<Contacts> {

        @Override
        public int compare(Contacts lhs, Contacts rhs) {
            return lhs.getLast_contact().compareTo(rhs.getLast_contact());
        }
    }

    private ArrayList<Contacts> getDublicates(ArrayList<Contacts> contactses) {
        final ArrayList<String> phones = new ArrayList<>();
        final ArrayList<Contacts> ctList = new ArrayList<>();
        Iterator<Contacts> it = contactses.iterator();
        while (it.hasNext()) {
            Contacts ct = it.next();
            final String phone = ct.getPhone();

            if (phones.contains(phone)) {
                ctList.add(ct);
                it.remove();
            } else {
                phones.add(phone);
            }

        }
        Log.e(TAG, String.valueOf(ctList.size()));
        Log.e(TAG, String.valueOf(contactses.size()));

       for (Contacts c : contactses) {
           ArrayList<NumberLog> numberLogs = new ArrayList<>();
            for (int i = 0; i < ctList.size(); i++) {
                if (c.equals(ctList.get(i))) {
                    //Log.e(TAG, "Equals");
                    NumberLog log = new NumberLog(ctList.get(i).getPhone(), ctList.get(i).getLast_contact_date(), ctList.get(i).getType_text(), ctList.get(i).getDuration());
                    if (ctList.get(i).getType().equals(CallLog.Calls.MISSED_TYPE)) {
                        log.setAnswered(false);
                    } else {
                        log.setAnswered(true);
                    }

                    numberLogs.add(log);
                }
            }
           c.listOfCalls = numberLogs;
           c.number_of_calls = c.number_of_calls + numberLogs.size();
           Log.e(TAG, String.valueOf(c.getNumber_of_calls()) + "-" + String.valueOf(c.getListOfCalls().size()));
       }

        return contactses;
    }
}
