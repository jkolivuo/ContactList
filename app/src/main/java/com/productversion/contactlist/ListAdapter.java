package com.productversion.contactlist;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class ListAdapter extends ArrayAdapter<Contacts> {

    List<Contacts> contactsList = Collections.emptyList();
    private Context _context;
    String TAG = ListAdapter.class.getSimpleName();
    protected ListView mListView;
    Resources res;

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    public ListAdapter(Context context, int resource, List<Contacts> contactses) {
        super(context, resource, contactses);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.contact_list_item, null);
        }

        final Contacts contact = getItem(position);

        if (contact != null) {
            TextView phone = (TextView) v.findViewById(R.id.phone);
            TextView last_contact = (TextView) v.findViewById(R.id.last_contact);
            TextView number_of_calls = (TextView)v.findViewById(R.id.num_of_contact_attemps);
            TextView offer_left = (TextView)v.findViewById(R.id.offer_left);
            res = _context.getResources();

            if (phone != null) {
                phone.setText(res.getString(R.string.phone, contact.getPhone()));
            }
            if (last_contact != null) {
                SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
                contact.setLast_contact_date(sf.format(contact.getLast_contact()));
                last_contact.setText(contact.getLast_contact_date());
            }
            if (number_of_calls != null) {
                number_of_calls.setText(res.getString(R.string.numberOfCalls, String.valueOf(contact.getNumber_of_calls())));
            }
            if (offer_left != null) {
                String answer = "";
                if(contact.offer_left) {
                    answer = "Kyllä";
                } else {
                    answer = "Ei";
                }
                offer_left.setText(res.getString(R.string.offerLeft, answer));
            }

        }

        return v;
    }


}

