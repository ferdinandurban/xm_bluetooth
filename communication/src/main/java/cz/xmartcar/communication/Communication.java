/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication;

import android.bluetooth.BluetoothDevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.xmartcar.communication.bluetooth.BluetoothService;
import cz.xmartcar.communication.model.XMCarData;
import cz.xmartcar.communication.model.XMError;
import cz.xmartcar.communication.model.XMUser;
import cz.xmartcar.communication.rest.sso.SSOService;

public class Communication {

    private static final Logger log = LoggerFactory.getLogger(Communication.class);

    private BluetoothService mBluetoothService = null;
    private SSOService       mSsoService = new SSOService();

    // SSOService Connection
    public XMError registerUser(String username, String password){
        XMUser user = new XMUser();
        user.setName(username);
        user.setPassword(password);

        mSsoService.registerUser(user);

        return null;
    }

    public String getUserToken(){
        return "";
    }

    public boolean verifyUser(){
        return false;
    }

    // Bluetooth Connection
    // connect to the car

    public void carConnect(BluetoothDevice device){
        log.debug("Connecting to the car");

        if (mBluetoothService != null) {
            if (mBluetoothService.getState() == BluetoothService.STATE_NONE){
                mBluetoothService.connect(device, true);
            }
        }
    }

    // disconnect from the car
    public void carDisconnect(){
        log.debug("Disconnecting from the car");
        mBluetoothService.stop();
    }

    public int carConnectionState(){
        return mBluetoothService.getState();
    }

    public void sendCommand(XMCarData command){
        if(mBluetoothService.isConnected()){
            mBluetoothService.write(command.getCommand());
        }
    }

}
