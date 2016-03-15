/*
 *
 * @author Ferdinand Urban
 * Copyright (C) year  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.model;

import java.util.Date;

/**
 * Created by ferdinandurban on 14/03/16.
 */
public class XMEventInfo {
    private long mId;
    private Date mCreatedAt;
    private String mType;


    public XMEvent getType() {
        String t = mType;

        return null;    // unknown event type
    }


    public long getId() {
        return mId;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

}
