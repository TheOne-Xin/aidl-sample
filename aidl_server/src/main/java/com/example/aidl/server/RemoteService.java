package com.example.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class RemoteService extends Service {
    private final String TAG = "RemoteService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        Log.d(TAG, "onBind");
        return binder;
    }

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        @Override
        public int getPid() {
            return Process.myPid();
        }

        @Override
        public void addRectInOut(Rect rect) {
            Log.d(TAG, "addRectInOut:" + rect.toString());
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) {
            Log.d(TAG, "basicTypes anInt:" + anInt + ";aLong:" + aLong + ";aBoolean:" + aBoolean + ";aFloat:" + aFloat + ";aDouble:" + aDouble + ";aString:" + aString);
        }
    };
}
