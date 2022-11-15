package com.zhy.calc.aidl;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CalcManager.getInstance().startRemoteService(this);
        CalcPlusManager.getInstance().startRemoteService(this);
    }
}
