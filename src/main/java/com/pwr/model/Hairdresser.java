package com.pwr.model;

public class Hairdresser {
    private String firstName;
    private String lastName;
    private String specjalization;
    private String telephone;
    private int experienceYears;

    public Hairdresser(String firstName, String lastName, String specjalization, String telephone, int experienceYears) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specjalization = specjalization;
        this.telephone = telephone;
        this.experienceYears = experienceYears;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecjalization() {
        return specjalization;
    }

    public void setSpecjalization(String specjalization) {
        this.specjalization = specjalization;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
}
