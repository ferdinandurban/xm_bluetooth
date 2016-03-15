/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.model;

import java.util.ArrayList;
import java.util.List;

public class RestResults {

    private int totalCount;
    private boolean incompleteResults;
    private List<XMData> items = new ArrayList<XMData>();

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<XMData> getItems() {
        return items;
    }

    public void setItems(List<XMData> items) {
        this.items = items;
    }
}
