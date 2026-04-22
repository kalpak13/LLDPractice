package Service;

import models.ParkingFloor;
import models.ParkingResponse;
import models.ParkingTicket;
import models.Vehicle;

import java.util.List;

public class NearestFirstParkingStrategy implements ParkingStartegy{

    public ParkingTicket parkVehicle(Vehicle vehicle, List<ParkingFloor> parkingFloors, Integer totalFloors) {

        for(int i=0;i<totalFloors;i++){
            if(parkingFloors.get(i).parkVehicle(vehicle)){
                return new ParkingTicket(vehicle.getId(),i+1);
            }
        }
        return null;

    }
    public Boolean unparkVehicle(ParkingTicket ticket,List<ParkingFloor> parkingFloors) {
            ParkingFloor floor = parkingFloors.get(ticket.getFloor()-1);
            return floor.UnparkVehicle(ticket.getVehicleId());

    }
}
