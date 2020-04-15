package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;

import org.auto.util.NodeUtil;

public class MineAccessbility extends Accessbility {

    private static MineAccessbility mInstance;


    public static MineAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new MineAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }


    public void clickAvatar() {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/bef");
    }
}
