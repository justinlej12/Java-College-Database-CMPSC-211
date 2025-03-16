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

public class ClassQueries {
    private static Connection connection;

    public static void addClass(ClassEntry classEntry) {
        connection = DBConnection.getConnection();
        String query = "insert into app.class (semester, coursecode, seats) values (?, ?, ?)";
        try (PreparedStatement addClass = connection.prepareStatement(query)) {
            addClass.setString(1, classEntry.getSemester());
            addClass.setString(2, classEntry.getCourseCode());
            addClass.setInt(3, classEntry.getSeats());
            addClass.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>();
        String query = "select coursecode from app.class where semester = ? order by coursecode";
        try (PreparedStatement getAllCourseCodes = connection.prepareStatement(query)) {
            getAllCourseCodes.setString(1, semester);
            try (ResultSet resultSet = getAllCourseCodes.executeQuery()) {
                while (resultSet.next()) {
                    courseCodes.add(resultSet.getString(1));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }

    public static int getClassSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;
        String query = "select seats from app.class where semester = ? and coursecode = ?";
        try (PreparedStatement getClassSeats = connection.prepareStatement(query)) {
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);
            try (ResultSet resultSet = getClassSeats.executeQuery()) {
                if (resultSet.next()) {
                    seats = resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return seats;
    }

    public static void dropClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        String query = "delete from app.class where semester = ? and coursecode = ?";
        try (PreparedStatement dropClass = connection.prepareStatement(query)) {
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

