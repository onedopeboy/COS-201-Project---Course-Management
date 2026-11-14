
public class Course {

    // Private attributes of a course
    private String courseCode;
    private String courseTitle;
    private int creditUnits;

    // Constructor
    public Course(String courseCode, String courseTitle, int creditUnits) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.courseTitle = courseTitle.trim();
        this.creditUnits = creditUnits;
    }

    // Method to get course code
    public String getCourseCode() {
        return courseCode;
    }

    // Method to get course title
    public String getCourseTitle() {
        return courseTitle;
    }

    // Method to get credit units
    public int getCreditUnits() {
        return creditUnits;
    }

    // Method to display course details
    public void displayCourse() {
        System.out.printf("%-15s %-35s %-10d%n",
                courseCode, courseTitle, creditUnits);
    }

    // Method to convert course details into a file format
    public String toFileString() {
        return courseCode + "|" + courseTitle + "|" + creditUnits;
    }
}
