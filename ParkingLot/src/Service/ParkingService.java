package Service;

import models.ParkingFloor;
import models.ParkingResponse;
import models.ParkingTicket;
import models.Vehicle;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingService {
    ParkingStartegy parkingStartegy;

   public void setParkingStartegy(ParkingStartegy parkingStartegy) {
       this.parkingStartegy = parkingStartegy;
    }

    public ParkingTicket ParkVehicle(Vehicle vehicle, List<ParkingFloor> parkingFloors, Integer totalFloors){

       return  parkingStartegy.parkVehicle(vehicle,parkingFloors,totalFloors);
    }

    public Boolean UnparkVehicle(ParkingTicket ticket, List<ParkingFloor> parkingFloors){
        return  parkingStartegy.unparkVehicle(ticket,parkingFloors);
    }

}
