package com.example.incidentreporterapp;

public class IncidentsModelClass {
    private int incidentIm;
    private String incidentDescription, latitude, longitude, divider;

    IncidentsModelClass(int incidentIm,  String incidentDescription, String latitude, String longitude){
        this.incidentIm = incidentIm;
        this.incidentDescription = incidentDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.divider = divider;
    }

    public int getIncidentIm() {
        return incidentIm;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDivider() {
        return divider;
    }
}
