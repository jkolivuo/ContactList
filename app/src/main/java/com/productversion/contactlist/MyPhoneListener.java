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
                    
                    break;
                default:
                    break;
            }
        }
    }

