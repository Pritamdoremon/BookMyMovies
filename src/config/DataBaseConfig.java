package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfig {
  static String url = "jdbc:postgresql://localhost:5432/bookmymovies";
  static String username = "postgres";
  static String password = "8597";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, username, password);
  }
}
