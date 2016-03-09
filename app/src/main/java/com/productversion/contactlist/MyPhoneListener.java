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
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(_contex.getApplicationContext(),"soitetaan...", Toast.LENGTH_LONG).show();
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:

                    if (onCall) {
                        Intent restart = _contex.getApplicationContext().getPackageManager().getLaunchIntentForPackage(_contex.getApplicationContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        _contex.getApplicationContext().startActivity(restart);
                        onCall = false;
                    }
                    break;
                default:
                    break;
            }
        }
    }

