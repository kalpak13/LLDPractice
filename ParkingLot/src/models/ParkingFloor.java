package models;

import Enums.VehicleType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingFloor {

    ConcurrentHashMap<VehicleType, Integer> parkingSpaces;
    ConcurrentHashMap<String,ParkingSpot > occupiedSpaces ;
    ConcurrentHashMap<VehicleType, Integer> availableSpaces;
    private final ReentrantLock lock = new ReentrantLock();

    Integer floorNumber;
    Integer totalSpaces;

    public ParkingFloor(){
        availableSpaces = new ConcurrentHashMap<>();
        occupiedSpaces = new ConcurrentHashMap<>();
        parkingSpaces = new ConcurrentHashMap<>();
        totalSpaces = 0;
    }
    public void addSpacesByType(VehicleType type, int spaces){
        parkingSpaces.put(type, spaces);
        availableSpaces.put(type, spaces);
        totalSpaces += spaces;
    }

    public Boolean parkVehicle(Vehicle vehicle){
        lock.lock();
        try{
            Integer available = availableSpaces.get(vehicle.getVehicleType());
            if (available == null || available <= 0) {
                return false;
            }
            ParkingSpot spot = new ParkingSpot(vehicle);
            occupiedSpaces.put(vehicle.getId(), spot);
            availableSpaces.put(vehicle.getVehicleType(), available - 1);
            return true;

        } finally {
            lock.unlock();
        }


    }

    public Boolean UnparkVehicle(String vehicleId){
        lock.lock();
        try{
            if(!occupiedSpaces.containsKey(vehicleId)) return false;
            ParkingSpot spot = occupiedSpaces.get(vehicleId);
            spot.isOccupied = false;
            occupiedSpaces.remove(vehicleId);
            availableSpaces.put(spot.vehicleType, availableSpaces.get(spot.vehicleType)+1);
            return true;
        }
        finally {
            lock.unlock();
        }

    }

    public Boolean hasAvailableSpace(VehicleType type){
        if(availableSpaces.containsKey(type)){
            return availableSpaces.get(type) > 0;
        }
        return false;
    }

}
