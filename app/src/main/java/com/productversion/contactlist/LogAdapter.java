package com.productversion.contactlist;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class LogAdapter extends ArrayAdapter<NumberLog> {

    List<NumberLog> contactsList = Collections.emptyList();
    private Context _context;
    String TAG = ListAdapter.class.getSimpleName();
    protected ListView mListView;
    Resources res;

    public LogAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    public LogAdapter(Context context, int resource, List<NumberLog> contactses) {
        super(context, resource, contactses);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.calledlogrow, null);
        }

        final NumberLog contact = getItem(position);

        if (contact != null) {
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView type = (TextView) v.findViewById(R.id.type);
            ImageView calledImage = (ImageView)v.findViewById(R.id.answerImage);
            res = _context.getResources();

            date.setText(contact.getCallDate());
            type.setText(contact.getCalltype());
            if (!contact.answered) {
                calledImage.setBackgroundResource(R.drawable.ic_local_phone_red_24dp);
            } else {
                calledImage.setBackgroundResource(R.drawable.ic_local_phone_black_24dp);
            }

        }

        return v;
    }

}
