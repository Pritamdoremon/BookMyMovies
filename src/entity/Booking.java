package entity;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private int movieId;
    private String bookedSeats;
    private Timestamp bookingTime;

    public Booking(int bookingId, int userId, int movieId, String bookedSeats, Timestamp bookingTime) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.bookedSeats = bookedSeats;
        this.bookingTime = bookingTime;
    }

    // getters and setters

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(String bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }
}
