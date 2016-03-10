package com.productversion.contactlist;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


    public class MyPhoneListener extends PhoneStateListener {

        private  boolean onCall = false;
        Context _contex;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.e("TAG", "Calling...");
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.e("TAG", "Calling...");
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

