package com.zhy.calc.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 基于Messenger 的进程通信
 */
public class MessengerService extends Service {

    private static  final int MSG_SUM = 0x110;

    private Messenger messenger = new Messenger(new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Message msgToClient = Message.obtain(msg);
            switch (msgToClient.what){
                case MSG_SUM:
                    msgToClient.what = MSG_SUM;
                    try {
                        Thread.sleep(500);
                        msgToClient.arg2 = msgToClient.arg1 +msgToClient.arg2;
                        msg.replyTo.send(msgToClient);
                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    });
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
