package com.zhy.calc.aidl;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * @author dhl
 * 在进程里启动remoteService
 */
public class CalcManager {

      private static final String TAG = "CalcManager";
      private   ICalcAIDL iCalcAIDL;

      public static CalcManager calcManager;

      private ServiceConnection mServiceCon = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                  Log.e(TAG,"onServiceConnected");
                  iCalcAIDL = ICalcAIDL.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                  Log.e(TAG,"onServiceDisconnected");
                  iCalcAIDL = null;
            }
      };

      private CalcManager(){

      }

      public static CalcManager getInstance(){
            if(calcManager == null){
                  synchronized (CalcManager.class){
                        if(calcManager == null){
                              calcManager = new CalcManager();
                        }
                  }
            }
            return calcManager;
      }

      public ICalcAIDL getCalcAIDL() {
            return iCalcAIDL;
      }

      public void startRemoteService(Application application){
            if(iCalcAIDL == null) {
                  Intent intent = new Intent(application, CalcService.class);
                  application.bindService(intent, mServiceCon, Context.BIND_AUTO_CREATE);
            }

      }

}
