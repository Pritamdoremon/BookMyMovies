package entity;

public class Movie {
    private int movieId;
    private String title;
    private String theaterName;
    private String city;
    private String showTime;

    public Movie(int movieId, String title, String theaterName, String city, String showTime) {
        this.movieId = movieId;
        this.title = title;
        this.theaterName = theaterName;
        this.city = city;
        this.showTime = showTime;
    }

    // getters and setters

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
}
