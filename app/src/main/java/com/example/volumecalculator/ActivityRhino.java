package com.example.volumecalculator;

import org.mozilla.javascript.Scriptable;

public class ActivityRhino {
    public static String rhinoLibrary(String toString) {
        String data = toString;

        data = data.replaceAll("×" , "*");
        data = data.replaceAll("÷" , "/");
        data = data.replaceAll("%" , "/100");

        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        Scriptable scriptable = rhino.initStandardObjects();

        return rhino.evaluateString(scriptable , data , "Javascript" , 1 , null).toString();
    }
}
