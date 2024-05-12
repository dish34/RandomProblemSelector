import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomProblemSelector {

	private static final String RANDOM_PROBLEM_SELECTED_TXT = "RandomProblemSelected.txt";
	private static final String PROBLEM_FILE = "Problems.txt";
	private static List<String> problems = new ArrayList<>();

	public static void saveProblemsToFile(String file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (String problem : problems) {
				writer.write(problem);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("An error occurred while writing to file: " + e.getMessage());
		}
	}

	public static String selectRandomProblem(List<String> problems) {
		if (problems.isEmpty()) {
			return null;
		}
		Random random = new Random();
		int randomIndex = random.nextInt(problems.size());
		return problems.get(randomIndex);
	}

	public static void saveProblemToFile(String problem, String file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(problem);
		} catch (IOException e) {
			System.err.println("An error occurred while writing to file: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		// List<String> problems = new ArrayList<String>();

		// final String PROBLEM_FILE = PROBLEM_FILE;

		if (fileExistsAndNotEmpty(PROBLEM_FILE)) {
			System.out.println("File Exists and not empty");

			try {
				problems = Files.readAllLines(Paths.get(PROBLEM_FILE));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			problems = readProblemsFromInput();
		}
		if (!problems.isEmpty()) {
			saveProblemsToFile(PROBLEM_FILE);
			String randomProblem = selectRandomProblem(problems);
			if (randomProblem != null) {
				saveProblemToFile(randomProblem, RANDOM_PROBLEM_SELECTED_TXT);
				removeRandomProblemFromFile(randomProblem, PROBLEM_FILE);
				System.out.println("Randomly selected problem: " + randomProblem);
				System.out.println("Randomly selected problem saved to file: RandomProblemSelected.txt");
			} else {
				System.out.println("No problems entered.");
			}
		}

		else {
			System.out.println("No problems entered.");
		}
	}

	private static void removeRandomProblemFromFile(String randomProblem,
			String randomProblemFile) {
		// TODO Auto-generated method stub
		problems.removeIf(problem -> problem.contains(randomProblem));
		Path path = Paths.get(randomProblemFile);
		try {
			Files.write(path, problems);
			System.out.println("Removing " + randomProblem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean fileExistsAndNotEmpty(String filePath) {
		// Check if the file exists
		File file = new File(filePath);
		if (!file.exists()) {
			return false; // File does not exist
		}

		// Check if the file has content
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath));
			return !lines.isEmpty(); // Return true if file has content
		} catch (IOException e) {
			e.printStackTrace(); // Handle IO exception if occurred
			return false; // Return false in case of exception
		}
	}

	private static List<String> readProblemsFromInput() {
		List<String> problems = new ArrayList<>();

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter problems (type 'THEEND' on a new line to stop):");

			String input;
			while (!(input = scanner.nextLine()).equals("THEEND")) {
				problems.add(input);
			}
		} catch (Exception e) {
			System.err.println("An error occurred while reading input: " + e.getMessage());
		}
		return problems;
	}
}
