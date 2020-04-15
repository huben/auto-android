package org.auto.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "yjj";
    public SmsReceiver() {
        Log.i("yjj", "new SmsReceiver");
    }
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "jie shou dao");
        Cursor cursor = null;
        try {
            if (SMS_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "sms received!");
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    final SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    if (messages.length > 0) {
                        String content = messages[0].getMessageBody();
                        String sender = messages[0].getOriginatingAddress();
                        long msgDate = messages[0].getTimestampMillis();
                        String smsToast = "New SMS received from : "
                                + sender + "\n'"
                                + content + "'";

                        Log.d(TAG, "message from: " + sender + ", message body: " + content
                                + ", message date: " + msgDate);
                        //自己的逻辑
                    }
                }
                cursor = context.getContentResolver().query(Uri.parse("content://sms"), new String[] { "_id", "address", "read", "body", "date" }, "read = ? ", new String[] { "0" }, "date desc");
                if (null == cursor){
                    return;
                }
                Log.i(TAG,"m cursor count is "+cursor.getCount());
                Log.i(TAG,"m first is "+cursor.moveToFirst());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception : " + e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }
}
