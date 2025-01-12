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

    public Schedule(int sessionId, int movieId, String hall, String day, String time) {
        this.sessionId = new SimpleIntegerProperty(sessionId);
        this.movieId = new SimpleIntegerProperty(movieId);
        this.hall = new SimpleStringProperty(hall);
        this.day = new SimpleStringProperty(day);
        this.time = new SimpleStringProperty(time);
    }

    // Getters and Setters for Properties
    public IntegerProperty sessionIdProperty() {
        return sessionId;
    }

    public IntegerProperty movieIdProperty() {
        return movieId;
    }

    public StringProperty hallProperty() {
        return hall;
    }

    public StringProperty dayProperty() {
        return day;
    }

    public StringProperty timeProperty() {
        return time;
    }

    // Regular Getters and Setters
    public int getSessionId() {
        return sessionId.get();
    }

    public void setSessionId(int sessionId) {
        this.sessionId.set(sessionId);
    }

    public int getMovieId() {
        return movieId.get();
    }

    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    public String getHall() {
        return hall.get();
    }

    public void setHall(String hall) {
        this.hall.set(hall);
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

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