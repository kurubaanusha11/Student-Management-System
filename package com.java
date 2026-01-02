package com.tap;
import java.sql.*;
import java.util.Scanner;

public class StudentManagementJDBC {
	    private static final String URL = "jdbc:mysql://localhost:3306/student";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "root";
    // ===== GET DATABASE CONNECTION =====
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // ===== ADD STUDENT =====
    public static void addStudent(int id, String name, String course, int marks) {
        String sql = "INSERT INTO students (id, name, course, marks) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setInt(4, marks);

            ps.executeUpdate();
            System.out.println("✅ Student Added Successfully");

        } catch (SQLException e) {
            System.out.println("❌ Error Adding Student");
            e.printStackTrace();
        }
    }

    // ===== VIEW STUDENTS =====
    public static void viewStudents() {
        String sql = "SELECT * FROM students";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID | Name | Course | Marks");
            System.out.println("--------------------------------");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("course") + " | " +
                    rs.getInt("marks")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error Fetching Students");
            e.printStackTrace();
        }
    }

    // ===== UPDATE STUDENT =====
    public static void updateStudent(int id, int marks) {
        String sql = "UPDATE students SET marks=? WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, marks);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Student Updated Successfully");
            } else {
                System.out.println("⚠ Student ID Not Found");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error Updating Student");
            e.printStackTrace();
        }
    }

    // ===== DELETE STUDENT =====
    public static void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Student Deleted Successfully");
            } else {
                System.out.println("⚠ Student ID Not Found");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error Deleting Student");
            e.printStackTrace();
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n---- Student Management System ----");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();

                    addStudent(id, name, course, marks);
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int uid = sc.nextInt();

                    System.out.print("Enter New Marks: ");
                    int newMarks = sc.nextInt();

                    updateStudent(uid, newMarks);
                    break;

                case 4:
                    System.out.print("Enter ID: ");
                    int did = sc.nextInt();

                    deleteStudent(did);
                    break;

                case 5:
                    System.out.println("👋 Application Closed");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid Choice");
            }
        }
    }
}

