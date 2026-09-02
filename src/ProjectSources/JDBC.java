package ProjectSources;

import java.sql.*;

public class JDBC {
    
//    private static final String URL ="jdbc:sqlserver://localhost\\SQLEXPRESS:1433;trustServerCertificate=true;databaseName=gangchil_interior_db;user=arvd;password=1131";
//    private static final String USER = "arvd";
//    private static final String PASSWORD = "1131";
//
//
//    public static java.sql.Connection getConnection() throws SQLException
//    {
//        return DriverManager.getConnection(URL,USER,PASSWORD);
//    }   
    
    public static Connection con() {

        try {

            String url =
            "jdbc:sqlserver://localhost:1433;trustServerCertificate=true;databaseName=DiamondHotelDB;encrypt=true";

            String user = "kaashiv";
            String password = "kaashiv";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {

            System.err.println(e);
            return null;
        }
    }

}
