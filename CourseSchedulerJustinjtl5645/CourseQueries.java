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

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static ResultSet resultSet;

    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try (PreparedStatement addCourse = connection.prepareStatement(
                "insert into app.course (coursecode, description) values (?, ?)")) {
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes() {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCode = new ArrayList<>();
        try (PreparedStatement getAllCourseCodes = connection.prepareStatement(
                "select coursecode from app.course order by coursecode");
             ResultSet resultSet = getAllCourseCodes.executeQuery()) {
            while (resultSet.next()) {
                courseCode.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courseCode;
    }
}