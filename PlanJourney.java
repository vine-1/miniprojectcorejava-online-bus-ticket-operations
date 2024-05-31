package miniprojectcorejava;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;
public class PlanJourney {



	static TreeSet<String> locations = new TreeSet<>();
	static Scanner sc = new Scanner(System.in);

	public boolean bookTicket() {
		displayAllStations();
		System.out.println("\nPlease Choose Your Boarding Station : \n");
		String boardingStation = sc.nextLine();
		while (!isValidStation(boardingStation)) {
			System.out.println("Oh Oh!! We Couldn't Find Entered Station (-_-)");
			System.out.println("\nPlease Re-Enter A Valid Station :");
			boardingStation = sc.nextLine();
		}

		displayAllStations();
		System.out.println("\nPlease Choose Your Destination : \n");
		String destinationStation = sc.nextLine();
		while (!isValidStation(destinationStation)) {
			System.out.println("Oh Oh!! We Couldn't Find Entered Station (-_-)");
			System.out.println("\nPlease Re-Enter A Valid Station :");
			destinationStation = sc.nextLine();
		}

		System.out.println("Enter Date OF Your Journey in 'YYYY-MM-DD Format':");
		String date = sc.nextLine();
		while (!isValidDate(date)) {
			System.out.println("\nPlease Re-Enter A Valid Date :");
			date = sc.nextLine();
		}

		List<Bus> availableBusList = getAvailableBuses(boardingStation, destinationStation);
		if (availableBusList.isEmpty()) {
			System.out.println("Sorry No Buses Are Available in Selected Route!!\n");
			System.out.println("Please Choose A Different Route :) ");
			bookTicket();
		}

		printBuses(availableBusList, boardingStation, destinationStation);

		System.out.println("Enter Bus Number to Book Seats : ");
		String busNum = sc.nextLine();
		while (getPassengerBus(availableBusList, busNum).isEmpty()) {
			System.out.println("You Have Entetered an Invalid Bus Number!!\n");
			System.out.println("Enter A Correct Bus Number : ");
			busNum = sc.nextLine();
		}

		Bus passengerBus = getPassengerBus(availableBusList, busNum).get(0);

		System.out.println("Enter Number of Passengers : ");
		int passengersCount = sc.nextInt();
		sc.nextLine();
		while (!isValidPassengerCount(passengerBus, passengersCount)) {
			System.out.println("Re-Enter Valid Number of Passengers : ");
			passengersCount = sc.nextInt();
			sc.nextLine();
		}

		List<Passenger> passengersList = storePassenger(passengersCount);
		Bill.printBill(passengerBus, boardingStation, destinationStation, passengersList, LocalDate.parse(date));

		System.out.println("\nChoose From Below Options : \n1 Confirm Booking\n2 Cancel Booking");
		while (true) {
			int choice = sc.nextInt();
			sc.nextLine();
			switch (choice) {
			case 1:
				if (isHuman()) {
					passengerBus.setVacancies(passengerBus.getVacancies() - passengersCount);
					System.out.println("\nThank You!!! Your Booking Has Been Confirmed !!");
					return true;
				}
				break;
			case 2:
				System.out.println("Sorry We Couldn't Confirm Your Booking !!");
				return false; 
			default:
				System.out.println("Please Enter A Proper Option !!");
				break;
			}
		}
	}

	private static void displayAllStations() {
		Adminstrator.getAdminObj().getBusList().forEach(bus -> bus.getStationsMap().forEach((k, v) -> locations.add(k)));
		locations.stream().forEach(loc -> System.out.println(loc));
	}

	private boolean isValidStation(String station) {
		return locations.stream().anyMatch(station::equalsIgnoreCase);
	}

	private static boolean isValidDate(String date) {
		try {
			LocalDate inputDate = LocalDate.parse(date);
			if (inputDate.isBefore(LocalDate.now())) {
				System.out.println("\nEntered Date is Behind Today!!");
				return false;
			}
			if (ChronoUnit.DAYS.between(LocalDate.now(), inputDate) > 90) {
				System.out.println("\nYou Cant Book More than 3 Months in Advance!!");
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private static List<Bus> getAvailableBuses(String boardingStation, String destinationStation) {

		return Adminstrator.getAdminObj().getBusList().stream()
				.filter(bus -> (bus.getStationsMap().containsKey(boardingStation)
						&& bus.getStationsMap().containsKey(destinationStation))
						&& getPrice(bus, boardingStation, destinationStation) >= 0)
				.collect(Collectors.toList());
	}

	private static void printBuses(List<Bus> availableBusList, String boardingStation, String destinationStation) {

		availableBusList.stream()
				.forEach(bus -> System.out.printf("Bus Number : %s\t%S\t-\t%S\tExpress\t%S\t%S%n%S\t-\t%S\t%7.2f%n%n",
						bus.getBusNumber(), bus.getSourceStation(), bus.getDestStation(), bus.getBusType(),
						bus.getCategory(), boardingStation, destinationStation,
						getPrice(bus, boardingStation, destinationStation)));
	}

	protected static double getPrice(Bus bus, String boardingStation, String destinationStation) {
		if (bus.getCategory().equalsIgnoreCase("AC")) {
			if (bus.getBusType().equalsIgnoreCase("Seater"))
				return (bus.getStationsMap().get(destinationStation) - bus.getStationsMap().get(boardingStation)) * 3.5;
			else
				return (bus.getStationsMap().get(destinationStation) - bus.getStationsMap().get(boardingStation)) * 5.5;
		} else {
			if (bus.getBusType().equalsIgnoreCase("Seater"))
				return (bus.getStationsMap().get(destinationStation) - bus.getStationsMap().get(boardingStation)) * 1.5;
			else
				return (bus.getStationsMap().get(destinationStation) - bus.getStationsMap().get(boardingStation)) * 2.5;
		}
	}

	private static List<Bus> getPassengerBus(List<Bus> availableBusList, String busNum) {
		return availableBusList.stream().filter(bus -> bus.getBusNumber().equals(busNum))
				.collect(Collectors.toList());
	}

	private static boolean isValidPassengerCount(Bus passengerBus, int passengersCount) {
		if (passengersCount > 6) {
			System.out.println("You Can choose a Max Of 6 Passengers in a Single Transaction");
			return false;
		} else if (passengersCount < 1) {
			System.out.println("You Should Choose Atleast 1 Passenger to Book Tickets");
			return false;
		} else if (passengerBus.getVacancies() < passengersCount) {
			System.out.println("Sorry We Have Only " + passengerBus.getVacancies() + " Seats Left :)");
			return false;
		}
		return true;
	}

	private static List<Passenger> storePassenger(int passengerCount) {
		List<Passenger> passengerList = new ArrayList<>(passengerCount);
		for (int i = 1; i <= passengerCount; i++) {
			System.out.println("Please Enter " + i + " Passenger Name : ");
			String name = sc.nextLine();
			System.out.println("Please Enter " + i + " Passenger Gender :");
			String gender = sc.nextLine();
			System.out.println("Please Enter " + i + " Passenger Age : ");
			int age = sc.nextInt();
			sc.nextLine();
			passengerList.add(new Passenger(name, gender, age));
		}
		return passengerList;
	}

	protected static boolean isWeekend(LocalDate date) {
		return (date.getDayOfWeek().toString().equalsIgnoreCase("SATURDAY")
				|| date.getDayOfWeek().toString().equalsIgnoreCase("SUNDAY"));
	}

	private static boolean isHuman() {
		String captcha = generateCaptcha();
		System.out.println("Captcha : - " + captcha);
		System.out.println("\nPlease Enter The Above Captcha to Confirm Your Booking : ");
		String entry = sc.nextLine();
		while (!captcha.equals(entry)) {
			System.out.println("\nYou Have Entered an Incorrect Captcha!!\n");
			captcha = generateCaptcha();
			System.out.println("Captcha : - " + captcha);
			System.out.println("\nPlease Enter The Above Captcha to Confirm Your Booking : ");
			entry = sc.nextLine();
		}
		return true;
	}

	private static String generateCaptcha() {
		Random rand = new Random();
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder captcha = new StringBuilder();
		for (int i = 1; i <= 6; i++) {
			captcha.append(chars.charAt(rand.nextInt(61)));
		}
		return captcha.toString();
	}
}
