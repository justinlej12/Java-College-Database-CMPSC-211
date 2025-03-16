/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CourseSchedulerJustinjtl5645;


/**
 *
 * @author JustinLejeune
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;

    public static void addStudent(StudentEntry studentEntry) {
        connection = DBConnection.getConnection();
        String query = "insert into app.student (studentid, firstname, lastname) values (?, ?, ?)";
        try (PreparedStatement addStudent = connection.prepareStatement(query)) {
            addStudent.setString(1, studentEntry.getStudentID());
            addStudent.setString(2, studentEntry.getFirstName());
            addStudent.setString(3, studentEntry.getLastName());
            addStudent.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<>();
        String query = "select studentid, firstname, lastname from app.student order by studentid";
        try (PreparedStatement getAllStudents = connection.prepareStatement(query);
             ResultSet resultSet = getAllStudents.executeQuery()) {
            while (resultSet.next()) {
                student.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return student;
    }

    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        String query = "select studentid, firstname, lastname from app.student where studentid = ?";
        try (PreparedStatement getStudent = connection.prepareStatement(query)) {
            getStudent.setString(1, studentID);
            try (ResultSet resultSet = getStudent.executeQuery()) {
                while (resultSet.next()) {
                    student = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return student;
    }

    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        String query = "delete from app.student where studentid = ?";
        try (PreparedStatement dropStudent = connection.prepareStatement(query)) {
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

