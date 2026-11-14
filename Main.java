
import java.util.Scanner;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Create Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Create CourseManager object
        CourseManager manager = new CourseManager();

        // File used to store course information
        String fileName = "courses.txt";

        int choice = 0;

        System.out.println("==============================================");
        System.out.println("     STUDENT COURSE MANAGEMENT SYSTEM");
        System.out.println("             COS 201 PROJECT");
        System.out.println("==============================================");

        // Main program loop
        while (choice != 7) {

            System.out.println("\n============== MAIN MENU ==============");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by Code");
            System.out.println("4. Calculate Total Credit Units");
            System.out.println("5. Save Courses to File");
            System.out.println("6. Load Courses from File");
            System.out.println("7. Exit Program");
            System.out.println("=======================================");

            System.out.print("Enter your choice (1-7): ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {

                    case 1:
                        manager.addCourse(scanner);
                        break;

                    case 2:
                        manager.viewAllCourses();
                        break;

                    case 3:
                        manager.findAndDisplayCourse(scanner);
                        break;

                    case 4:
                        manager.displayTotalUnits();
                        break;

                    case 5:
                        try {
                            manager.saveCoursesToFile(fileName);
                        } catch (IOException e) {
                            System.out.println(
                                "Error saving courses: " + e.getMessage()
                            );
                        }
                        break;

                    case 6:
                        try {
                            manager.loadCoursesFromFile(fileName);
                        } catch (IOException e) {
                            System.out.println(
                                "Error loading courses: " + e.getMessage()
                            );
                        }
                        break;

                    case 7:
                        System.out.println("\nThank you for using");
                        System.out.println(
                            "Student Course Management System!"
                        );
                        break;

                    default:
                        System.out.println(
                            "Invalid choice! Please select between 1 and 7."
                        );
                }

            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid input! Please enter a number between 1 and 7."
                );
            }
        }

        // Close Scanner
        scanner.close();
    }
}
