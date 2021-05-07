package com.example.aviaapplication.api.models;

public class RecentFlight {

    private Long id;
    private Long userId;
    private Flight flight;
    private Integer amount;

    public RecentFlight(Long id, Long userId, Flight flight, Integer amount) {
        this.id = id;
        this.userId = userId;
        this.flight = flight;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Flight getFlightId() {
        return flight;
    }

    public void setFlightId(Flight flightId) {
        this.flight = flightId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
