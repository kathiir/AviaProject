package com.example.aviaapplication.api.models;

public class RecentCity {

    private Long userId;
    private Long cityId;

    public RecentCity(Long userId, Long cityId) {
        this.userId = userId;
        this.cityId = cityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
