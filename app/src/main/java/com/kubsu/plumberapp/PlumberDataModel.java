package com.kubsu.plumberapp;

import java.io.Serializable;

class PlumberDataModel implements Serializable {

    private int plumberID;
    private String plumberStatus;

    public int getPlumberID() {
        return plumberID;
    }

    public void setPlumberID(int plumberID) {
        this.plumberID = plumberID;
    }

    public String getPlumberStatus() {
        return plumberStatus;
    }

    public void setPlumberStatus(String plumberStatus) {
        this.plumberStatus = plumberStatus;
    }
}
