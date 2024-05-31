package miniprojectcorejava;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class LogoDisplay {
public static void logoDisplay(String path) {
		try(FileInputStream fis = new FileInputStream(new File(path))) {
			int temp = 0;
			while ((temp = fis.read()) != -1) {
				System.out.print((char) temp);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Oh Oh!!!! We Couldn't Fetch The Logo (-_-)");
//			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
