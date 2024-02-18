package Airline;

import Interface.ReservationSystem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Airline Reservation System");

        // Create ChoiceBoxes for day, airline, and number of travelers
        ChoiceBox<String> dayChoiceBox = new ChoiceBox<>();
        dayChoiceBox.getItems().addAll(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
        dayChoiceBox.setValue("Monday"); // Default value

        ChoiceBox<Flight> airlineChoiceBox = new ChoiceBox<>();
        airlineChoiceBox.getItems().addAll(
            new Flight("AirIndia", "08:00 AM", 100),
            new Flight("Indigo", "10:00 AM", 120),
            new Flight("JetBlue", "12:00 PM", 80)
        );
        airlineChoiceBox.setValue(new Flight("Airine", "Time", 100)); // Default value

        Spinner<Integer> travelerSpinner = new Spinner<>(1, 10, 1);

        // Create a button to calculate the price
        Button calculateButton = new Button("Calculate Price");

        // Create a label to display the price
        Label priceLabel = new Label();

        // Define the action when the "Calculate Price" button is clicked
        calculateButton.setOnAction(e -> {
            int numberOfTravelers = travelerSpinner.getValue();
            Flight selectedFlight = airlineChoiceBox.getValue();
            
            try {
                ReservationSystem reservationSystem = new SimpleReservationSystem(); // Instantiate the reservation system
                double price = reservationSystem.calculatePrice(selectedFlight, numberOfTravelers);
                priceLabel.setText("Selected Flight: " + selectedFlight + "\nTotal Price: Rs. " + price);
            } catch (NotEnoughSeatsException ex) {
                priceLabel.setText("Error: " + ex.getMessage());
            }
        });

        // Create a VBox to hold all the components
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
            new Label("Select Day:"),
            dayChoiceBox,
            new Label("Select Flight:"),
            airlineChoiceBox,
            new Label("Number of Travelers:"),
            travelerSpinner,
            calculateButton,
            priceLabel
        );

        // Create a scene and add the VBox to it
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Custom exception to handle not enough seats
    public static class NotEnoughSeatsException extends Exception {
        public NotEnoughSeatsException(String message) {
            super(message);
        }
    }

   // Interface for the reservation system
    // interface ReservationSystem {
    //     double calculatePrice(Flight flight, int numberOfTravelers) throws NotEnoughSeatsException;
    // }

    public class Flight {
        private String airlineName;
        private String flightTime;
        private int availableSeats;

        public Flight(String airlineName, String flightTime, int availableSeats) {
            this.airlineName = airlineName;
            this.flightTime = flightTime;
            this.availableSeats = availableSeats;
        }

        @Override
        public String toString() {
            return airlineName + " - " + flightTime;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }
    }

    // An example implementation of the ReservationSystem interface
    public class SimpleReservationSystem implements ReservationSystem {
        @Override
        public double calculatePrice(Flight flight, int numberOfTravelers) throws NotEnoughSeatsException {
            if (flight.getAvailableSeats() < numberOfTravelers) {
                throw new NotEnoughSeatsException("Not enough seats available on the selected flight.");
            }
            // Simple pricing calculation, you can modify this
            return numberOfTravelers * 20000;
        }
    }
}
