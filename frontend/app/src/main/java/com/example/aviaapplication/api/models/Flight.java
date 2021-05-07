package com.example.aviaapplication.api.models;

import java.util.Date;

public class Flight {

    private Long flightId;
    private Date departureDate;
    private Date arrivalDate;
    private String arrivalAirportName;
    private City arrivalCity;
    private City depCity;
    private String depAirportName;
    private String depAirportCode;
    private String arrivalAirportCode;
    private Integer businessPrice;
    private Integer economyPrice;

    public Flight(){}

    public Flight(Long flightId, Date departureDate, Date arrivalDate, String arrivalAirportName, String depAirportName, String arrivalAirportCode, String depAirportCode, Integer businessPrice, Integer economyPrice) {
        this.flightId = flightId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportName = arrivalAirportName;
        this.depAirportName = depAirportName;
        this.depAirportCode = depAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
        this.businessPrice = businessPrice;
        this.economyPrice = economyPrice;
    }

    public City getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(City arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public City getDepCity() {
        return depCity;
    }

    public void setDepCity(City depCity) {
        this.depCity = depCity;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getDepAirportName() {
        return depAirportName;
    }

    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName;
    }

    public String getDepAirportCode() {
        return depAirportCode;
    }

    public void setDepAirportCode(String depAirportCode) {
        this.depAirportCode = depAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public Integer getBusinessPrice() {
        return businessPrice;
    }

    public void setBusinessPrice(Integer businessPrice) {
        this.businessPrice = businessPrice;
    }

    public Integer getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(Integer economyPrice) {
        this.economyPrice = economyPrice;
    }
}
