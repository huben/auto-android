package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;
import org.auto.util.NodeUtil;
import org.auto.util.WaitUtil;

public class SearchAccessbility extends Accessbility {

    private static SearchAccessbility mInstance;


    public static SearchAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new SearchAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }

    public void inputSearch(String msg) {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/ai2");
        WaitUtil.sleep(1000);
        NodeUtil.inputText(mService, msg);
        WaitUtil.sleep(1000);
        NodeUtil.clickRightBottom(mService);
        WaitUtil.sleep(3000);
        MainAccessbility.getInstance().swipeLeft(mService);
        WaitUtil.sleep(2000);
        MainAccessbility.getInstance().swipeLeft(mService);
        WaitUtil.sleep(1000);
        NodeUtil.clickListViewFirst(mService, "com.ss.android.ugc.aweme:id/ch7");
    }

    public void clickAvatar() {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/btx");
    }
    
}
