package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;

import org.auto.App;
import org.auto.util.LogUtils;
import org.auto.util.NodeUtil;
import org.auto.util.WaitUtil;

import java.util.List;

import static android.view.KeyEvent.ACTION_UP;
import static android.view.KeyEvent.KEYCODE_SEARCH;

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
//        boolean  b = NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/cyf");
//        LogUtils.d(b + "listview");
        NodeUtil.clickListViewFirst(mService, "com.ss.android.ugc.aweme:id/cyf");
    }

    public void clickAvatar() {
        NodeUtil.clickNodeById(mService, "com.ss.android.ugc.aweme:id/btx");
    }
    
}
