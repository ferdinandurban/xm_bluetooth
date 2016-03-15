
package cz.xmartcar.communication.model;

public class XMCarData {

    private String mCommand;

    public XMCarData(String command){
        mCommand = command;
    }

    public byte[] getCommand(){
        return mCommand.getBytes();
    }
}
