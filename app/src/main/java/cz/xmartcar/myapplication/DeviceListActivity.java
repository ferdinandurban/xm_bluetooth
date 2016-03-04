/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cz.xmartcar.communication.bluetooth.BluetoothMessageStates;
import cz.xmartcar.communication.bluetooth.BluetoothProtocol;
import cz.xmartcar.communication.bluetooth.BluetoothStatus;
import cz.xmartcar.communication.bluetooth.DeviceList;

public class DeviceListActivity extends AppCompatActivity {
    private BluetoothProtocol mBTProtocol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        mBTProtocol = new BluetoothProtocol(this);

        if(!mBTProtocol.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        mBTProtocol.setOnDataReceivedListener(new BluetoothProtocol.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(DeviceListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        mBTProtocol.setBluetoothConnectionListener(new BluetoothProtocol.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext(), "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = (Button)findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(mBTProtocol.getServiceState() == BluetoothStatus.STATE_CONNECTED) {
                    mBTProtocol.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothMessageStates.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        mBTProtocol.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!mBTProtocol.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothMessageStates.REQUEST_ENABLE_BT);
        } else {
            if(!mBTProtocol.isServiceAvailable()) {
                mBTProtocol.setupService();
                mBTProtocol.startService(BluetoothMessageStates.DEVICE_ANDROID);
                setup();
            }
        }
    }

    public void setup() {
        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mBTProtocol.send("Text", true);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothMessageStates.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                mBTProtocol.connect(data);
        } else if(requestCode == BluetoothMessageStates.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                mBTProtocol.setupService();
                mBTProtocol.startService(BluetoothMessageStates.DEVICE_ANDROID);
                setup();
            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
