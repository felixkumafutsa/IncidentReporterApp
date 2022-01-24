package com.example.incidentreporterapp;

import java.util.Date;

public class IncidentModel{
    private String incidentDescription;
    private String incidentLatitude;
    private String incidentLongitude;
    private Date mTimestamp;

    public Date getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }

    public IncidentModel(String incidentDescription, String incidentLatitude, String incidentLongitude) {
        this.incidentDescription = incidentDescription;
        this.incidentLatitude = incidentLatitude;
        this.incidentLongitude = incidentLongitude;
    }

    public IncidentModel() {
        //empty but required
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public String getIncidentLatitude() {
        return incidentLatitude;
    }

    public String getIncidentLongitude() {
        return incidentLongitude;
    }
}
