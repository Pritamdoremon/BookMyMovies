package service;

import config.DataBaseConfig;

import java.sql.*;
import java.util.Scanner;

public class BookMyMovieSys {
    Scanner sc = new Scanner(System.in);

    public int registerUser() {
        try (Connection con = DataBaseConfig.getConnection()) {
            System.out.println("Enter Name:");
            String name = sc.nextLine();
            System.out.println("Enter Email:");
            String email = sc.nextLine();
            System.out.println("Enter Phone:");
            String phone = sc.nextLine();
            System.out.println("Enter Password:");
            String password = sc.nextLine();

            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM Users WHERE email=?");
            checkStmt.setString(1, email);
            ResultSet rsCheck = checkStmt.executeQuery();
            if (rsCheck.next()) {
                System.out.println("❌ Email already registered. Try logging in.");
                return -1;
            }

            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Users (name,email,phone,password) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("✅ Registration Successful! Welcome " + name);
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int loginUser() {
        try (Connection con = DataBaseConfig.getConnection()) {
            System.out.println("Enter Email:");
            String email = sc.nextLine();
            System.out.println("Enter Password:");
            String password = sc.nextLine();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Users WHERE email=? AND password=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("✅ Login Successful! Welcome back, " + rs.getString("name"));
                return rs.getInt("user_id");
            } else {
                System.out.println("❌ Invalid email or password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void displayCities() {
        System.out.println("\nAvailable Cities:");
        System.out.println("1. Kolkata\n2. Ranchi\n3. Delhi");
    }

    public void displayMovies(String city) {
        try (Connection con = DataBaseConfig.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Movies WHERE city=?");
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nAvailable Movies in " + city + ":");
            while (rs.next()) {
                System.out.println(rs.getInt("movie_id") + ". " +
                        rs.getString("title") + " (" +
                        rs.getString("theater_name") + ", " +
                        rs.getString("show_time") + ", Available Seats: " +
                        rs.getInt("available_seats") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bookSeats(int userId, int movieId, String seats) {
        try (Connection con = DataBaseConfig.getConnection()) {
            con.setAutoCommit(false);

            boolean seatUnavailable = false;
            String[] seatList = seats.split(",");

            PreparedStatement checkStmt = con.prepareStatement(
                    "SELECT seat_number FROM Seats WHERE movie_id=? AND seat_number=? AND is_booked=TRUE"
            );

            for (String seat : seatList) {
                checkStmt.setInt(1, movieId);
                checkStmt.setString(2, seat.trim());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    seatUnavailable = true;
                    System.out.println("❌ Seat " + seat + " is already booked!");
                }
            }

            if (seatUnavailable) {
                System.out.println("❌ Booking Failed. One or more seats already booked.");
                return;
            }

            PreparedStatement bookStmt = con.prepareStatement(
                    "UPDATE Seats SET is_booked=TRUE WHERE movie_id=? AND seat_number=?"
            );
            for (String seat : seatList) {
                bookStmt.setInt(1, movieId);
                bookStmt.setString(2, seat.trim());
                bookStmt.executeUpdate();
            }

            PreparedStatement insertBooking = con.prepareStatement(
                    "INSERT INTO Bookings (user_id, movie_id, booked_seats) VALUES (?,?,?)"
            );
            insertBooking.setInt(1, userId);
            insertBooking.setInt(2, movieId);
            insertBooking.setString(3, seats);
            insertBooking.executeUpdate();

            // Update available seats
            PreparedStatement updateSeats = con.prepareStatement(
                    "UPDATE Movies SET available_seats = available_seats - ? WHERE movie_id=?"
            );
            updateSeats.setInt(1, seatList.length);
            updateSeats.setInt(2, movieId);
            updateSeats.executeUpdate();

            con.commit();
            System.out.println("✅ Booking Successful!");
            System.out.println("🎟️ Your Seats: " + seats);

            PreparedStatement remainingStmt = con.prepareStatement(
                    "SELECT available_seats FROM Movies WHERE movie_id=?"
            );
            remainingStmt.setInt(1, movieId);
            ResultSet rsRemaining = remainingStmt.executeQuery();
            if (rsRemaining.next()) {
                System.out.println("Now Available Seats: " + rsRemaining.getInt("available_seats"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAvailableSeats(int movieId) {
        try (Connection con = DataBaseConfig.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT available_seats FROM Movies WHERE movie_id=?"
            );
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("🎫 Available Seats Now: " + rs.getInt("available_seats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
