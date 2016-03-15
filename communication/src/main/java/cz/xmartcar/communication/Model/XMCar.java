/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.model;

public class XMCar {

    private String mVIN;
    private String mRegistrationNumber;


    public XMCar(){

    }

    public String getRegistrationNumber() {
        return mRegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        mRegistrationNumber = registrationNumber;
    }

    public String getVIN() {
        return mVIN;
    }

    public void setVIN(String VIN) {
        mVIN = VIN;
    }

}
