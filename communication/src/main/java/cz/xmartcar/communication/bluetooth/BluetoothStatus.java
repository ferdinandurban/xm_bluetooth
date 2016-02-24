/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */


package cz.xmartcar.communication.bluetooth;

// Constants that indicate the current connection state
public enum BluetoothStatus {
    STATE_NONE(0),       	// we're doing nothing
    STATE_LISTEN(1),     	// now listening for incoming connections
    STATE_CONNECTING(2), 	// now initiating an outgoing connection
    STATE_CONNECTED(3),  	// now connected to a remote device
    STATE_NULL(-1);          // now service is null

    private int value;

    private BluetoothStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}


//}
