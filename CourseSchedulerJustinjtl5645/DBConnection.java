/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CourseSchedulerJustinjtl5645;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acv
 */
public class DBConnection {
    private static Connection connection;
    private static final String user = "java";
    private static final String password = "java";
    private static final String database = "jdbc:derby://localhost:1527/CourseSchedulerDBJustinjtl5645;create=true";

    public static Connection getConnection()
    {
        if (connection == null)
        {
            try
            {
                connection = DriverManager.getConnection(database, user, password);
            } catch (SQLException e){
                e.printStackTrace();
                System.out.println("Could not open database.");
                System.exit(1);
            }
        }
        return connection;
    }

    
}
