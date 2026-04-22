import Exceptions.ExceptionMapper;
import Exceptions.GlobalHandler;
import models.ParkingLot;
import models.ParkingResponse;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalHandler());

        ParkingLot parkingLot = new ParkingLot();
        try {
            parkingLot.UnparkVehicle("some-vehicle-id", "invalid-ticket");
        } catch (Exception e) {
            ParkingResponse response = ExceptionMapper.toResponse(e);
            LOGGER.info(response.getMessage() + " | code=" + response.getErrorCode());
        }
    }
}
