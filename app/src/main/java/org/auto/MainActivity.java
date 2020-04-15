package org.auto;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;

import org.auto.service.AutoService;
import org.auto.service.CmdService;
import org.auto.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.ACTION_UP;
import static android.view.KeyEvent.KEYCODE_SEARCH;

public class MainActivity extends FragmentActivity {

    private int PERMISSION_CODE = 0x0002;
    public static final int REQUEST_CODE_SETTING = 3;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS };

    List<String> mPermissionList = new ArrayList<>();

    AlertDialog mPermissionDialog;

    EditText phoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneEdit = findViewById(R.id.conn_phone);
        findViewById(R.id.open_auto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AutoService.isStart()) {
                    try {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    } catch (Exception e) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                } else {
                    Log.e("opt", "opt service start");
                    Toast.makeText(MainActivity.this, "开启无障碍服务成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.conn_btn).setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            imm.dispatchKeyEventFromInputMethod(null, new KeyEvent(ACTION_UP, KEYCODE_SEARCH));
            if (!AutoService.isStart()) {
                Toast.makeText(MainActivity.this, "请先开启无障碍服务", Toast.LENGTH_SHORT).show();
            } else {
                String phone = phoneEdit.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(MainActivity.this, "请输入连接手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                CmdService.start(MainActivity.this, phone);
            }
        });
        initPermission();
    }

    private void init() {
        new VerificationCode(this, new VerificationCode.OnNewMessageListener() {
            @Override
            public void onNewMessage(String msg) {
                LogUtils.e("vcode: " + msg);
            }
        }, "\\d{4}").registerTo(1, LoaderManager.getInstance(this));
    }

    private void initPermission() {

        mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
        } else {
            //权限已经都通过了，可以将程序继续打开了
            init();
        }
    }

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", (dialog, which) -> {
                        Log.e("permission", "设置");
                        mPermissionDialog.cancel();
                        Uri packageURI = Uri.parse("package:" + MainActivity.this.getPackageName());
                        Intent intent = new Intent(Settings.
                                ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivityForResult(intent, REQUEST_CODE_SETTING);
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        Log.e("permission", "cancel");
                        //关闭页面或者做其他操作
                        mPermissionDialog.cancel();
                        MainActivity.this.finish();
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (PERMISSION_CODE == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
        }
        if (hasPermissionDismiss) {//如果有没有被允许的权限
            showPermissionDialog();
        } else {
            //权限已经都通过了，可以将程序继续打开了
            init();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTING) {
            initPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("main", "onNewIntent");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
