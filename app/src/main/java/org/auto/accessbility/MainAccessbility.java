package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.os.SystemClock;
import android.view.accessibility.AccessibilityNodeInfo;

import org.auto.util.LogUtils;
import org.auto.util.NodeUtil;
import org.auto.util.ScreenUtils;
import org.auto.util.WaitUtil;

public class MainAccessbility extends Accessbility {

    private static MainAccessbility mInstance;


    public static MainAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new MainAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }

    public void gotoMain() {
        LogUtils.d("gotoMain");
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService, accessibilityNodeInfo,"首页");
    }

    public void swipeUp(Context ctx) {
        final int x = ScreenUtils.getWindowsWidth(ctx) / 2;
        final int y = ScreenUtils.getWindowsHeight(ctx) / 2;
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, 0);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 100L, 50L));
        GestureDescription gesture = builder.build();
        mService.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                LogUtils.d("swipeUp");
            }
            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    public void swipeRight(Context ctx) {
        final int x = ScreenUtils.getWindowsWidth(ctx);
        final int y = ScreenUtils.getWindowsWidth(ctx) / 2;
        Path path = new Path();
        path.moveTo(x - 500, y);
        path.lineTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 100L, 50L));
        GestureDescription gesture = builder.build();
        mService.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                LogUtils.d("swipeRight");
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    public void swipeDown(Context ctx) {
        final int x = ScreenUtils.getWindowsWidth(ctx) / 2;
        final int y = ScreenUtils.getWindowsHeight(ctx) / 2;
        Path path = new Path();
        path.moveTo(x, 50);
        path.lineTo(x, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 100L, 50L));
        GestureDescription gesture = builder.build();
        mService.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                LogUtils.d("swipeUp");
            }
            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    public void swipeLeft(Context ctx) {
        final int x = ScreenUtils.getWindowsWidth(ctx);
        final int y = ScreenUtils.getWindowsWidth(ctx) / 2;
        Path path = new Path();
        path.moveTo(x - 100, y);
        path.lineTo(10, y);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(new GestureDescription.StrokeDescription(path, 100L, 50L));
        GestureDescription gesture = builder.build();
        mService.dispatchGesture(gesture, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                LogUtils.d("swipeLeft");
            }
            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    public void doubleClick(Context ctx){
        int x = ScreenUtils.getWindowsWidth(ctx) / 2;
        int y = ScreenUtils.getWindowsWidth(ctx) / 2;
        NodeUtil.clickPoint(mService, x, y, 3);
        WaitUtil.sleep(200);
        NodeUtil.clickPoint(mService, x, y, 3);
    }

    public void mainToLive() {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/b3f");
    }

    public void mainToSearch() {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/b37");
    }
}
