package com.zhy.calc.aidl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.calc.aidl.service.MessengerService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "client";

    private static final int MSG_SUM = 0x110;

    private Button addButton;

    private LinearLayout linearLayout;

    private int count;

    //private ICalcAIDL iCalcAIDL;

    private Messenger mService;

    private ServiceConnection mServiceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            //iCalcAIDL = ICalcAIDL.Stub.asInterface(service);
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
            mService = null;
        }
    };

    private Messenger messenger = new Messenger(new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_SUM:
                    TextView tv = (TextView) linearLayout.findViewById(msg.arg1);

                    //String bean = (String) msg.getData().get("bean");
                    tv.setText(tv.getText()+"=>"+msg.arg2);
                    break;
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindMessenger();
        linearLayout = findViewById(R.id.id_ll_container);
        addButton = findViewById(R.id.id_btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = count++;
                int b = (int)(Math.random() *100);
                TextView tv = new TextView(MainActivity.this);
                tv.setText(a+" + "+b+" = caculating ....");
                tv.setId(a);
                linearLayout.addView(tv);
                Message clientMessage = Message.obtain(null,MSG_SUM,a,b);
//                Bundle bundle = new Bundle();
//
//                Bean bean = new Bean();
//                bean.pram1 = a;
//                bean.pram2 = b;
//                //clientMessage.obj = bean;
//                bundle.putString("bean","test");
//                bundle.putParcelable("bean1",bean);
//                clientMessage.setData(bundle);
                clientMessage.replyTo = messenger;
                try {
                    mService.send(clientMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void bindMessenger(){
        Intent intent = new Intent(MainActivity.this, MessengerService.class);
        bindService(intent, mServiceCon, Context.BIND_AUTO_CREATE);
    }
    public void bindService(View view) {
        Intent intent = new Intent(MainActivity.this, MessengerService.class);
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