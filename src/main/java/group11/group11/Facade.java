package group11.group11;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;
import java.sql.PreparedStatement;

public class Facade {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cinemacenter";
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = "addnone2013"; // Replace with your password

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
        String query = "SELECT movie_id, moviesSummary, moviesGenre, moviesImage, moviesDuration, movieReleaseYear FROM movies WHERE moviesName = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extract values from the ResultSet
                int movie_id = rs.getInt("movie_id");
                String movieSummary = rs.getString("moviesSummary");
                String movieGenre = rs.getString("moviesGenre");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");

                return new Movie(movie_id, fullName, movieSummary, movieGenre, movieImage, movieDuration, movieReleaseYear);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie; // Return null if no movie is found
    }
    public List<Movie> initializeMovieTable() {
        String query = "SELECT * FROM movies";
        List<Movie> movieList = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Extract values from the ResultSet
                int movie_id = rs.getInt("movie_id");
                String movieName = rs.getString("moviesName");
                String movieSummary = rs.getString("moviesSummary");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");
                String genre = rs.getString("moviesGenre"); // Assuming genre is also a column in your table

                // Create and add the movie object to the list
                Movie movie = new Movie(movie_id, movieName, movieSummary, genre, movieImage, movieDuration, movieReleaseYear);
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return movieList;
    }
    public List<Movie> searchMovieByGenre(String genre) {
        String query = "SELECT movie_id, moviesName, moviesSummary, moviesImage, moviesDuration, movieReleaseYear FROM movies WHERE moviesGenre = ?";
        List<Movie> movieList = new ArrayList<>();

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Extract values from the ResultSet
                int movie_id = rs.getInt("movie_id");
                String movieName = rs.getString("moviesName");
                String movieSummary = rs.getString("moviesSummary");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");

                // Create and add the movie object to the list
                Movie movie = new Movie(movie_id, movieName, movieSummary, genre, movieImage, movieDuration, movieReleaseYear);
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList; // Return the list of movies (can be empty if no results)
    }

    public List<Movie> searchMovieByPartialName(String partialName) {
        String query = "SELECT movie_id, moviesName, moviesSummary, moviesGenre, moviesImage, moviesDuration, movieReleaseYear " +
                "FROM movies WHERE moviesName LIKE ?";
        List<Movie> movieList = new ArrayList<>();

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + partialName + "%");  // Match any part of the movie name
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Extract values from the ResultSet
                int movie_id = rs.getInt("movie_id");
                String movieName = rs.getString("moviesName");
                String movieSummary = rs.getString("moviesSummary");
                String movieGenre = rs.getString("moviesGenre");
                String movieImage = rs.getString("moviesImage");
                String movieDuration = rs.getString("moviesDuration");
                String movieReleaseYear = rs.getString("movieReleaseYear");

                // Create and add the movie object to the list
                Movie movie = new Movie(movie_id, movieName, movieSummary, movieGenre, movieImage, movieDuration, movieReleaseYear);
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList; // Return the list of movies (can be empty if no results)
    }

    public static List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, name, price, stock FROM products";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                double taxedPrice = rs.getDouble("taxed_price");
                int stock = rs.getInt("stock");

                // Create a Product object and add it to the list
                Product product = new Product(productId, name, price, stock);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getProductsFromDb(String orderNo) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT item_type, price_per_item, quantity FROM cart WHERE item_id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Extract values from the ResultSet
                String productName = rs.getString("item_type");
                double productPrice = rs.getDouble("price_per_item");
                int productQuantity = rs.getInt("quantity");

                // Create a new Cart.Product object and add it to the list
                Product product = new Product(productName, productPrice, productQuantity);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products; // Return the list of products
    }

    public static void addProductToCart(String orderNo, String productName, double productPrice, int quantity) {
        String query = "INSERT INTO cart (item_id, item_type, price_per_item, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderNo);
            stmt.setString(2, productName);
            stmt.setDouble(3, productPrice);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding product to the cart in the database.");
        }
    }

    public void saveCustomerInfo(String name, String surname, LocalDate birthdate) {
        String sql = "INSERT INTO customers (name, surname, birthdate) VALUES (?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setDate(3, Date.valueOf(birthdate));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer information saved to database!");
            } else {
                System.out.println("Failed to save customer information.");
            }
        } catch (Exception e) {
            System.err.println("Error saving customer information: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateProductQuantity(String orderNo, String productName, int newQuantity) {
        String query = "UPDATE cart SET quantity = ? WHERE item_id = ? AND item_type = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newQuantity);
            stmt.setString(2, orderNo);
            stmt.setString(3, productName);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating product quantity in the database.");
        }
    }

    public static int productExistsInCart(String orderNo, String productName) {
        String query = "SELECT quantity FROM cart WHERE item_id = ? AND item_type = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderNo);
            stmt.setString(2, productName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count; // Returns true if the product exists in the cart
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    public static List<Date> getSessionDays(int movieId) {
        List<Date> sessionDays = new ArrayList<>();
        String query = "SELECT DISTINCT day FROM Sessions WHERE movie_id = ? ORDER BY day";

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

    public static List<Time> getSessionTimes(Date selectedDay) {
        List<Time> sessionTimes = new ArrayList<>();
        String query = "SELECT session_id, time FROM Sessions WHERE day = ? ORDER BY time";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, new Date(selectedDay.getTime()));
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

    public static List<String> getUnavailableSeats(int sessionId) {
        List<String> unavailableSeats = new ArrayList<>();
        String query = "SELECT seat_number FROM Seats WHERE session_id = ? AND is_available = 0";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, sessionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String seatNumber = rs.getString("seat_number");
                    unavailableSeats.add(seatNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unavailableSeats;
    }

    public static List<String> getSessionHalls(Time selectedTime) {
        List<String> sessionHalls = new ArrayList<>();
        String query = "SELECT DISTINCT hall FROM Sessions WHERE time = ? ORDER BY hall";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setTime(1, new Time(selectedTime.getTime()));

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

    public static int getSessionId(Time selectedTime, int movieId) {
        int session_id = 0;
        String query = "SELECT session_id FROM Sessions WHERE time = ? AND movie_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setTime(1, new Time(selectedTime.getTime()));
            pstmt.setInt(2, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    session_id = rs.getInt("session_id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return session_id;
    }
    public static void clearCart() {
        String query = "DELETE FROM cart"; // Delete all rows from the cart table

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate(); // Execute the delete query
            System.out.println("Cart table cleared successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error clearing the cart table.");
        }
    }

    public static int checkStock(String productName)
    {
        String query = "SELECT stock FROM products WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void updateStock(String productName, int newStock)
    {
        String query = "UPDATE products SET stock = ? WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newStock);
            pstmt.setString(2, productName);
            int rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getTaxedPrice(String productName)
    {
        double a =0;
        return a;
    }


    public static boolean isEnoughSeat(int numOfTickets, int session_id) {
        String query = "SELECT COUNT(*) AS available_seats FROM Seats WHERE is_available = 1 AND session_id = ?";
        int availableSeats = 0;

        System.out.println("Checking seats for session_id: " + session_id); // Debug statement

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the session_id parameter
            pstmt.setInt(1, session_id);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the count of available seats
                    availableSeats = rs.getInt("available_seats");
                    System.out.println("Available seats: " + availableSeats); // Debug statement
                } else {
                    System.out.println("No result returned from the query."); // Debug statement
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }

        // Check if there are enough seats
        System.out.println("Enough seats: " + (availableSeats >= numOfTickets)); // Debug statement
        return availableSeats >= numOfTickets;
    }
}