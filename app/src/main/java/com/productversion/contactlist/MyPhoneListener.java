package com.productversion.contactlist;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


    public class MyPhoneListener extends PhoneStateListener {

        private  boolean onCall = false;
        Context _contex;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(_contex.getApplicationContext(), incomingNumber + " soittaa", Toast.LENGTH_LONG).show();
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(_contex.getApplicationContext(),"soitetaan...", Toast.LENGTH_LONG).show();
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:

                    onCall = false;
                    break;
                default:
                    break;
            }
        }
    }

