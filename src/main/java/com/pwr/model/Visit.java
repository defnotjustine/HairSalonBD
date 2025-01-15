package com.pwr.model;

public class Visit {
    private static int visitId;
    private String visitDetails;

    public Visit(int visitId, String visitDetails) {
        this.visitId = visitId;
        this.visitDetails = visitDetails;
    }

    public static int getVisitId() {
        return visitId;
    }

    public String getVisitDetails() {
        return visitDetails;
    }
}