/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.bluetooth;

// Message types sent from the BluetoothChatService Handler
public class BluetoothMessageStatus {
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
}
