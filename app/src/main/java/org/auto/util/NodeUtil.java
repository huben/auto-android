package org.auto.util;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

import static android.view.accessibility.AccessibilityNodeInfo.FOCUS_INPUT;

public class NodeUtil {

    private static final String TAG = "NodeUtil";

    public static List<AccessibilityNodeInfo> findNodeForText(AccessibilityNodeInfo accessibilityNodeInfo, String text){
        List<AccessibilityNodeInfo> targetNodeInfo = new ArrayList<>();
        if (accessibilityNodeInfo == null){
            return targetNodeInfo;
        }
        int size = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i<size; i++){
           AccessibilityNodeInfo childNodeInfo = accessibilityNodeInfo.getChild(i);
           if (childNodeInfo == null){
               return null;
           }
           List<AccessibilityNodeInfo> textNodeInfo = childNodeInfo.findAccessibilityNodeInfosByText(text);
           if (textNodeInfo.size() > 0){
               targetNodeInfo.addAll(childNodeInfo.findAccessibilityNodeInfosByText(text));
           }else {
              findNodeForText(childNodeInfo,text);
           }
        }
        return targetNodeInfo;
    }


    public static List<AccessibilityNodeInfo> targetNodeInfo = new ArrayList<>();

    public static List<AccessibilityNodeInfo> findeNodeForClassName(AccessibilityNodeInfo accessibilityNodeInfo,String className){
        int size = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i<size; i++){
            AccessibilityNodeInfo childNodeInfo = accessibilityNodeInfo.getChild(i);
            Log.d(TAG, "findeNodeForClassName: className = "+childNodeInfo.getClassName());
            Log.d(TAG, "findeNodeForClassName: result = "+childNodeInfo.getClassName().equals(className));
            if (childNodeInfo.getClassName().equals(className)){
                targetNodeInfo.add(childNodeInfo);
            }else {
               findeNodeForClassName(childNodeInfo,className);
            }
        }
        return targetNodeInfo;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void clickPoint(AccessibilityService service, float x1, float y1, long duration) {
        Path path = new Path();
        path.moveTo(x1, y1);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.StrokeDescription(path, 0, duration))
                .build();
        boolean b = service.dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Log.e("TIAOSHI###", "点击结束..." + gestureDescription.getStrokeCount());
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Log.e("TIAOSHI###", "点击取消");
            }
        }, null);
    }


    public static AccessibilityNodeInfo getNodeByClassName(StringBuilder sb, AccessibilityNodeInfo nodeInfo, String... classNames) {
        if (nodeInfo.getChildCount() == 0) {
            return null;
        }

        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
            for (String className : classNames) {
                if (childNodeInfo.getClassName().toString().equals(className) && isNumericZidai(childNodeInfo.getText())) {
                    sb.append(childNodeInfo.getText()+",");
                }
            }
            getNodeByClassName(sb, childNodeInfo, classNames);
        }
        return null;
    }

    public static boolean isNumericZidai(CharSequence charSequence) {
        if (charSequence ==null){
            return false;
        }
        String str = charSequence.toString();
        String lasetStr = str.substring(str.length()-1);
        if ("w".equals(lasetStr)){
            str = str.substring(0,str.length()-1);
        }
        try{
            double d = Double.valueOf(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void clickNodeForTxt(AccessibilityService service, AccessibilityNodeInfo nodeInfo, String txt, int offsetX, int offsetY){
        clickNodeForTxt(service,nodeInfo,txt,0,null,offsetX,offsetY);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean clickNodeForTxt(AccessibilityService service, AccessibilityNodeInfo nodeInfo, String txt){
        return clickNodeForTxt(service,nodeInfo,txt,0,null,0,0);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean clickNodeForTxt(AccessibilityService service, AccessibilityNodeInfo nodeInfo, String txt, String cls){
        return clickNodeForTxt(service,nodeInfo,txt,0,cls,0,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean clickNedeForTxt(AccessibilityService service, AccessibilityNodeInfo nodeInfo, String txt, int index){
        return clickNodeForTxt(service,nodeInfo,txt,index,null,0,0);
    }

    /**
     * 根据txt点击节点
     * @param service
     * @param nodeInfo
     * @param txt
     * @param index 第几个
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean clickNodeForTxt(AccessibilityService service, AccessibilityNodeInfo nodeInfo, String txt, int index, String className, int offsetX, int offsetY){
        List<AccessibilityNodeInfo> accessibilityNodeInfoList = NodeUtil.findNodeForText(nodeInfo,txt);
        for (int i = accessibilityNodeInfoList.size()-1;i>=0;i--){

            if (accessibilityNodeInfoList.get(i).getClassName().equals("android.widget.TextView")){
                if (!txt.equals(accessibilityNodeInfoList.get(i).getText())){
                    accessibilityNodeInfoList.remove(i);
                    continue;
                }

            }else {
                if (!txt.equals(accessibilityNodeInfoList.get(i).getContentDescription())){
                    accessibilityNodeInfoList.remove(i);
                    continue;
                }
            }
            if (className != null){
                if (!className.equals(accessibilityNodeInfoList.get(i).getClassName())){
                    accessibilityNodeInfoList.remove(i);
                    continue;
                }
            }

        }
        if (accessibilityNodeInfoList!=null && accessibilityNodeInfoList.size() > index){
//            boolean clicked = accessibilityNodeInfoList.get(index).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Rect rect = new Rect();
            accessibilityNodeInfoList.get(index).getBoundsInScreen(rect);
//                Log.d(TAG, "clickNodeForTxt: left = "+rect.left);
//                Log.d(TAG, "clickNodeForTxt: right = "+rect.right);
//                Log.d(TAG, "clickNodeForTxt: top = "+rect.top);
//                Log.d(TAG, "clickNodeForTxt: bottom = "+rect.bottom);
            int x = rect.centerX() + offsetX;
            int y = rect.centerY() + offsetY;
            Log.d(TAG, "clickNodeForTxt: x = "+x);
            Log.d(TAG, "clickNodeForTxt: y = "+y);
            clickPoint(service,x+20,y+10,200);
            return true;
        } else {
            return false;
        }
    }

    public static boolean clickNodeById(AccessibilityService service, String id) {
        LogUtils.d("clickNodeById" + id);
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            nodeInfo.recycle();
            boolean isSuccess = false;
            for (AccessibilityNodeInfo node: nodes) {
                if (node.isClickable()) {
                    isSuccess = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
            return isSuccess;
        } else {
            return false;
        }
    }

    public static boolean clickListViewFirst(AccessibilityService service, String id) {
        AccessibilityNodeInfo nodeInfo = service.getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            nodeInfo.recycle();
            boolean isSuccess = false;
            if (nodes != null && nodes.size() > 0) {
                Rect rect = new Rect();
                nodes.get(0).getBoundsInScreen(rect);
//                Log.d(TAG, "clickNodeForTxt: left = "+rect.left);
//                Log.d(TAG, "clickNodeForTxt: right = "+rect.right);
//                Log.d(TAG, "clickNodeForTxt: top = "+rect.top);
//                Log.d(TAG, "clickNodeForTxt: bottom = "+rect.bottom);
                int x = rect.left + 10;
                int y = rect.top + 10;
                Path path = new Path();
                path.moveTo(x, y);
                GestureDescription.Builder builder = new GestureDescription.Builder();
                GestureDescription gestureDescription = builder
                        .addStroke(new GestureDescription.StrokeDescription(path, 0, 200))
                        .build();
                boolean b = service.dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
                    @Override
                    public void onCompleted(GestureDescription gestureDescription) {
                        super.onCompleted(gestureDescription);
                        Log.e("TIAOSHI###", "点击结束..." + gestureDescription.getStrokeCount());
                    }

                    @Override
                    public void onCancelled(GestureDescription gestureDescription) {
                        super.onCancelled(gestureDescription);
                        Log.e("TIAOSHI###", "点击取消");
                    }
                }, null);
            }
            return isSuccess;
        } else {
            return false;
        }
    }

    public static boolean inputText(AccessibilityService service, String msg) {
        WaitUtil.sleep(500);
        AccessibilityNodeInfo node = service.findFocus(FOCUS_INPUT);
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, msg);
        return node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    public static void addTextToNode(AccessibilityService service, AccessibilityNodeInfo nodeInfo, String oldTxt, String newTxt){

        List<AccessibilityNodeInfo> accessibilityNodeInfoList = NodeUtil.findNodeForText(nodeInfo, oldTxt);
        if (accessibilityNodeInfoList.size() > 0){
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, newTxt);
            accessibilityNodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,arguments);
            LogUtils.d("添加标题成功！");
        }else {
            accessibilityNodeInfoList = findeNodeForClassName(nodeInfo,"android.widget.EditText");
            if (accessibilityNodeInfoList!= null && accessibilityNodeInfoList.size()>0){
                Bundle arguments = new Bundle();
                arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, newTxt);
                accessibilityNodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT,arguments);
                LogUtils.d("添加标题成功！");
            }
        }
    }

    public static void goHome(AccessibilityService service){
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }

    public static void topSlide(AccessibilityService service){
        service.performGlobalAction(AccessibilityService.GESTURE_SWIPE_UP);
    }

    public static boolean tapBack(AccessibilityService service) {
        return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }










}
