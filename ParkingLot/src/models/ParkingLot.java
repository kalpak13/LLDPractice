package models;

import Exceptions.InvalidTicketNumberException;
import Service.NearestFirstParkingStrategy;
import Service.ParkingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    List<ParkingFloor> parkingFloors;
    Integer totalFloors;
    Map<String,ParkingTicket> tickets;

    ParkingService parkingService;


    public ParkingLot(){
        totalFloors = 0;
        parkingFloors = new ArrayList<>();
        tickets = new HashMap<>();
        parkingService = new ParkingService();
        parkingService.setParkingStartegy(new NearestFirstParkingStrategy());//default strategy
    }
    public void addFloor(ParkingFloor floor){
        parkingFloors.add(floor);
        totalFloors++;
    }

    public ParkingResponse ParkVehicle(Vehicle vehicle){

      ParkingTicket ticket = parkingService.ParkVehicle(vehicle,parkingFloors,totalFloors);
      if(ticket == null) return new ParkingResponse("No available spaces",false,null);
      else{
          tickets.put(ticket.getTicketNumber(),ticket);
          return new ParkingResponse("Vehicle parked successfully",true,ticket.ticketNumber);

      }

    }

    public ParkingResponse UnparkVehicle(String vehicleId,String ticketNumber) {
         //check if ticket number is valid
        if(tickets.containsKey(ticketNumber)){
            ParkingTicket ticket = tickets.get(ticketNumber);
            if(!ticket.getVehicleId().equals(vehicleId)){
                return new ParkingResponse("Vehicle not found",false,null);
            }
            if(parkingService.UnparkVehicle(ticket,parkingFloors)){
                tickets.remove(ticketNumber);
                //calculate fees

                return new ParkingResponse("Vehicle unparked successfully",true,ticketNumber);

            }else{
                return new ParkingResponse("Vehicle not found",false,null);
            }

        }else {
            throw new InvalidTicketNumberException("12343", "invalid ticketNumber " + ticketNumber);
        }


    }

}
