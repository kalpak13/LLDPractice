package models;

import Enums.VehicleType;

public class ParkingFloorBuilder {

    ParkingFloor parkingFloor;

    public ParkingFloorBuilder(){
        parkingFloor = new ParkingFloor();
    }

    public ParkingFloorBuilder addSpaceByVehicleType(VehicleType vehicleType,Integer spaces){
        parkingFloor.addSpacesByType(vehicleType, spaces);
        return this;
    }

    public ParkingFloorBuilder addFloorNumber(Integer floorNumber){
        parkingFloor.floorNumber = floorNumber;
        return this;
    }
    public ParkingFloor build(){
        ParkingFloor newParkingFloor = parkingFloor;
        parkingFloor = new ParkingFloor();
        return newParkingFloor;
    }
}
