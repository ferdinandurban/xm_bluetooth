/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.bluetooth;

public class BluetoothOther {
    public static final int REQUEST_CONNECT_DEVICE = 384;
    public static final int REQUEST_ENABLE_BT = 385;

    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";
    public static final String TOAST = "toast";

    public static final boolean DEVICE_ANDROID = true;
    public static final boolean DEVICE_OTHER = false;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
}
