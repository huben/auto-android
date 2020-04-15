package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;

import org.auto.util.LogUtils;
import org.auto.util.NodeUtil;
import org.auto.util.WaitUtil;

import java.util.List;

import static android.view.accessibility.AccessibilityNodeInfo.FOCUS_INPUT;

public class LoginAccessbility extends Accessbility {

    private static LoginAccessbility mInstance;


    public static LoginAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new LoginAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }

    public void login() {
        LogUtils.d("login");
        WaitUtil.sleep();
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService, accessibilityNodeInfo,"登录");
    }

    public void gotoUserCenter() {
        LogUtils.d("gotoUserCenter");
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService, accessibilityNodeInfo,"我");
    }

    public boolean quickLogin() {
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        return NodeUtil.clickNodeForTxt(mService, accessibilityNodeInfo,"本机号码一键登录");
    }

    public void switchOtherPhone() {
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService, accessibilityNodeInfo,"其他手机号码登录");
    }

    public void inputPhone() {
        WaitUtil.sleep(1500);
        AccessibilityNodeInfo node = mService.findFocus(FOCUS_INPUT);
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "18501979416");
        node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//        List<AccessibilityNodeInfo> nodes =
//                NodeUtil.findeNodeForClassName(mService.getRootInActiveWindow(), "android.widget.EditText");
//        LogUtils.d("inputPhoneNumber size " + nodes.size());
//
//        if (nodes.size() > 0) {
//            Bundle arguments = new Bundle();
//            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "18501979416");
//            nodes.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//        } else {
//            LogUtils.d("can not find input phone");
//        }
    }

    public void sendVcode() {
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService, accessibilityNodeInfo,"获取短信验证码");
    }

    public void inputVcode() {

        WaitUtil.sleep(1500);
        AccessibilityNodeInfo node = mService.findFocus(FOCUS_INPUT);
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "1234");
        node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);



//        List<AccessibilityNodeInfo> nodes =
//                NodeUtil.findeNodeForClassName(mService.getRootInActiveWindow(), "android.widget.EditText");
//        LogUtils.d("vcode size " + nodes.size());
//
//        if (nodes.size() > 1) {
//            Bundle arguments = new Bundle();
//            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "1234");
//            nodes.get(1).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//        } else {
//            LogUtils.d("can not find input vcode");
//        }

    }
}
