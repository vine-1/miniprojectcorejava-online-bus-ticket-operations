package miniprojectcorejava;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
public class UserLogin {
	static Scanner sc = new Scanner(System.in);

	public boolean login() {
		UserSignUp signUpObj = UserSignUp.getSignUpObject();
		System.out.println("\nPlease Enter Your Registered Email Id :");
		String email = sc.nextLine();

		while (validateEmail(email)) {
			System.out.println("No User Exists With Entered Email ID!!\n");
			System.out.println("Please Choose Below Options");
			System.out.println("\n1 Re-Enter Mail Id\n2 Create an account");
			String choice = sc.nextLine();
			switch (choice) {
			case "1":
				System.out.println("\nRe-Enter Your Registered Email Id :");
				email = sc.nextLine();
				break;
			case "2":
				signUpObj.storeData();
				return false;
			default:
				System.out.println("Oh Oh!! You Have Opted a Wrong Option (-_-)");
				break;
			}
		}

		int failedCount = findUser(email).get(email).getFailedCount();
		System.out.println("\nPlease Enter Your Password :");
		String password = sc.nextLine();
		if (failedCount >= 5) {
			System.out.println("Your Account Is Already Locked !!");
			return false;
		}

		while (!validatePassword(email, password)) {
			failedCount++;
			if (failedCount == 5) {
				System.out.println("\nOh Oh!!You Have Exceeded Your Number of Login Attempts");
				System.out.println("\nYour Account is currently Locked..");
				findUser(email).get(email).setAccountStatus("Locked");
				findUser(email).get(email).setFailedCount(failedCount);
				return false;
			} else {
				System.out.println("\nYou Have Entered Incorrect Password!!!\n");
				findUser(email).get(email).setFailedCount(failedCount);
				System.out.println("\nRe-Enter Your Password :");
				password = sc.nextLine();
			}
		}

		System.out.println("\nLogin Successful");
		System.out.println("\nWelcome " + findUser(email).get(email).getFirstName());
		findUser(email).get(email).setFailedCount(0);
		return true;

	}

	public static boolean validateEmail(String email) {
		return findUser(email).isEmpty();
	}

	public static boolean validatePassword(String email, String password) {
		return findUser(email).get(email).getPassword().equals(password);
	}

	private static Map<String, UserAccount> findUser(String email) {
		Map<String, UserAccount> usersMap = UserSignUp.getSignUpObject().getUserMap();
		return usersMap.entrySet().stream().filter(user -> user.getKey().equals(email))
				.collect(Collectors.toMap(user -> user.getKey(), user -> user.getValue()));
	}
}
