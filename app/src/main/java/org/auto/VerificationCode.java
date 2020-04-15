package org.auto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import org.auto.util.LogUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationCode implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private OnNewMessageListener listener;
    private String msgRegular;

    public VerificationCode(Context context){
        this(context,null,null);
    }

    public VerificationCode(Context context,OnNewMessageListener listener){
        this(context,listener,null);
    }

    /**
     * @param context
     * @param listener 新短信监听
     * @param msgRegular 对新短信处理的正则表达式，如果为空，则不进行处理
     */
    public VerificationCode(Context context, OnNewMessageListener listener,String msgRegular){
        this.context=context;
        this.listener=listener;
        this.msgRegular=msgRegular;
    }

    public VerificationCode registerTo(int id,LoaderManager manager){
        manager.initLoader(id,null,this);
        return this;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context, Uri.parse("content://sms/inbox"),
                new String[] { "_id", "address", "read", "body" },
                "read=?",
                new String[] { "0" },
                "_id desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        LogUtils.d("onLoadFinished");
        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                int smsbodyColumn = data.getColumnIndex("body");
                String smsBody = data.getString(smsbodyColumn);
                if(listener!=null){
                    String code = getDynamicPassword(smsBody,msgRegular);
                    if (!TextUtils.isEmpty(code)) {
                        listener.onNewMessage(code);
                    }
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface OnNewMessageListener {
        void onNewMessage(String msg);
    }

    private String getDynamicPassword(String str,String regular) {
        if (!str.startsWith("【抖音】")) {
            return "";
        }
        if(regular==null)return "";
        Pattern continuousNumberPattern = Pattern.compile(msgRegular);
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            LogUtils.d("match " + m.group());
            dynamicPassword = m.group();
        }
        return dynamicPassword;
    }

}
