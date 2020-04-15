package org.auto.accessbility;

import android.accessibilityservice.AccessibilityService;

public abstract class Accessbility {

    public AccessibilityService mService;

    public void init(AccessibilityService service) {
        this.mService = service;
    }

}
