/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication;

import cz.xmartcar.communication.model.XMError;

public class Communication {

    // SSO Connection
    public XMError registerUser(){
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
    public boolean connect(){

        return false;
    }

    // disconnect from the car
    public boolean disconnect(){
        return false;
    }

    // check if the car connection is alive
    public boolean isConnected(){
        return false;
    }
}
