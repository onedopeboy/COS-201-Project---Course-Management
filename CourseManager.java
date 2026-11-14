
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CourseManager {

    // ArrayList to store course objects
    private ArrayList<Course> courses = new ArrayList<>();

    // Method to add a new course
    public void addCourse(Scanner scanner) {

        try {
            System.out.println("\n--- ADD NEW COURSE ---");

            System.out.print("Enter Course Code: ");
            String code = scanner.nextLine().trim().toUpperCase();

            if (code.isEmpty() || code.contains("|")) {
                throw new IllegalArgumentException(
                    "Course code cannot be empty or contain |."
                );
            }

            // Check for duplicate course codes
            if (searchCourse(code) != null) {
                System.out.println("Error: This course already exists.");
                return;
            }

            System.out.print("Enter Course Title: ");
            String title = scanner.nextLine().trim();

            if (title.isEmpty() || title.contains("|")) {
                throw new IllegalArgumentException(
                    "Course title cannot be empty or contain |."
                );
            }

            System.out.print("Enter Credit Units: ");
            int units = Integer.parseInt(scanner.nextLine().trim());

            if (units <= 0) {
                throw new IllegalArgumentException(
                    "Credit units must be greater than zero."
                );
            }

            // Create a new Course object
            Course newCourse = new Course(code, title, units);

            // Add the object to the ArrayList
            courses.add(newCourse);

            System.out.println("\nCourse added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number for units.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to display all courses
    public void viewAllCourses() {

        System.out.println("\n========== COURSE LIST ==========");

        if (courses.isEmpty()) {
            System.out.println("No courses have been recorded.");
            return;
        }

        System.out.printf("%-15s %-35s %-10s%n",
                "COURSE CODE", "COURSE TITLE", "UNITS");

        System.out.println("--------------------------------------------------------------");

        for (Course course : courses) {
            course.displayCourse();
        }

        System.out.println("--------------------------------------------------------------");
    }

    // Method to search for a course by code
    public Course searchCourse(String code) {

        String searchCode = code.trim().toUpperCase();

        // Use recursive search
        return recursiveSearch(0, searchCode);
    }

    // Recursive search method
    private Course recursiveSearch(int index, String code) {

        // Base case: course not found
        if (index >= courses.size()) {
            return null;
        }

        Course currentCourse = courses.get(index);

        // Check if the current course matches
        if (currentCourse.getCourseCode().equals(code)) {
            return currentCourse;
        }

        // Recursive call
        return recursiveSearch(index + 1, code);
    }

    // Method to calculate total credit units
    public int calculateTotalUnits() {

        int total = 0;

        for (Course course : courses) {
            total += course.getCreditUnits();
        }

        return total;
    }

    // Method to search and display a course
    public void findAndDisplayCourse(Scanner scanner) {

        System.out.println("\n--- SEARCH COURSE ---");

        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine().trim().toUpperCase();

        Course foundCourse = searchCourse(code);

        if (foundCourse != null) {

            System.out.println("\nCourse Found!");

            System.out.printf("%-15s %-35s %-10s%n",
                    "COURSE CODE", "COURSE TITLE", "UNITS");

            foundCourse.displayCourse();

        } else {
            System.out.println("Course not found.");
        }
    }

    // Method to display total units
    public void displayTotalUnits() {

        int total = calculateTotalUnits();

        System.out.println("\n--- TOTAL CREDIT UNITS ---");
        System.out.println("Total number of courses: " + courses.size());
        System.out.println("Total credit units: " + total);
    }

    // Method to save courses to a file
    public void saveCoursesToFile(String fileName)
            throws IOException {

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(fileName))) {

            for (Course course : courses) {
                writer.write(course.toFileString());
                writer.newLine();
            }
        }

        System.out.println("Courses saved successfully to " + fileName);
    }

    // Method to load courses from a file
    public void loadCoursesFromFile(String fileName)
            throws IOException {

        ArrayList<Course> loadedCourses = new ArrayList<>();

        try (BufferedReader reader =
                new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] details = line.split("\\|", -1);

                if (details.length != 3) {
                    throw new IOException("Invalid course data in file.");
                }

                String code = details[0].trim().toUpperCase();
                String title = details[1].trim();
                int units;

                try {
                    units = Integer.parseInt(details[2].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid credit units in file.");
                }

                if (code.isEmpty() || title.isEmpty()
                        || units <= 0) {
                    throw new IOException("Invalid course details in file.");
                }

                // Check for duplicate courses in the file
                for (Course existing : loadedCourses) {
                    if (existing.getCourseCode().equals(code)) {
                        throw new IOException("Duplicate course code in file.");
                    }
                }

                loadedCourses.add(new Course(code, title, units));
            }
        }

        // Replace existing courses after successful loading
        courses.clear();
        courses.addAll(loadedCourses);

        System.out.println("Courses loaded successfully!");
        System.out.println("Total courses loaded: " + courses.size());
    }
}
