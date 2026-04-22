package models;

import java.util.UUID;

public class ParkingTicket {
    String ticketNumber;
    String vehicleId;
    Integer floorNumber;

    public ParkingTicket(String vehicleId,Integer floorNumber){
        this.ticketNumber = UUID.randomUUID().toString();
        this.vehicleId = vehicleId;
        this.floorNumber = floorNumber;
    }
    public String getTicketNumber(){
        return ticketNumber;
    }
    public String getVehicleId(){
        return vehicleId;
    }
    public Integer getFloor(){
        return floorNumber;
    }
}
