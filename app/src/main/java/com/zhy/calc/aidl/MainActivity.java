package com.zhy.calc.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "client";

    //private ICalcAIDL iCalcAIDL;

    private ServiceConnection mServiceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            //iCalcAIDL = ICalcAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
            //iCalcAIDL = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bindService(View view) {
        Intent intent = new Intent(MainActivity.this, CalcService.class);
        bindService(intent, mServiceCon, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        unbindService(mServiceCon);
    }

    public void addInvoked(View view) {
        if (CalcManager.getInstance().getCalcAIDL() != null) {
            try {
                int addRes = CalcManager.getInstance().getCalcAIDL().add(12, 12);
                Toast.makeText(this, addRes + "", Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "服务被杀死，请重新绑定服务端", Toast.LENGTH_LONG).show();
        }
    }

    public void minInvoked(View view) {
        if (CalcManager.getInstance().getCalcAIDL() != null) {
            try {
                int addRes = CalcManager.getInstance().getCalcAIDL().min(50, 12);
                Toast.makeText(this, addRes + "", Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "服务被杀死，请重新绑定服务端", Toast.LENGTH_LONG).show();
        }
    }

    public void multiInvoked(View view) {
        if (CalcPlusManager.getInstance().getmPlusBinder() != null) {

            CalcPlusManager.getInstance().multiInvoked();

        } else {
            Toast.makeText(this, "服务被杀死，请重新绑定服务端", Toast.LENGTH_LONG).show();
        }
    }

}