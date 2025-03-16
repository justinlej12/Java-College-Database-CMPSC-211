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

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getWaitlistedStudentsByClass;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;

    public static void addScheduleEntry(ScheduleEntry scheduleEntry) {
        connection = DBConnection.getConnection();
        String query = "insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)";
        try (PreparedStatement addScheduleEntry = connection.prepareStatement(query)) {
            addScheduleEntry.setString(1, scheduleEntry.getSemester());
            addScheduleEntry.setString(2, scheduleEntry.getCourseCode());
            addScheduleEntry.setString(3, scheduleEntry.getStudentID());
            addScheduleEntry.setString(4, scheduleEntry.getStatus());
            addScheduleEntry.setTimestamp(5, scheduleEntry.getTimestamp());
            addScheduleEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        String query = "select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentid = ? order by timestamp";
        try (PreparedStatement getScheduleByStudent = connection.prepareStatement(query)) {
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            try (ResultSet resultSet = getScheduleByStudent.executeQuery()) {
                while (resultSet.next()) {
                    schedule.add(new ScheduleEntry(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5)
                    ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedule;
    }

    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;
        String query = "select count(studentid) from app.schedule where semester = ? and coursecode = ?";
        try (PreparedStatement getScheduledStudentCount = connection.prepareStatement(query)) {
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            try (ResultSet resultSet = getScheduledStudentCount.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return count;
    }

    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<>();
        String query = "select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = 'W' order by timestamp";
        try (PreparedStatement getWaitlistedStudentsByClass = connection.prepareStatement(query)) {
            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);
            try (ResultSet resultSet = getWaitlistedStudentsByClass.executeQuery()) {
                while (resultSet.next()) {
                    students.add(new ScheduleEntry(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5)
                    ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return students;
    }

    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        String query = "delete from app.schedule where semester = ? and studentid = ? and coursecode = ?";
        try (PreparedStatement dropStudentScheduleByCourse = connection.prepareStatement(query)) {
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        String query = "delete from app.schedule where semester = ? and coursecode = ?";
        try (PreparedStatement dropScheduleByCourse = connection.prepareStatement(query)) {
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void updateScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        String query = "update app.schedule set status = 'S' where semester = ? and coursecode = ? and studentid = ?";
        try (PreparedStatement updateScheduleEntry = connection.prepareStatement(query)) {
            updateScheduleEntry.setString(1, entry.getSemester());
            updateScheduleEntry.setString(2, entry.getCourseCode());
            updateScheduleEntry.setString(3, entry.getStudentID());
            updateScheduleEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}