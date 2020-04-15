package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import org.auto.util.LogUtils;
import org.auto.util.NodeUtil;

import java.util.List;

public class InstallAccessbility extends Accessbility {

    private static InstallAccessbility mInstance;


    public static InstallAccessbility getInstance(){
        if (mInstance == null) {
            mInstance = new InstallAccessbility();
        }
        return mInstance;
    }

    @Override
    public void init(AccessibilityService service) {
        super.init(service);
    }


    
}
