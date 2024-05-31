package miniprojectcorejava;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class UserSignUp {
	Map<String, UserAccount> userMap = new HashMap<>();

	private UserSignUp() {
	}

	static UserSignUp signUpObj = new UserSignUp();

	public static UserSignUp getSignUpObject() {
		return signUpObj;
	}

	public Map<String, UserAccount> getUserMap() {
		return userMap;
	}

	static Scanner sc = new Scanner(System.in);

	public void storeData() {
		System.out.println("Please Enter Your emailID :");
		String email = sc.nextLine();
		while (isExistingUser(email)) {
			System.out.println("User With This Email-Id Already Exists!!");
			System.out.println("\nPlease Enter A Different Mail-Id :)");
			email = sc.nextLine();
		}
		while (!isValidEmail(email)) {
			System.out.println("Please Enter A Valid Email ID");
			System.out.println("\nPlease Enter A Different Mail-Id :)");
			email = sc.nextLine(); 
		}

		System.out.println("Please Enter Your Password :");
		String password = sc.nextLine();
		while (!signUpObj.isValidPassword(password)) {
			System.out.println("InValid Password!!\n");
			System.out.println(
					"Your password should Contain Atleast 1 Upper Case Letter , Atleast 1 Lower Case Letter , Atleast 1 Digit And Length Should Be Atleast 6\n");
			System.out.println("Please Enter Your Password Again:");
			password = sc.nextLine();
		}

		System.out.println("Please Enter Your First Name :");
		String firstName = sc.nextLine();
		while (!signUpObj.isValidName(firstName)) {
			System.out.println("InValid name!!!\n");
			System.out.println("Please Enter a Valid Name Again:");
			firstName = sc.nextLine();
		}

		System.out.println("Please Enter Your Last Name :");
		String lastName = sc.nextLine();
		while (!signUpObj.isValidName(lastName)) {
			System.out.println("InValid name!!!\n");
			System.out.println("Please Enter a Valid Name Again:");
			lastName = sc.nextLine();
		}

		System.out.println("Please Enter Your Mobile Number :");
		String mobileNumber = sc.nextLine();
		while (!signUpObj.isValidMobileNumber(mobileNumber)) {
			System.out.println("Please Enter a Valid 10 Digit Number!!\n");
			System.out.println("Please Enter Your Mobile Number Again:\n");
			mobileNumber = sc.nextLine();
		}

		System.out.println("Please Enter Your Gender :");
		String gender = sc.nextLine();

		userMap.put(email, new UserAccount(firstName, lastName, mobileNumber, gender, email, password));
		System.out.println("\nUser Sign Up Successful :)");
	}

	private boolean isValidPassword(String password) {
		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		boolean hasDigit = false;
		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c))
				hasUpperCase = true;
			else if (Character.isLowerCase(c))
				hasLowerCase = true;
			else if (Character.isDigit(c))
				hasDigit = true;
		}
		return hasLowerCase && hasUpperCase && hasDigit && password.length() >= 6;
	}

	private boolean isValidName(String name) {
		return name.trim().matches("[a-zA-Z]+");
	}

	private boolean isValidMobileNumber(String mobileNumber) {
		return (mobileNumber.matches("[0-9]*$")) && (mobileNumber.length() == 10);
	}

	private boolean isExistingUser(String email) {
		return userMap.get(email) != null;
	}

	private boolean isValidEmail(String email) {
		return email.contains("@") && email.contains(".") && !email.contains("@.");
	}

}
