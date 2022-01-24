package com.example.incidentreporterapp;

class IncidentModelClass {
    int    incidentImage;
    String incidentDescription;
    String incidentLatitude;
    String incidentLongitude;



    public IncidentModelClass(){
        //needed empty constructor
    }

    public IncidentModelClass(int incidentImage, String incidentDescription, String incidentLatitude, String incidentLongitude) {
        this.incidentImage = incidentImage;
        this.incidentDescription = incidentDescription;
        this.incidentLatitude = incidentLatitude;
        this.incidentLongitude = incidentLongitude;
    }

    public String getIncidentDescription() {
        return incidentDescription;
    }

    public int getIncidentImage() {
        return incidentImage;
    }
    public String getIncidentLatitude() {
        return incidentLatitude;
    }

    public String getIncidentLongitude() {
        return incidentLongitude;
    }
}
