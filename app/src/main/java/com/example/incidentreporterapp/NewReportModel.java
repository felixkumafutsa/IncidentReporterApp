package com.example.incidentreporterapp;

public class NewReportModel {
    private String incidentDescription;
    private String incidentLatitude;
    private String incidentLongitude;

    public NewReportModel(String incidentDescription) {
        this.incidentDescription = incidentDescription;
        this.incidentLatitude = incidentLatitude;
        this.incidentLongitude = incidentLongitude;
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
