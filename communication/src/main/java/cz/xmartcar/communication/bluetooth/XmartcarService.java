/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.bluetooth;

import android.os.ParcelUuid;

public class XmartcarService {

    /* Full Bluetooth UUID that defines the xmartcar Service */
    public static final ParcelUuid XMARTCAR_SERVICE = ParcelUuid.fromString("137A2365-D360-4C90-8E92-87742816B044");

    /* Short-form UUID that defines the xmartcar service */
    private static final int UUID_SERVICE_XMARTCAR = 0x1809;

    private String  mName;
    private int     mSignal;
    private String  mAddress;

    public XmartcarService() {
        mSignal = 1;
        mAddress = "1234-5678-9012";

        mName = "testovaci";
    }

    public String getName() {
        return mName;
    }

    public int getSignal() {
        return mSignal;
    }

    public String getAddress() {
        return mAddress;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s", mName, mSignal, mAddress);
    }
}