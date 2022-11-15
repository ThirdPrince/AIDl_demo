package com.zhy.calc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 不依赖Aidl
 *
 */
public class CalcPlusService extends Service {

    private static final String TAG = "CalcPlusService";
    public static final String DESCRIPTOR = TAG;

    private MyBinder myBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    /**
     * 自定义Binder
     */
    private class MyBinder extends Binder{
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {

            switch (code){
                case 0x110: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = _arg0 * _arg1;
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }

                case 0x111: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1 ;
                    _arg1 = data.readInt();
                    int _result = _arg0 / _arg1;
                    reply.writeInt(_result);
                    return true;
                }

            }
            return  super.onTransact(code,data,reply,flags);
        }
    }
}
