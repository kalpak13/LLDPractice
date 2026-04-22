package Service;

import models.ParkingFloor;
import models.ParkingResponse;
import models.ParkingTicket;
import models.Vehicle;

import java.util.List;

public interface ParkingStartegy {
    ParkingTicket parkVehicle(Vehicle vehicle, List<ParkingFloor> parkingFloors, Integer totalFloors);
    Boolean unparkVehicle(ParkingTicket ticket , List<ParkingFloor> parkingFloors);
}
