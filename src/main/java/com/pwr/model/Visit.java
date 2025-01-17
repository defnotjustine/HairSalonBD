package com.pwr.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visit {
    private static int visitId;
    private LocalDate date;
    private LocalTime time;
    private int clientId;
    private int hairdresserId;
    private String status;
    private int serviceId;
    private int price;
    private String visitDetails;

    public Visit(int visitId, String visitDetails) {
        this.visitId = visitId;
        this.visitDetails = visitDetails;
    }

    public static int getVisitId() {
        return visitId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHairdresserId() {
        return hairdresserId;
    }

    public void setHairdresserId(int hairdresserId) {
        this.hairdresserId = hairdresserId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getVisitDetails() {
        return visitDetails;
    }

    public void setVisitDetails(String visitDetails) {
        this.visitDetails = visitDetails;
    }
}