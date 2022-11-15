package com.zhy.calc.aidl;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.Toast;

/**
 * @author dhl
 * 在进程里启动remoteService
 */
public class CalcPlusManager {

      private static final String TAG = "CalcManager";
      private   IBinder mPlusBinder;

      public static CalcPlusManager calcManager;

      private ServiceConnection mServiceCon = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                  Log.e(TAG,"onServiceConnected");
                  mPlusBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                  Log.e(TAG,"onServiceDisconnected");
                  mPlusBinder = null;
            }
      };

      private CalcPlusManager(){

      }

      public static CalcPlusManager getInstance(){
            if(calcManager == null){
                  synchronized (CalcPlusManager.class){
                        if(calcManager == null){
                              calcManager = new CalcPlusManager();
                        }
                  }
            }
            return calcManager;
      }

      public IBinder getmPlusBinder() {
            return mPlusBinder;
      }

      public void startRemoteService(Application application){
            if(mPlusBinder == null) {
                  Intent intent = new Intent(application, CalcPlusService.class);
                  application.bindService(intent, mServiceCon, Context.BIND_AUTO_CREATE);
            }

      }

      /**
       * 乘法
       */
      public void multiInvoked(){

            if (mPlusBinder ==null){
                  Toast.makeText(App.context,"未连接服务端或者服务端被杀死",Toast.LENGTH_LONG).show();
            }else {
                  android.os.Parcel _data = android.os.Parcel.obtain();
                  android.os.Parcel _reply = android.os.Parcel.obtain();
                  int _result ;
                  try {
                        _data.writeInterfaceToken("CalcPlusService");
                        _data.writeInt(50);
                        _data.writeInt(12);
                        mPlusBinder.transact(0x110,_data,_reply,0);
                        _reply.readException();
                        _result = _reply.readInt();
                        Toast.makeText(App.context,"result = "+_result,Toast.LENGTH_LONG).show();

                  } catch (RemoteException e) {
                        e.printStackTrace();
                  }finally {
                        _reply.recycle();
                        _data.recycle();
                  }
            }

      }



}
