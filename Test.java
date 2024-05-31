package miniprojectcorejava;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Test {
	Map<String, UserAccount> userMap = new HashMap<>();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean isLoggedin = false;
		LogoDisplay.logoDisplay("Logo.txt");
		Adminstrator.getAdminObj().addBus();
		boolean flag = true;
		while (flag) {
			printMenu();
			String choice = sc.nextLine();
			switch (choice) {

			case "1":
				if (isLoggedin)
					System.out.println("Log Out Before Trying to Sign Up");
				else {
					UserSignUp.getSignUpObject().storeData();
					System.out.println("Please Login Now!!");
				}
				break;
				
			case "2":
				if (isLoggedin) {
					System.out.println("You Are Already Logged In!!");
					break;
				}
				UserLogin loginObj = new UserLogin();
				if (loginObj.login())
					isLoggedin = true;
				else
					System.out.println("Login Un Successful !!");
				break;
				
			case "3":
				if (isLoggedin) {
					PlanJourney journeyObj = new PlanJourney();
					journeyObj.bookTicket();
				} else
					System.out.println("Please Login to Book Tickets !!");
				break;
				
			case "4":
				if (isLoggedin) {
					isLoggedin = false;
					System.out.println("\nSuccessfully Logged Out !!");
				} else
					System.out.println("\nNo User Logged In");
				break;
				
			case "5":
				if (isLoggedin)
					System.out.println("Log Out Before Trying to Unlock Your Account");
				else
					Adminstrator.getAdminObj().unLockAccount();
				break;
				
			case "6":
				flag = false;
				break;
				
			default:
				System.out.println("Please Choose A Valid Option !");
				break;
			}
		}
	}

	private static void printMenu() {
		System.out.println("\nPlease Choose From Below Options : \n");
		System.out
				.println("1 Create Account\n2 Login\n3 Book Tickets\n4 Log Out\n5 Un Lock Account\n6 Exit Application");
	}
}
