package models;

import Enums.VehicleType;

public class VehicleFactory {

    public static Vehicle createVehicle(VehicleType vehicleType) {
        return new Vehicle(vehicleType);
    }
}
