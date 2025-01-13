/**
 * The Movie class represents a movie entity with details such as ID, name, summary, genre, image, duration, and release year.
 * It provides constructors to initialize movie objects and getter/setter methods to access and modify movie attributes.
 */
package group11.group11;

public class Movie {
    private int movie_id;
    private String movieName;
    private String movieSummary;
    private String movieGenre;
    private String movieImage;
    private String movieDuration;
    private String movieReleaseYear;

    /**
     * Constructs a Movie object with basic details.
     *
     * @param movieId      The unique ID of the movie.
     * @param moviesName   The name of the movie.
     * @param moviesGenre  The genre of the movie.
     * @param moviesSummary A brief summary of the movie.
     * @param moviesImage  The image URL or path associated with the movie.
     */
    public Movie(int movieId, String moviesName, String moviesGenre, String moviesSummary, String moviesImage) {
        this.movie_id = movieId;
        this.movieName = moviesName;
        this.movieGenre = moviesGenre;
        this.movieSummary = moviesSummary;
        this.movieImage = moviesImage;
    }

   /**
     * Constructs a Movie object with all details.
     *
     * @param movie_id         The unique ID of the movie.
     * @param movieName        The name of the movie.
     * @param movieSummary     A brief summary of the movie.
     * @param movieGenre       The genre of the movie.
     * @param movieImage       The image URL or path associated with the movie.
     * @param movieDuration    The duration of the movie.
     * @param movieReleaseYear The release year of the movie.
     */
    public Movie(int movie_id, String movieName, String movieSummary, String movieGenre,
                 String movieImage, String movieDuration, String movieReleaseYear) {
        this.movie_id = movie_id;
        this.movieName = movieName;
        this.movieSummary = movieSummary;
        this.movieGenre = movieGenre;
        this.movieImage = movieImage;
        this.movieDuration = movieDuration;
        this.movieReleaseYear = movieReleaseYear;
    }

    public Movie() {

    }



    


    /**
     * Default constructor for the Movie class.
     */
    public int getMovie_id() {
        return movie_id;
    }
    /**
     * Sets the unique ID of the movie.
     *
     * @param movie_id The movie ID to set.
     */
    public void setMovie_id(int movie_id)
    {
        this.movie_id = movie_id;
    }

    /**
     * Returns the name of the movie.
     *
     * @return The movie name.
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * Sets the name of the movie.
     *
     * @param movieName The movie name to set.
     */
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    /**
     * Returns the summary of the movie.
     *
     * @return The movie summary.
     */
    public String getMovieSummary() {
        return movieSummary;
    }

    /**
     * Sets the summary of the movie.
     *
     * @param movieSummary The movie summary to set.
     */
    public void setMovieSummary(String movieSummary) {
        this.movieSummary = movieSummary;
    }

    /**
     * Returns the genre of the movie.
     *
     * @return The movie genre.
     */
    public String getMovieGenre() {
        return movieGenre;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param movieGenre The movie genre to set.
     */
    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    /**
     * Returns the image URL or path associated with the movie.
     *
     * @return The movie image.
     */
    public String getMovieImage() {
        return movieImage;
    }

    /**
     * Sets the image URL or path associated with the movie.
     *
     * @param movieImage The movie image to set.
     */
    public void setMovieImage() {
        this.movieImage = movieImage;
    }

    /**
     * Returns the duration of the movie.
     *
     * @return The movie duration.
     */
    public String getMovieDuration() {
        return movieDuration;
    }

    /**
     * Sets the duration of the movie.
     *
     * @param movieDuration The movie duration to set.
     */
    public void setMovieDuration(String movieDuration) {
        this.movieDuration = movieDuration;
    }

    /**
     * Returns the release year of the movie.
     *
     * @return The movie release year.
     */
    public String getMovieReleaseYear() {
        return movieReleaseYear;
    }

    /**
     * Sets the release year of the movie.
     *
     * @param movieReleaseYear The movie release year to set.
     */
    public void setMovieReleaseYear(String movieReleaseYear) {
        this.movieReleaseYear = movieReleaseYear;
    }

}
