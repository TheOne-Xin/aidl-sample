package com.example.aidl.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidl.server.IRemoteService;
import com.example.aidl.server.Rect;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ClientActivity";
    private IRemoteService iRemoteService;
    private Button mBindServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBindServiceButton = findViewById(R.id.btn_bind_service);
        mBindServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mBindServiceButton.getText().toString();
                if ("Bind Service".equals(text)) {
                    Intent intent = new Intent();
                    intent.setAction("com.example.aidl");
                    intent.setPackage("com.example.aidl.server");
                    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                } else {
                    unbindService(mConnection);
                    mBindServiceButton.setText("Bind Service");
                }
            }
        });
    }

    ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            iRemoteService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            iRemoteService = IRemoteService.Stub.asInterface(service);
            try {
                int pid = iRemoteService.getPid();
                int currentPid = Process.myPid();
                Log.d(TAG, "currentPID: " + currentPid + ", remotePID: " + pid);
                iRemoteService.basicTypes(12, 123, true, 123.4f, 123.45,
                        "服务端你好，我是客户端");
                iRemoteService.addRectInOut(new Rect(1, 2, 3, 4));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBindServiceButton.setText("Unbind Service");
        }
    };
}
