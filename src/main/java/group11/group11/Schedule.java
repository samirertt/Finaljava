/**
 * The Schedule class represents a movie schedule entity with details such as session ID, movie ID, hall, day, and time.
 * It uses JavaFX properties to allow binding with UI components.
 */
package group11.group11;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Schedule {
    private final IntegerProperty sessionId;
    private final IntegerProperty movieId;
    private final StringProperty hall;
    private final StringProperty day;
    private final StringProperty time;

    /**
     * Constructs a Schedule object with the specified session ID, movie ID, hall, day, and time.
     *
     * @param sessionId The unique ID of the session.
     * @param movieId   The ID of the movie associated with the session.
     * @param hall      The hall where the session will take place.
     * @param day       The day of the session.
     * @param time      The time of the session.
     */
    public Schedule(int sessionId, int movieId, String hall, String day, String time) {
        this.sessionId = new SimpleIntegerProperty(sessionId);
        this.movieId = new SimpleIntegerProperty(movieId);
        this.hall = new SimpleStringProperty(hall);
        this.day = new SimpleStringProperty(day);
        this.time = new SimpleStringProperty(time);
    }

    /**
     * Returns the session ID property for JavaFX binding.
     *
     * @return The session ID property.
     */
    public IntegerProperty sessionIdProperty() {
        return sessionId;
    }

     /**
     * Returns the movie ID property for JavaFX binding.
     *
     * @return The movie ID property.
     */
    public IntegerProperty movieIdProperty() {
        return movieId;
    }

    /**
     * Returns the hall property for JavaFX binding.
     *
     * @return The hall property.
     */
    public StringProperty hallProperty() {
        return hall;
    }

    /**
     * Returns the day property for JavaFX binding.
     *
     * @return The day property.
     */
    public StringProperty dayProperty() {
        return day;
    }

    /**
     * Returns the time property for JavaFX binding.
     *
     * @return The time property.
     */
    public StringProperty timeProperty() {
        return time;
    }

    /**
     * Returns the session ID.
     *
     * @return The session ID.
     */
    public int getSessionId() {
        return sessionId.get();
    }

    /**
     * Sets the session ID.
     *
     * @param sessionId The session ID to set.
     */
    public void setSessionId(int sessionId) {
        this.sessionId.set(sessionId);
    }

    /**
     * Returns the movie ID.
     *
     * @return The movie ID.
     */
    public int getMovieId() {
        return movieId.get();
    }

    /**
     * Sets the movie ID.
     *
     * @param movieId The movie ID to set.
     */
    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    /**
     * Returns the hall where the session will take place.
     *
     * @return The hall.
     */
    public String getHall() {
        return hall.get();
    }

    /**
     * Sets the hall where the session will take place.
     *
     * @param hall The hall to set.
     */
    public void setHall(String hall) {
        this.hall.set(hall);
    }

    /**
     * Returns the day of the session.
     *
     * @return The day.
     */
    public String getDay() {
        return day.get();
    }

    /**
     * Sets the day of the session.
     *
     * @param day The day to set.
     */
    public void setDay(String day) {
        this.day.set(day);
    }

    /**
     * Returns the time of the session.
     *
     * @return The time.
     */
    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "sessionId=" + sessionId.get() +
                ", movieId=" + movieId.get() +
                ", hall=" + hall.get() +
                ", day=" + day.get() +
                ", time=" + time.get() +
                '}';
    }
}
