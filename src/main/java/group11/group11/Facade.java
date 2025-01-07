package group11.group11;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Facade {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cinemacenter";
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = "blodreina"; // Replace with your password

    private static Connection connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to validate user login
    public Users validateLogin(String username, String password) {
        String query = "SELECT id, username, password, role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extract user details
                String fetchedUsername = rs.getString("username");
                String fetchedPassword = rs.getString("password");
                String fetchedRole = rs.getString("role");
                int fetcheduserId = rs.getInt("id");

                if(Objects.equals(username, fetchedUsername) && Objects.equals(password, fetchedPassword))
                {
                    Users users = new Users();
                    users.setuserId(fetcheduserId);
                    users.setUsername(fetchedUsername);
                    users.setPassword(fetchedPassword);
                    users.setrole(fetchedRole);

                    return users;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Movie searchMovieByFullName(String fullName) {
        Movie movie = new Movie();
        String query = "SELECT moviesSummary, moviesGenre, moviesImage, moviesDuration, movieReleaseYear FROM movies WHERE moviesName = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extract values from the ResultSet
                String movieSummary = rs.getString("moviesSummary");
                String movieGenre = rs.getString("moviesGenre");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");


                return new Movie(fullName, movieSummary, movieGenre, movieImage, movieDuration, movieReleaseYear);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie; // Return null if no movie is found
    }

    public List<Movie> searchMovieByGenre(String genre) {
        String query = "SELECT moviesName, moviesSummary, moviesImage, moviesDuration, movieReleaseYear FROM movies WHERE moviesGenre = ?";
        List<Movie> movieList = new ArrayList<>();

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Extract values from the ResultSet
                String movieName = rs.getString("moviesName");
                String movieSummary = rs.getString("moviesSummary");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");

                // Create and add the movie object to the list
                Movie movie = new Movie(movieName, movieSummary, genre, movieImage, movieDuration, movieReleaseYear);
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList; // Return the list of movies (can be empty if no results)
    }


    public List<Movie> searchMovieByPartialName(String partialName) {
        String query = "SELECT moviesName, moviesSummary, moviesGenre, moviesImage, moviesDuration, movieReleaseYear " +
                "FROM movies WHERE moviesName LIKE ?";
        List<Movie> movieList = new ArrayList<>();

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + partialName + "%");  // Match any part of the movie name
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Extract values from the ResultSet
                String movieName = rs.getString("moviesName");
                String movieSummary = rs.getString("moviesSummary");
                String movieGenre = rs.getString("moviesGenre");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");

                // Create and add the movie object to the list
                Movie movie = new Movie(movieName, movieSummary, movieGenre, movieImage, movieDuration, movieReleaseYear);
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList; // Return the list of movies (can be empty if no results)
    }

    public static int getMovieIdByName(String movieName) {
        int movieId = -1; // Default value if the movie is not found
        String query = "SELECT movie_id FROM movies WHERE moviesName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, movieName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movieId = rs.getInt("movie_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieId;
    }

    public static List<Date> getSessionDays(int movieId)
    {
        List<Date> sessionDays = new ArrayList<>();
        String query = "SELECT DISTINCT day FROM sessions WHERE movie_id = ? ORDER BY day";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, movieId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Date sessionDate = rs.getDate("day");
                    sessionDays.add(sessionDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionDays;
    }

    public static List<Time> getSessionTimes(Date selectedDay)
    {
        List<Time> sessionTimes = new ArrayList<>();
        String query = "SELECT time FROM sessions WHERE day = ? ORDER BY time";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, new java.sql.Date(selectedDay.getTime()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Time sessionTime = rs.getTime("time");
                    sessionTimes.add(sessionTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionTimes;
    }

    public static List<String> getSessionHalls(Time selectedTime)
    {
        List<String> sessionHalls = new ArrayList<>();
        String query = "SELECT DISTINCT hall FROM sessions WHERE time = ? ORDER BY hall";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setTime(1, new java.sql.Time(selectedTime.getTime()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String hallName = rs.getString("hall");
                    sessionHalls.add(hallName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionHalls;
    }
}