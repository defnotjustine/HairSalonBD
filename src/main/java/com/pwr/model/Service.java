package com.pwr.model;

public class Service {
    private int serviceId;
    private String serviceName;
    private String description;
    private double price;
    private int duration;

    public Service(int serviceId, String serviceName, String description, double price, int duration) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

}
