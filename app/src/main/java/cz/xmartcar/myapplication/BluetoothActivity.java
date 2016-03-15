///*
// *
// * @author Ferdinand Urban
// * Copyright (C) year  Ferdinand Urban
// *
// *
// */
//
//package cz.xmartcar.myapplication;
//
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothServerSocket;
//import android.bluetooth.BluetoothSocket;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import java.io.IOException;
//import java.util.UUID;
//
//public class BluetoothActivity extends Activity {
//
//    // Use the same UUID as for iOS service
//    private final static UUID uuid = UUID.fromString("6E6B5C64-FAF7-40AE-9C21-D4933AF45B23");
//    private static final int ENABLE_BT_REQUEST_CODE = 1;
//    private static final int DISCOVERABLE_BT_REQUEST_CODE = 2;
//    private static final int DISCOVERABLE_DURATION = 300;
//
//    private BluetoothAdapter mBluetoothAdapter;
//    private ToggleButton mToggleButton;
//    private ListView mListView;
//    private ArrayAdapter mAdapter;
//
//    // Create a BroadcastReceiver for ACTION_FOUND
//    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                mAdapter.add(bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress());
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bluetooth);
//
//        mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
//
//        mListView = (ListView) findViewById(R.id.listView);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String itemValue = (String) mListView.getItemAtPosition(position);
//                String MAC = itemValue.substring(itemValue.length() - 17);
//                BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC);
//
//                ConnectingThread t = new ConnectingThread(bluetoothDevice);
//                t.start();
//            }
//        });
//
//        mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
//        mListView.setAdapter(mAdapter);
//
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//    }
//
//    public void onToggleClicked(View view) {
//
//        mAdapter.clear();
//
//        ToggleButton toggleButton = (ToggleButton) view;
//
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(getApplicationContext(), "Oop! Your device does not support Bluetooth", Toast.LENGTH_SHORT).show();
//            toggleButton.setChecked(false);
//        } else {
//
//            if (toggleButton.isChecked()){ // to turn on bluetooth
//                if (!mBluetoothAdapter.isEnabled()) {
//                    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(enableBluetoothIntent, ENABLE_BT_REQUEST_CODE);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Your device has already been enabled." + "\n" + "Scanning for remote Bluetooth devices...", Toast.LENGTH_SHORT).show();
//
//                    discoverDevices();
//                    makeDiscoverable();
//                }
//            } else { // Turn off bluetooth
//
//                mBluetoothAdapter.disable();
//                mAdapter.clear();
//                Toast.makeText(getApplicationContext(), "Your device is now disabled.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == ENABLE_BT_REQUEST_CODE) {
//
//            // Bluetooth successfully enabled!
//            if (resultCode == Activity.RESULT_OK) {
//                Toast.makeText(getApplicationContext(), "Ha! Bluetooth is now enabled." + "\n" + "Scanning for remote Bluetooth devices...", Toast.LENGTH_SHORT).show();
//
//                discoverDevices();
//                makeDiscoverable();
//
//                // Start a thread to create a  server socket to listen for connection request
//                ListeningThread t = new ListeningThread();
//                t.start();
//
//            } else { // RESULT_CANCELED
//                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled.", Toast.LENGTH_SHORT).show();
//
//                mToggleButton.setChecked(false);
//            }
//        } else if (requestCode == DISCOVERABLE_BT_REQUEST_CODE){
//
//            if (resultCode == DISCOVERABLE_DURATION){
//                Toast.makeText(getApplicationContext(), "Your device is now discoverable by other devices for " + DISCOVERABLE_DURATION + " seconds", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "Fail to enable discoverability on your device.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    protected void discoverDevices(){
//        if (mBluetoothAdapter.startDiscovery()) {
//            Toast.makeText(getApplicationContext(), "Discovering other bluetooth devices...", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Discovery failed to start.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    protected void makeDiscoverable(){
//        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);
//        startActivityForResult(discoverableIntent, DISCOVERABLE_BT_REQUEST_CODE);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        this.registerReceiver(mBroadcastReceiver, filter);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        this.unregisterReceiver(mBroadcastReceiver);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        // getMenuInflater().inflate(R.menu.bluetooth, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    // ============================================================================================
//    //                                      LISTENING THREAD
//    // ============================================================================================
//    private class ListeningThread extends Thread {
//        private final BluetoothServerSocket mBluetoothServerSocket;
//
//        public ListeningThread() {
//            BluetoothServerSocket temp = null;
//            try {
//                temp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(getString(R.string.app_name), uuid);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mBluetoothServerSocket = temp;
//        }
//
//        public void run() {
//            BluetoothSocket bluetoothSocket;
//
//            while (true) {
//                try {
//                    bluetoothSocket = mBluetoothServerSocket.accept();
//                } catch (IOException e) {
//                    break;
//                }
//
//                // Connection is accepted
//                if (bluetoothSocket != null) {
//
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "A connection has been accepted.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    try {
//                        mBluetoothServerSocket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//
//        // Cancel the listening socket and terminate the thread
//        public void cancel() {
//            try {
//                mBluetoothServerSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // ============================================================================================
//    //                                      CONNECTING THREAD
//    // ============================================================================================
//    private class ConnectingThread extends Thread {
//        private final BluetoothSocket mBluetoothSocket;
//        private final BluetoothDevice mBluetoothDevice;
//
//        public ConnectingThread(BluetoothDevice device) {
//
//            BluetoothSocket temp = null;
//            mBluetoothDevice = device;
//
//            try {
//                temp = mBluetoothDevice.createRfcommSocketToServiceRecord(uuid);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            mBluetoothSocket = temp;
//        }
//
//        public void run() {
//            // Cancel discovery as it will slow down the connection
//            mBluetoothAdapter.cancelDiscovery();
//
//            try {
//                mBluetoothSocket.connect();
//            } catch (IOException connectException) {
//                connectException.printStackTrace();
//                try {
//                    mBluetoothSocket.close();
//                } catch (IOException closeException) {
//                    closeException.printStackTrace();
//                }
//            }
//        }
//
//        // Cancel an open connection and terminate the thread
//        public void cancel() {
//            try {
//                mBluetoothSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
