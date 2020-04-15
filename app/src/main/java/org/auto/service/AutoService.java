package org.auto.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;


import org.auto.MainActivity;
import org.auto.Utils;
import org.auto.accessbility.CommonAccessbility;
import org.auto.accessbility.LiveAccessbility;
import org.auto.accessbility.LoginAccessbility;
import org.auto.accessbility.MainAccessbility;
import org.auto.accessbility.MineAccessbility;
import org.auto.accessbility.SearchAccessbility;
import org.auto.util.LogUtils;
import org.auto.util.NodeUtil;
import org.auto.util.WaitUtil;

import static org.auto.Constant.CMD_BACK;
import static org.auto.Constant.CMD_DOUBLE_CLICK;
import static org.auto.Constant.CMD_LIVE_INPUT_COMMENT;
import static org.auto.Constant.CMD_LIVE_SEND_COMMENT;
import static org.auto.Constant.CMD_MAIN_LIVE;
import static org.auto.Constant.CMD_MAIN_SEARCH;
import static org.auto.Constant.CMD_MINE_AVATAR;
import static org.auto.Constant.CMD_ONLINE;
import static org.auto.Constant.CMD_OPEN;
import static org.auto.Constant.CMD_SEARCH_INPUT;
import static org.auto.Constant.CMD_SEARCH_RESULT_AVATAR;
import static org.auto.Constant.CMD_SWIPE_DOWN;
import static org.auto.Constant.CMD_SWIPE_LEFT;
import static org.auto.Constant.CMD_SWIPE_RIGHT;
import static org.auto.Constant.CMD_SWIPE_UP;
import static org.auto.Constant.DOUYIN_LOGIN_ACTIVITY;
import static org.auto.Constant.DOUYIN_MAIN_ACTIVITY;
import static org.auto.Constant.DOUYIN_PKG_NAME;
import static org.auto.Constant.DOUYIN_UPDATE_LEVEL;

public class AutoService extends AccessibilityService {

    public static AutoService mService;

    public static boolean isStart() {
        return mService != null;
    }

    static Handler msgHandler;

    public static Handler getMsgHandler() {
        return msgHandler;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getPackageName().equals(DOUYIN_PKG_NAME)) {
            String className = event.getClassName().toString();
            LogUtils.e(className);
            switch (className) {
                case DOUYIN_LOGIN_ACTIVITY:
//                    if (!LoginAccessbility.getInstance().quickLogin()) {
//                        NodeUtil.tapBack(mService);
//                    } else {
//                    }
//                    LoginAccessbility.getInstance().switchOtherPhone();
//                    LoginAccessbility.getInstance().inputPhone();
//                    LoginAccessbility.getInstance().sendVcode();
//                    LoginAccessbility.getInstance().inputVcode();
//                    LoginAccessbility.getInstance().login();

                    break;
                case DOUYIN_MAIN_ACTIVITY:
//                    if (isLogin) {
//                        for (int i = 0; i < 4; i ++) {
//                            MainAccessbility.getInstance().swipeLeft(AutoService.this);
//                            WaitUtil.sleep(2000);
//                            MainAccessbility.getInstance().swipeRight(AutoService.this);
//                            WaitUtil.sleep(2000);
//                            MainAccessbility.getInstance().doubleClick(AutoService.this);
//                            WaitUtil.sleep(2000);
//                            MainAccessbility.getInstance().swipeUp(AutoService.this);
//                            WaitUtil.sleep(2000);
//                        }
//                    } else {
//                        LoginAccessbility.getInstance().gotoUserCenter();
//                    }
                    break;
                case DOUYIN_UPDATE_LEVEL:
//                    CommonAccessbility.getInstance().ignoreUpdate();
                    break;
                default:
                    LogUtils.d(event.toString());
                    break;

            }
        }

    }

    private void handleMsg() {
        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case CMD_ONLINE:
                        Toast.makeText(mService, "连接成功", Toast.LENGTH_SHORT).show();
                        break;
                    case CMD_OPEN:
                        Utils.openDouyin(mService);
                        break;
                    case CMD_SWIPE_UP:
                        MainAccessbility.getInstance().swipeUp(mService);
                        break;
                    case CMD_SWIPE_RIGHT:
                        MainAccessbility.getInstance().swipeRight(mService);
                        break;
                    case CMD_SWIPE_DOWN:
                        MainAccessbility.getInstance().swipeDown(mService);
                        break;
                    case CMD_SWIPE_LEFT:
                        MainAccessbility.getInstance().swipeLeft(mService);
                        break;
                    case CMD_DOUBLE_CLICK:
                        MainAccessbility.getInstance().doubleClick(mService);
                        break;
                    case CMD_BACK:
                        NodeUtil.tapBack(mService);
                        break;
                    case CMD_MAIN_LIVE:
                        MainAccessbility.getInstance().mainToLive();
                        break;
                    case CMD_LIVE_INPUT_COMMENT:
                        LiveAccessbility.getInstance().inputComment(msg.obj.toString());
                        break;
                    case CMD_LIVE_SEND_COMMENT:
                        LiveAccessbility.getInstance().sendComment();
                        break;
                    case CMD_MAIN_SEARCH:
                        MainAccessbility.getInstance().mainToSearch();
                        break;
                    case CMD_SEARCH_INPUT:
                        SearchAccessbility.getInstance().inputSearch(msg.obj.toString());
                        break;
                    case CMD_SEARCH_RESULT_AVATAR:
                        SearchAccessbility.getInstance().clickAvatar();
                        break;
                    case CMD_MINE_AVATAR:
                        MineAccessbility.getInstance().clickAvatar();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected void onServiceConnected() {
        Log.e("opt", "onServiceConnected");
        super.onServiceConnected();
        mService = this;
        CommonAccessbility.getInstance().init(this);
        LoginAccessbility.getInstance().init(this);
        MainAccessbility.getInstance().init(this);

        LiveAccessbility.getInstance().init(this);
        SearchAccessbility.getInstance().init(this);
        MineAccessbility.getInstance().init(this);

        handleMsg();
    }

    @Override
    public void onInterrupt() {
        Log.e("opt", "onInterrupt");
        mService = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mService = null;
    }
}
