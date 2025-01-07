package group11.group11;

public class Movie {

    private String movieName;
    private String movieSummary;
    private String movieGenre;
    private String movieImage;
    private String movieDuration;
    private String movieReleaseYear;


    // Constructor to initialize the movie object
    public Movie(String movieName, String movieSummary, String movieGenre,
                 String movieImage, String movieDuration, String movieReleaseYear) {
        this.movieName = movieName;
        this.movieSummary = movieSummary;
        this.movieGenre = movieGenre;
        this.movieImage = movieImage;
        this.movieDuration = movieDuration;
        this.movieReleaseYear = movieReleaseYear;
    }

    public Movie() {

    }

    // Getters and Setters
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieSummary() {
        return movieSummary;
    }

    public void setMovieSummary(String movieSummary) {
        this.movieSummary = movieSummary;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }

    public String getMovieReleaseYear() {
        return movieReleaseYear;
    }

    public void setMovieReleaseYear(String movieReleaseYear) {
        this.movieReleaseYear = movieReleaseYear;
    }

}
