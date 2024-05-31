package miniprojectcorejava;
import java.time.LocalDate;
import java.util.List;
public class Bill {
public static void printBill(Bus bus, String boardingStation, String destination, List<Passenger> passengersList,
			LocalDate date) {
		printHorizontal();
		System.out.printf("%n|%38s%36s%n", "Bill", "|");
		printHorizontal();
		System.out.printf("%n| %-10s%-20S-%20S%7S%13S%2s", bus.getBusNumber(), boardingStation, destination,
				bus.getCategory(), bus.getBusType(), "|");
		printPassengers(passengersList);
		int rem = 13 - (passengersList.size() * 2);
		printVertical(rem);
		double price = PlanJourney.getPrice(bus, boardingStation, destination);
		printPrice(price * passengersList.size(), date);
		printHorizontal();
	}

	private static void printHorizontal() {
		System.out.print(" ");
		for (int i = 1; i < 74; i++) {
			System.out.print("-");
		}
	}

	private static void printPassengers(List<Passenger> passengersList) {
		String ageCategory;
		for (int i = 0; i < passengersList.size(); i++) {
			if (passengersList.get(i).getAge() < 18) {
				ageCategory = "Child";
			} else if (passengersList.get(i).getAge() >= 18 && passengersList.get(i).getAge() < 60) {
				ageCategory = "Adult";
			} else {
				ageCategory = "Senior Citizen";
			}
			System.out.printf("%n|%74s%n| %-20s%-7d%-7S%-15S%24s", "|", passengersList.get(i).getName(),
					passengersList.get(i).getAge(), passengersList.get(i).getGender(), ageCategory, "|");
		}
	}

	private static void printVertical(int count) {
		for (int i = 1; i <= count; i++) {
			System.out.printf("%n|" + "%74s", "|");
		}
	}

	private static void printPrice(double price, LocalDate date) {
		double weekendSurge = 0.00;
		double gst = 5 * price / 100;
		if (PlanJourney.isWeekend(date)) {
			weekendSurge = 200.00;
		}
		double total = price + gst + weekendSurge;
		System.out.printf("%n%-48s%-13S - %8.2f%3s", "|", "Base Fare", price, "|");
		System.out.printf("%n%-48s%-13S - %8.2f%3s", "|", "weekend surge", weekendSurge, "|");
		System.out.printf("%n%-48s%-13S - %8.2f%3s", "|", "gst", gst, "|");
		System.out.printf("%n%-65s--------%2s", "|", "|");
		System.out.printf("%n%-48s%-13S - %8.2f%3s", "|", "Total", total, "|");
		System.out.printf("%n%-65s--------%2s%n", "|", "|");
	}

}

