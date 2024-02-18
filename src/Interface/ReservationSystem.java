package Interface;

import Airline.App.*;

public interface ReservationSystem {
        double calculatePrice(Flight flight, int numberOfTravelers)throws NotEnoughSeatsException;
    }
