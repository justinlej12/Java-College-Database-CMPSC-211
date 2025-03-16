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

public class MultiTableQueries {
    private static Connection connection;

    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> schedule = new ArrayList<>();
        String query = "select app.class.courseCode, description, seats " +
                       "from app.class, app.course " +
                       "where semester = ? and app.class.courseCode = app.course.courseCode " +
                       "order by app.class.courseCode";
        try (PreparedStatement getAllClassDescriptions = connection.prepareStatement(query)) {
            getAllClassDescriptions.setString(1, semester);
            try (ResultSet resultSet = getAllClassDescriptions.executeQuery()) {
                while (resultSet.next()) {
                    schedule.add(new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedule;
    }

    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<>();
        String query = "select app.student.studentid, firstname, lastname " +
                       "from app.student, app.schedule " +
                       "where semester = ? and coursecode = ? and status = 'S' " +
                       "and app.student.studentid = app.schedule.studentid " +
                       "order by timestamp";
        try (PreparedStatement getScheduledStudentsByClass = connection.prepareStatement(query)) {
            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);
            try (ResultSet resultSet = getScheduledStudentsByClass.executeQuery()) {
                while (resultSet.next()) {
                    student.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return student;
    }

    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<>();
        String query = "select app.student.studentid, firstname, lastname " +
                       "from app.student, app.schedule " +
                       "where semester = ? and coursecode = ? and status = 'W' " +
                       "and app.student.studentid = app.schedule.studentid " +
                       "order by timestamp";
        try (PreparedStatement getWaitlistedStudentsByClass = connection.prepareStatement(query)) {
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            try (ResultSet resultSet = getWaitlistedStudentsByClass.executeQuery()) {
                while (resultSet.next()) {
                    student.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return student;
    }
}