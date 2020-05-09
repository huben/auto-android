package org.auto.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import org.auto.BuildConfig;
import org.auto.util.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class CmdService extends Service {

    public static String HANDLER_CLIENT = "HANDLER_CLIENT";
    public static int ONLINE = -2;

    public static String ACTICON_START_CMD_SERVICE = "ACTICON_START_CMD_SERVICE";
    public static String ACTICON_SEND_CMD = "ACTICON_SEND_CMD";
    public static String EXTRA_PHONE = "EXTRA_PHONE";
    public static String EXTRA_TEXT = "EXTRA_TEXT";
    public static String EXTRA_CMD = "EXTRA_CMD";

    public static void start(Context ctx, String phone) {
        Intent intent = new Intent(ctx, CmdService.class);
        intent.setAction(ACTICON_START_CMD_SERVICE);
        intent.putExtra(EXTRA_PHONE, phone);
        ctx.startService(intent);
    }

    public static void send(Context ctx,int cmd, String text) {
        Intent intent = new Intent(ctx, CmdService.class);
        intent.setAction(ACTICON_START_CMD_SERVICE);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_CMD, cmd);
        ctx.startService(intent);
    }

    Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            socket = IO.socket(BuildConfig.WEBSOCKET);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LogUtils.d("EVENT_CONNECT");
                }
            });
            socket.on(HANDLER_CLIENT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    if (args.length > 0)
                        LogUtils.e(args[0].toString());
                        try {
                            JSONObject obj = new JSONObject(args[0].toString());
                            int cmd = obj.optInt("cmd");
                            String text = obj.optString("text");

                            Message msg = Message.obtain();
                            msg.what = cmd;
                            msg.obj = text;
                            AutoService.getMsgHandler().sendMessage(msg);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                }
            });
            socket.on("disconnect",new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LogUtils.d("disconnect");
                }
            });
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (TextUtils.equals(intent.getAction(), ACTICON_START_CMD_SERVICE)) {
            String phone = intent.getStringExtra(EXTRA_PHONE);
            sendMsg(ONLINE, phone);
        } else if (TextUtils.equals(intent.getAction(), ACTICON_SEND_CMD)) {
            int cmd = intent.getIntExtra(EXTRA_CMD, -1);
            String text = intent.getStringExtra(EXTRA_TEXT);
            sendMsg(cmd, text);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMsg(int cmd, String text) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("cmd", cmd);
            obj.put("text", text);
            LogUtils.d(cmd + text);
            socket.emit(HANDLER_CLIENT, obj.toString(), new Ack() {
                @Override
                public void call(Object... args) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
