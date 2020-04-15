package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import org.auto.util.LogUtils;
import org.auto.util.NodeUtil;

import java.util.List;

public class CommonAccessbility extends Accessbility {

    private static CommonAccessbility mInstance;


    public static CommonAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new CommonAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }

    public void ignoreUpdate(){
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> accessibilityNodeInfoList = NodeUtil.findNodeForText(accessibilityNodeInfo,"以后再说");
        if (accessibilityNodeInfoList.size() > 0){
            accessibilityNodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            LogUtils.d("忽略升级成功！");
        }
    }

    public void ignoreFrend(){
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService,accessibilityNodeInfo,"取消");
        LogUtils.d("取消好友通讯录！");
    }

    public void ignoreChildMode(){
        AccessibilityNodeInfo accessibilityNodeInfo = mService.getRootInActiveWindow();
        NodeUtil.clickNodeForTxt(mService,accessibilityNodeInfo,"我知道了");
        LogUtils.d("取消好友通讯录！");
    }
    
}
