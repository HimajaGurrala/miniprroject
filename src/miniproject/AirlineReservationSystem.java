package miniproject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;
  //  private string email;
    private String firstname;
    private String lastname;
    private String phonenumber;
    

    public User(String username, String password, String firstname, String lastname,String                                                   phonenumber) {
        this.username = username;
        this.password = password;
        this.firstname=firstname;
        this.lastname=lastname;
        this.phonenumber=phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getfirstname() {
        return firstname;
    }

    public String getlastname() {
        return lastname;
    }
    public String getphonenumber() {
        return phonenumber;
    }

   // public String getPassword() {
     //   return password;
  //  }


}

class Seat {
    private int seatNumber;
    private double price;
    private boolean isReserved;

    public Seat(int seatNumber, double price) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.isReserved = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void reserve() {
        isReserved = true;
    }

    public void cancelReservation() {
        isReserved = false;
    }
}

class Flight {
	private String flightName;
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int totalSeats;
    private int availableSeats;

    public Flight(String flightName, String flightNumber, String origin, String destination,String departureTime,String arrivalTime, int totalSeats) {
        this.flightName = flightName;
    	this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void decreaseAvailableSeats(int numSeats) {
        availableSeats -= numSeats;
    }

    public void increaseAvailableSeats(int numSeats) {
        availableSeats += numSeats;
    }
}

class AirlineSystem {
    private Map<String, User> users;
    private Map<Integer, Seat> seats;
    private Map<String, Flight> flights;
    private int totalSeats;
    private int availableSeats;

    public AirlineSystem(int totalSeats) {
        users = new HashMap<>();
        seats = new HashMap<>();
        flights = new HashMap<>();
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        initializeSeats();
        initializeFlights();
    }

    private void initializeSeats() {
        // Initialize seats with seat number and price
        for (int i = 1; i <= totalSeats; i++) {
            double price = 100.00; // Set default price for each seat
            Seat seat = new Seat(i, price);
            seats.put(i, seat);
        }
    }

    private void initializeFlights() {
        // Initialize flights with flight number, origin, destination, departure time, arrival time, total seats
        Flight flight1 = new Flight("AirAsia", "FL001", "Hyderabad", "Banglore", "07:25", "09:05", totalSeats);
        Flight flight2 = new Flight("JetAirways", "FL002", "Delhi", "Mumbai", "11:00", "13:00", totalSeats);
        Flight flight3 = new Flight("IndiGo", "FL003", "Goa", "Chennai", "08:15", "17:30", totalSeats);
        Flight flight4 = new Flight("Emirates", "FL004", "Dubai", "Singapore", "05:00", "20:00", totalSeats);
        Flight flight5 = new Flight("SpiceJet", "FL005", "Visakhapatnam", "Jaipur", "20:40", "07:20", totalSeats);
        Flight flight6 = new Flight("AirIndia", "FL006", "Delhi", "London", "06:35", "11:30", totalSeats);
        
        flights.put(flight1.getFlightNumber(), flight1);
        flights.put(flight2.getFlightNumber(), flight2);
        flights.put(flight3.getFlightNumber(), flight3);
        flights.put(flight4.getFlightNumber(), flight4);
        flights.put(flight5.getFlightNumber(), flight5);
        flights.put(flight6.getFlightNumber(), flight6);

    }

    public void registerUser(String username, String password,String firstname,String lastname,String phonenumber) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            User newUser = new User(username, password, firstname, lastname, phonenumber);
            users.put(username, newUser);
            
            System.out.println("Registration successful! You can now log in with your new credentials.");
        }
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public void makeReservation(String username, String flightNumber, int numSeats) {
        Flight flight = flights.get(flightNumber);
        if (flight == null) {
            System.out.println("Invalid flight number.");
        } else if (numSeats > flight.getAvailableSeats()) {
            System.out.println("Seats not available. Cannot reserve " + numSeats + " seat(s).");
        } else {
            double totalPrice = 0;
            int seatsReserved = 0;
            for (Seat seat : seats.values()) {
                if (!seat.isReserved()) {
                    seat.reserve();
                    flight.decreaseAvailableSeats(1);
                    totalPrice += seat.getPrice();
                    seatsReserved++;
                    if (seatsReserved == numSeats) {
                        break;
                    }
                }
            }
            System.out.println("Reservation successful! Reserved " + seatsReserved + " seat(s) for " + username + ".");
            System.out.println("Total price: $" + totalPrice);
        }
    }

    public void cancelReservation(String username, String flightNumber, int numSeats) {
        Flight flight = flights.get(flightNumber);
        if (flight == null) {
            System.out.println("Invalid flight number.");
        } else {
            int seatsCancelled = 0;
            for (Seat seat : seats.values()) {
                if (seat.isReserved()) {
                    seat.cancelReservation();
                    flight.increaseAvailableSeats(1);
                    seatsCancelled++;
                    if (seatsCancelled == numSeats) {
                        break;
                    }
                }
            }
            System.out.println("Cancellation successful! Canceled " + seatsCancelled + " seat(s) for " + username + ".");
        }
    }

    public void displaySeatsAvailability(String flightNumber) {
        Flight flight = flights.get(flightNumber);
        if (flight == null) {
            System.out.println("Invalid flight number.");
        } else {
            System.out.println("Available seats: " + flight.getAvailableSeats());
            System.out.println("Reserved seats: " + (flight.getTotalSeats() - flight.getAvailableSeats()));
            System.out.println("Total seats: " + flight.getTotalSeats());
        }
    }

    public void displayFlights() {
        System.out.println("Flight availability:");
        for (Flight flight : flights.values()) {
        	System.out.println("Flight Name: " + flight.getFlightName());
            System.out.println("Flight Number: " + flight.getFlightNumber());
            System.out.println("Origin: " + flight.getOrigin());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("DepartureTime: " + flight.getDepartureTime());
            System.out.println("ArrivalTime: " + flight.getArrivalTime());
            System.out.println("Available Seats: " + flight.getAvailableSeats());
            System.out.println("-------------------------");
        }
    }
}

public class AirlineReservationSystem {
    public static void main(String[] args) {
        AirlineSystem airlineSystem = new AirlineSystem(100);

        Scanner scanner = new Scanner(System.in);

        boolean isLoggedIn = false;
        String loggedInUsername = "";

        while (!isLoggedIn) {
            displayLoginMenu();
            int loginChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (loginChoice) {
                case 1:
                    System.out.print("Username: ");
                    String enteredUsername = scanner.nextLine();

                    System.out.print("Password: ");
                    String enteredPassword = scanner.nextLine();

                    isLoggedIn = airlineSystem.loginUser(enteredUsername, enteredPassword);
                    if (isLoggedIn) {
                        loggedInUsername = enteredUsername;
                        System.out.println("Login successful! Welcome to the airline portal.");
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;
                case 2:
                    System.out.print("Username: ");
                    String newUsername = scanner.nextLine();

                    System.out.print("Create Password: ");
                    String newPassword = scanner.nextLine();

                    System.out.print("Firstname: ");
                    String firstname = scanner.nextLine();

                    System.out.print("Lastname: ");
                    String lastname = scanner.nextLine();
                    
                    System.out.print("PhoneNumber: ");
                    String phonenumber = scanner.nextLine();

                 //   System.out.print("Enter new password: ");
                   // String newPassword = scanner.nextLine();
                    
                    airlineSystem.registerUser(newUsername, newPassword,firstname,lastname,phonenumber);
                    break;
                case 3:
                    System.out.println("Thank you for using the airline system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        while (true) {
            displayMainMenu();
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (mainChoice) {
                case 1:
                    airlineSystem.displayFlights();
                    System.out.print("Enter the flight number: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Enter number of seats to reserve: ");
                    int numSeats = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    airlineSystem.makeReservation(loggedInUsername, flightNumber, numSeats);
                    break;
                case 2:
                    airlineSystem.displayFlights();
                    System.out.print("Enter the flight number: ");
                    String cancelFlightNumber = scanner.nextLine();
                    System.out.print("Enter number of seats to cancel: ");
                    int cancelSeats = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    airlineSystem.cancelReservation(loggedInUsername, cancelFlightNumber, cancelSeats);
                    break;
                case 3:
                    airlineSystem.displayFlights();
                    System.out.print("Enter the flight number: ");
                    String checkFlightNumber = scanner.nextLine();

                    airlineSystem.displaySeatsAvailability(checkFlightNumber);
                    break;
                case 4:
                    System.out.println("Thank you for using the airline system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayLoginMenu() {
        System.out.println("-- User Registration/Login --");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayMainMenu() {
        System.out.println("\n-- Airline Ticket Booking --");
        System.out.println("1. Make a Reservation");
        System.out.println("2. Cancel Reservation");
        System.out.println("3. Check Seats Availability");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
}
