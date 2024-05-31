package miniprojectcorejava;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class Adminstrator {

	private List<Bus> busList = new ArrayList<>();

	private Adminstrator() {
	}

	static Adminstrator busAdminObj = new Adminstrator();

	public static Adminstrator getAdminObj() {
		return busAdminObj;
	}
	
	static Scanner sc = new Scanner(System.in);

	public void addBus() {
		busList.add(new Bus("1", "Rajahmundry", "Vizag", "Seater", "AC", 30,
				Map.of("Rajahmundry", 0, "Annavaram", 90, "Vizag", 210)));
		busList.add(new Bus("2", "Rajahmundry", "Vizag", "Seater", "NonAC", 40,
				Map.of("Rajahmundry", 0, "Annavaram", 90, "Tuni", 120, "Vizag", 210)));
		busList.add(new Bus("3", "Rajahmundry", "Vizag", "Sleeper", "AC", 15,
				Map.of("Rajahmundry", 0, "Annavaram", 90, "Vizag", 210)));
		busList.add(new Bus("4", "Rajahmundry", "Vizag", "Sleeper", "NonAC", 20,
				Map.of("Rajahmundry", 0, "Annavaram", 90, "Tuni" ,120 , "Vizag", 210)));
		busList.add(new Bus("5", "Vizag", "Rajahmundry" ,"Seater", "AC", 30,
				Map.of( "Vizag" , 0, "Annavaram", 125,"Rajahmundry" , 210)));
		busList.add(new Bus("6", "Vizag", "Rajahmundry" ,"Seater", "NonAC", 40,
				Map.of( "Vizag" , 0, "Annavaram", 125,"Tuni" , 145,"Rajahmundry" , 210)));
		busList.add(new Bus("7", "Vizag", "Rajahmundry" ,"Sleeper", "NonAC", 20,
				Map.of( "Vizag" , 0, "Annavaram", 125, "Tuni" , 145 ,"Rajahmundry" , 210)));
		busList.add(new Bus("8", "Vizag", "Rajahmundry" ,"Sleeper", "AC", 15,
				Map.of( "Vizag" , 0, "Annavaram", 125,"Rajahmundry" , 210)));
		busList.add(new Bus("9", "Hyderabad", "Banglore", "Sleeper", "AC", 15,
				Map.of("Hyderabad", 0, "Kurnool", 220, "Banglore", 600)));
		busList.add(new Bus("10", "Hyderabad", "Banglore", "Sleeper", "NonAC", 20,
				Map.of("Hyderabad", 0, "Kurnool", 220, "Anantapur", 350, "Banglore", 600)));
		busList.add(new Bus("11", "Hyderabad", "Banglore", "Seater", "NonAC", 40,
				Map.of("Hyderabad", 0, "Kurnool", 220, "Anantapur", 350 , "Banglore", 600)));
		busList.add(new Bus("12", "Hyderabad", "Banglore", "Seater", "AC", 30,
				Map.of("Hyderabad", 0, "Kurnool", 220, "Banglore", 600)));
	}

	public List<Bus> getBusList() {
		return busList;
	}

	public void setBusList(List<Bus> busList) {
		this.busList = busList;
	}
	
	public void unLockAccount() {
		System.out.println("Enter your Email Id : ");
		String email = sc.nextLine();
		while(UserLogin.validateEmail(email)) {
			System.out.println("No user Exixts with entered mail id\n");
			System.out.println("Re-Enter your Email Id : ");
			email=sc.nextLine();
		}
		System.out.println("Enter your Password : ");
		String password = sc.nextLine();
		while(!UserLogin.validatePassword(email, password)) {
			System.out.println("Invalid Password\n");
			System.out.println("Re-Enter your password : ");
			password=sc.nextLine();
		}
		if(UserSignUp.getSignUpObject().getUserMap().get(email).getAccountStatus().equals("Locked")) {
			UserSignUp.getSignUpObject().getUserMap().get(email).setFailedCount(0);
			UserSignUp.getSignUpObject().getUserMap().get(email).setAccountStatus("Active");
			System.out.println("\nAccount Un Locked");
		}
		else
			System.out.println("Your Account is Already Active !!");
	}
}
