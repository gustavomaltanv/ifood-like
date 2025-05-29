package com.ifoodlike.model;

public class DeliveryDriver extends User {
    private String driverId;
    private String vehicleInfo;

    public DeliveryDriver(String userId, String name, String email, String driverId, String vehicleInfo) {
        super(userId, name, email);
        this.driverId = driverId;
        this.vehicleInfo = vehicleInfo;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void deliverOrder(Order order) {
        order.nextState();
    }
}