package models;

import Enums.VehicleType;

import java.util.UUID;

public class Vehicle {

    VehicleType vehicleType;

    UUID id;

    public Vehicle(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        this.id=UUID.randomUUID();
    }
    public String getId() {
        return id.toString();
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
