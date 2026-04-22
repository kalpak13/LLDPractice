package models;

import Enums.VehicleType;

import java.util.UUID;

public class ParkingSpot {

    VehicleType vehicleType;
    Boolean isOccupied;
    String vehicleId;

    ParkingSpot(Vehicle vehicle){
        isOccupied = true;
        vehicleType = vehicle.getVehicleType();
        vehicleId = vehicle.getId();
    }

    public String getVehicleId(){
        return vehicleId;
    }
}
