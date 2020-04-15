package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;

import org.auto.util.NodeUtil;
import org.auto.util.WaitUtil;

public class LiveAccessbility extends Accessbility {

    private static LiveAccessbility mInstance;


    public static LiveAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new LiveAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }

    public void inputComment(String msg) {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/aqx");
        WaitUtil.sleep(1000);
        NodeUtil.inputText(mService, msg);
    }

    public void sendComment() {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/ed6");
    }
    
}
