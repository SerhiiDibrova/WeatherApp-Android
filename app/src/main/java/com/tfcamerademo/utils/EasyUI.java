package com.example.android.tflitecamerademo.utils;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EasyUI {
    public static void setButtonOnClickListener(View view,int id,OnClickListener onClickListener){
        View testView = view.findViewById(id);
        if(testView == null){
            Log.d("EasyUI", "testView is null");
            return ;
        }
        if(!Button.class.isAssignableFrom(testView.getClass())&&!ImageButton.class.isAssignableFrom(testView.getClass())){
            Log.d("EasyUI", "testView is not a button");
            return ;
        }
        if(Button.class.isAssignableFrom(testView.getClass())){
            Button button = (Button) testView;
            button.setOnClickListener(onClickListener);
        }else if(ImageButton.class.isAssignableFrom(testView.getClass())){
            ImageButton button = (ImageButton) testView;
            button.setOnClickListener(onClickListener);
        }
    }
    public static Method getMethod(Class<? extends Object> clazz, String methodName) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName);
                }
            }
        }
        return method;
    }

    public static void setButtonClickMethod(View button, final Object instance,String methodName){
        Class<? extends Object> clazz = instance.getClass();
        Method declaredMethod = null;
        boolean hasParams = false;
        try {
            declaredMethod = clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
        }
        try {
            declaredMethod = clazz.getDeclaredMethod(methodName, View.class);
            hasParams = true;
        } catch (NoSuchMethodException e) {
        }
        if(declaredMethod == null){
            return ;
        }
        final Method finalDeclaredMethod = declaredMethod;
        final boolean finalHasParams = hasParams;
        button .setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (finalHasParams){
                        finalDeclaredMethod.invoke(instance,view);
                    }else {
                        finalDeclaredMethod.invoke(instance);
                    }
                } catch (IllegalAccessException e) {
                    int i = 0 ;
                    i++;
                } catch (InvocationTargetException e) {
                    int i = 0 ;
                    i++;
                }
            }
        });
    }
    public static TextView findTextViewById(ViewGroup root,int id){
        return (TextView) root.findViewById(id);
    }
    public static String getTextViewText(View view){
        return getTextViewText(view,null);
    }
    public static String getTextViewText(View view, String defaultText){
        try {
            TextView textView = (TextView)view;
            CharSequence text = textView.getText();
            if (text == null){
                return defaultText;
            }
            return text.toString();
        }catch (Exception e){
            return defaultText;
        }
    }
    public static ViewGroup.LayoutParams fillParentLayout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    public static LinearLayout.LayoutParams scaleHeightFillWidthLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , 0 , 1);
    public static LinearLayout.LayoutParams scaleWidthFillHeightLayout = new LinearLayout.LayoutParams(0 , ViewGroup.LayoutParams.MATCH_PARENT , 1);
}
