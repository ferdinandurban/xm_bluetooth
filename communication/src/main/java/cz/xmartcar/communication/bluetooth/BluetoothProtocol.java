/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothProtocol {
    private static final String TAG = "BluetoothProtocol";

    // Listener for Bluetooth Status & Connection
    private BluetoothStateListener      mBluetoothStateListener = null;
    private OnDataReceivedListener      mDataReceivedListener = null;
    private BluetoothConnectionListener mBluetoothConnectionListener = null;
    private AutoConnectionListener      mAutoConnectionListener = null;
    
    // Context from activity which call this class
    private Context mContext;
    
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;

    // Local Bluetooth LE Scanner
    private BluetoothLeScanner mBluetoothLeScanner = null;

    // Member object for the chat services
    private BluetoothService mBtService = null;
    
    // Name and Address of the connected device
    private String mDeviceName = null;
    private String mDeviceAddress = null;

    private boolean isAutoConnecting = false;
    private boolean isAutoConnectionEnabled = false;
    private boolean isConnected = false;
    private boolean isConnecting = false;
    private boolean isServiceRunning = false;
    
    private String keyword = "";
    private boolean isAndroid = true;
    
    private BluetoothConnectionListener bcl;
    private int c = 0;
    
    public BluetoothProtocol(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

    }
    
    public interface BluetoothStateListener {
        public void onServiceStateChanged(int state);
    }
    
    public interface OnDataReceivedListener {
        public void onDataReceived(byte[] data, String message);
    }
    
    public interface BluetoothConnectionListener {
        public void onDeviceConnected(String name, String address);
        public void onDeviceDisconnected();
        public void onDeviceConnectionFailed();
    }
    
    public interface AutoConnectionListener {
        public void onAutoConnectionStarted();
        public void onNewConnection(String name, String address);
    }
    
    public boolean isBluetoothAvailable() {
        try {
            if (mBluetoothAdapter == null || mBluetoothAdapter.getAddress().equals(null))
                return false;
        } catch (NullPointerException e) {
             return false;
        }
        return true;
    }
    
    public boolean isBluetoothEnabled() {
        return mBluetoothAdapter.isEnabled();
    }
    
    public boolean isServiceAvailable() {
        return mBtService != null;
    }
    
    public boolean isAutoConnecting() {
        return isAutoConnecting;
    }
    
    public boolean startDiscovery() {
        return mBluetoothAdapter.startDiscovery();
    }

    public void startLeScan(){
        ScanFilter serviceFilter = new ScanFilter.Builder()
                .setServiceUuid(XmartcarService.XMARTCAR_SERVICE)
                .build();
        ArrayList<ScanFilter> filters = new ArrayList<>();
        filters.add(serviceFilter);
        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                .build();

        mBluetoothLeScanner.startScan(filters, settings, mLEScanCallback);
    }


    private ScanCallback mLEScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.d(TAG, "onScanResult");
            processResult(result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            Log.d(TAG, "onBatchScanResults: "+results.size()+" results");
            for (ScanResult result : results) {
                processResult(result);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.w(TAG, "LE Scan Failed: "+errorCode);
        }

        private void processResult(ScanResult result) {
            Log.i(TAG, "New LE Device: " + result.getDevice().getName() + " @ " + result.getRssi());

//            XmartcarService carService = new XmartcarService(result.getScanRecord(),
//                    result.getDevice().getAddress(),
//                    result.getRssi());
//
        }
    };

    public boolean isDiscovery() {
        return mBluetoothAdapter.isDiscovering();
    }
    
    public boolean cancelDiscovery() {
        return mBluetoothAdapter.cancelDiscovery();
    }
    
    public void setupService() {
        mBtService = new BluetoothService(mContext, mHandler);
    }
    
    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }
    
    public BluetoothStatus getServiceState() {
        if(mBtService != null)
            return mBtService.getState();
        else 
            return BluetoothStatus.STATE_NULL;
    }
    
    public void startService(boolean isAndroid) {
        if (mBtService != null) {
            if (mBtService.getState() == BluetoothStatus.STATE_NONE) {
                isServiceRunning = true;
                mBtService.start(isAndroid);
                BluetoothProtocol.this.isAndroid = isAndroid;
            }
        }
    }
    
    public void stopService() {
        if (mBtService != null) {
            isServiceRunning = false;
            mBtService.stop();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (mBtService != null) {
                    isServiceRunning = false;
                    mBtService.stop();
                }
            }
        }, 500);
    }
    
    public void setDeviceTarget(boolean isAndroid) {
        stopService();
        startService(isAndroid);
        BluetoothProtocol.this.isAndroid = isAndroid;
    }
    
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case BluetoothMessageStates.MESSAGE_WRITE:
                break;
            case BluetoothMessageStates.MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                String readMessage = new String(readBuf);
                if(readBuf != null && readBuf.length > 0) {
                    if(mDataReceivedListener != null)
                        mDataReceivedListener.onDataReceived(readBuf, readMessage);
                }
                break;
            case BluetoothMessageStates.MESSAGE_DEVICE_NAME:
                mDeviceName = msg.getData().getString(BluetoothMessageStates.DEVICE_NAME);
                mDeviceAddress = msg.getData().getString(BluetoothMessageStates.DEVICE_ADDRESS);
                if(mBluetoothConnectionListener != null)
                    mBluetoothConnectionListener.onDeviceConnected(mDeviceName, mDeviceAddress);
                isConnected = true;
                break;
            case BluetoothMessageStates.MESSAGE_TOAST:
                Toast.makeText(mContext, msg.getData().getString(BluetoothMessageStates.TOAST)
                        , Toast.LENGTH_SHORT).show();
                break;
            case BluetoothMessageStates.MESSAGE_STATE_CHANGE:
                if(mBluetoothStateListener != null)
                    mBluetoothStateListener.onServiceStateChanged(msg.arg1);
                if(isConnected && msg.arg1 != BluetoothStatus.STATE_CONNECTED.getValue()) {
                    if(mBluetoothConnectionListener != null)
                        mBluetoothConnectionListener.onDeviceDisconnected();
                    if(isAutoConnectionEnabled) {
                        isAutoConnectionEnabled = false;
                        autoConnect(keyword);
                    }
                    isConnected = false;
                    mDeviceName = null;
                    mDeviceAddress = null;
                }
                
                if(!isConnecting && msg.arg1 == BluetoothStatus.STATE_CONNECTING.getValue()) {
                    isConnecting = true;
                } else if(isConnecting) {
                    if(msg.arg1 != BluetoothStatus.STATE_CONNECTED.getValue()) {
                        if(mBluetoothConnectionListener != null)
                            mBluetoothConnectionListener.onDeviceConnectionFailed();
                    }
                    isConnecting = false;
                }
                break;
            }
        }
    };
    
    public void stopAutoConnect() {
        isAutoConnectionEnabled = false;
    }
    
    public void connect(Intent data) {
        String address = data.getExtras().getString(BluetoothMessageStates.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        mBtService.connect(device);
    }
    
    public void connect(String address) {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        mBtService.connect(device);
    }
    
    public void disconnect() {
        if(mBtService != null) {
            isServiceRunning = false;
            mBtService.stop();
            if(mBtService.getState() == BluetoothStatus.STATE_NONE) {
                isServiceRunning = true;
                mBtService.start(BluetoothProtocol.this.isAndroid);
            }
        }
    }
    
    public void setBluetoothStateListener (BluetoothStateListener listener) {
        mBluetoothStateListener = listener;
    }
    
    public void setOnDataReceivedListener (OnDataReceivedListener listener) {
        mDataReceivedListener = listener;
    }
    
    public void setBluetoothConnectionListener (BluetoothConnectionListener listener) {
        mBluetoothConnectionListener = listener;
    }
    
    public void setAutoConnectionListener(AutoConnectionListener listener) {
        mAutoConnectionListener = listener;
    }
    
    public void enable() {
        mBluetoothAdapter.enable();
    }
    
    public void send(byte[] data, boolean CRLF) {
        if(mBtService.getState() == BluetoothStatus.STATE_CONNECTED) {
            if(CRLF) {
                byte[] data2 = new byte[data.length + 2];
                for(int i = 0 ; i < data.length ; i++) 
                    data2[i] = data[i];
                data2[data2.length - 2] = 0x0A;
                data2[data2.length - 1] = 0x0D;
                mBtService.write(data2);
            } else {
                mBtService.write(data);
            }
        }
    }
    
    public void send(String data, boolean CRLF) {
        if(mBtService.getState() == BluetoothStatus.STATE_CONNECTED) {
            if(CRLF) 
                data += "\r\n"; 
            mBtService.write(data.getBytes());
        }
    }
    
    public String getConnectedDeviceName() {
        return mDeviceName;
    }
    
    public String getConnectedDeviceAddress() {
        return mDeviceAddress;
    }
    
    public String[] getPairedDeviceName() {
        int c = 0;
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        String[] name_list = new String[devices.size()];
        for(BluetoothDevice device : devices) {
            name_list[c] = device.getName();
            c++;
        }  
        return name_list;
    }
    
    public String[] getPairedDeviceAddress() {
        int c = 0;
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        String[] address_list = new String[devices.size()];
        for(BluetoothDevice device : devices) {
            address_list[c] = device.getAddress();
            c++;
        }  
        return address_list;
    }
    
    
    public void autoConnect(String keywordName) {
        if(!isAutoConnectionEnabled) {
            keyword = keywordName;
            isAutoConnectionEnabled = true;
            isAutoConnecting = true;
            if(mAutoConnectionListener != null)
                mAutoConnectionListener.onAutoConnectionStarted();
            final ArrayList<String> arr_filter_address = new ArrayList<String>();
            final ArrayList<String> arr_filter_name = new ArrayList<String>();
            String[] arr_name = getPairedDeviceName();
            String[] arr_address = getPairedDeviceAddress();
            for(int i = 0 ; i < arr_name.length ; i++) {
                if(arr_name[i].contains(keywordName)) {
                    arr_filter_address.add(arr_address[i]);
                    arr_filter_name.add(arr_name[i]);
                }
            }
    
            bcl = new BluetoothConnectionListener() {
                public void onDeviceConnected(String name, String address) {
                    bcl = null;
                    isAutoConnecting = false;
                }
    
                public void onDeviceDisconnected() { }
                public void onDeviceConnectionFailed() {
                	Log.e("CHeck", "Failed");
                    if(isServiceRunning) {
                        if(isAutoConnectionEnabled) {
                            c++;
                            if(c >= arr_filter_address.size())
                                c = 0;
                            connect(arr_filter_address.get(c));
                        	Log.e("CHeck", "Connect");
                            if(mAutoConnectionListener != null)
                                mAutoConnectionListener.onNewConnection(arr_filter_name.get(c)
                                    , arr_filter_address.get(c));
                        } else {
                            bcl = null;
                            isAutoConnecting = false;
                        }
                    }
                }
            };

            setBluetoothConnectionListener(bcl);
            c = 0;
            if(mAutoConnectionListener != null)
                mAutoConnectionListener.onNewConnection(arr_name[c], arr_address[c]);
            if(arr_filter_address.size() > 0) 
                connect(arr_filter_address.get(c));
            else 
                Toast.makeText(mContext, "Device name mismatch", Toast.LENGTH_SHORT).show();
        }
    }
}
