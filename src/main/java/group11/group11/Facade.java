package group11.group11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Facade {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cinemacenter";
    private static final String DB_USER = "root"; // Replace with your username
    private static final String DB_PASSWORD = "addnone2013"; // Replace with your password

    private Connection connect() throws Exception {
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

    public List<Cart.Product> getProductsFromDb(String orderNo) {
        List<Cart.Product> products = new ArrayList<>();
        String query = "SELECT name_ofProduct, price_ofProduct, quantity_ofProduct " +
                "FROM `order` WHERE order_no = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Extract values from the ResultSet
                String productName = rs.getString("name_ofProduct");
                double productPrice = rs.getDouble("price_ofProduct");
                int productQuantity = rs.getInt("quantity_ofProduct");

                // Create a new Cart.Product object and add it to the list
                Cart.Product product = new Cart.Product(productName, productPrice, productQuantity);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products; // Return the list of products
    }

    public void updateProductQuantity(String orderNo, String productName, int newQuantity) {
        String query = "UPDATE `order` SET quantity_ofProduct = ? WHERE order_no = ? AND name_ofProduct = ?";

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

}