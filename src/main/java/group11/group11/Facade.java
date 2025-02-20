package group11.group11;

import javafx.collections.ObservableList;

import java.sql.*;

import java.sql.Date;
import java.util.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.sql.PreparedStatement;

public class Facade {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cinema";
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

    public void addEmployee(String name,String surname, String username, String password, String role )
    {
        String query = "INSERT INTO users (name,surname, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, role);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteEmployee(int id)
    {
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Employee> loadEmployees()
    {
        String query = "SELECT * FROM users";
        List<Employee> Employees = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                Employees.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Employees;
    }


    public void UpdateEmployee(String name,String surname, String username, String password, String role , int id)
    {
        String query = "UPDATE users SET name = ?,surname = ?, username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, surname);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.setInt(6, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Discount> load_Discounts()
    {
        List<Discount> Discounts = new ArrayList<>();
        String query = "SELECT * FROM discounts";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Discount discount = new Discount(
                        rs.getInt("min_age"),
                        rs.getInt("max_age"),
                        rs.getDouble("discount_rate")
                );
                Discounts.add(discount);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Discounts;
    }

    public void addDiscounts(int minAge, int maxAge, double discountRate)
    {
        String query = "INSERT INTO discounts (min_age, max_age, discount_rate) VALUES (?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, minAge);
            ps.setInt(2, maxAge);
            ps.setDouble(3, discountRate);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAgeRangeOverlap(int minAge, int maxAge) {
        String query = "SELECT COUNT(*) FROM discounts WHERE (min_age <= ? AND max_age >= ?) OR (min_age <= ? AND max_age >= ?)";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, maxAge);
            ps.setInt(2, minAge);
            ps.setInt(3, maxAge);
            ps.setInt(4, minAge);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Eğer COUNT(*) > 0 ise, çakışma var
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete_Discount(Discount selectedDiscount)
    {
        String query = "DELETE FROM discounts WHERE min_age = ? AND max_age = ?";

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, selectedDiscount.getMinAge());
            ps.setInt(2, selectedDiscount.getMaxAge());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int update_Discount(Discount selectedDiscount, int minAge, int maxAge, double discountRate)
    {
        String query = "UPDATE discounts SET min_age = ?, max_age = ?, discount_rate = ? WHERE min_age = ? AND max_age = ?";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, minAge);
            ps.setInt(2, maxAge);
            ps.setDouble(3, discountRate);
            ps.setInt(4, selectedDiscount.getMinAge());
            ps.setInt(5, selectedDiscount.getMaxAge());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isAgeRangeOverlapForUpdate(int minAge, int maxAge, Discount selectedDiscount) {
        String query = "SELECT COUNT(*) FROM discounts WHERE ((min_age <= ? AND max_age >= ?) OR (min_age <= ? AND max_age >= ?)) AND NOT (min_age = ? AND max_age = ?)";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, maxAge);
            ps.setInt(2, minAge);
            ps.setInt(3, maxAge);
            ps.setInt(4, minAge);
            ps.setInt(5, selectedDiscount.getMinAge());
            ps.setInt(6, selectedDiscount.getMaxAge());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Eğer COUNT(*) > 0 ise, çakışma var
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public static boolean cancelAndRefundOrder(int orderNo) {
        try (Connection conn = connect()) {
            connect().setAutoCommit(false);

            String deleteTicketsQuery = "DELETE FROM Tickets WHERE order_no = ?";
            try (PreparedStatement deleteTicketsPs = conn.prepareStatement(deleteTicketsQuery)) {
                deleteTicketsPs.setInt(1, orderNo);
                deleteTicketsPs.executeUpdate();
            }

            List<orderItem> orderItems = fetchOrderItemsByOrderId(orderNo);

            String updateProductStockQuery = "UPDATE Products SET stock = stock + ? WHERE product_id = ?";
            for (orderItem item : orderItems) {
                if ("product".equalsIgnoreCase(item.getItemType())) {
                    try (PreparedStatement updateProductStockPs = connect().prepareStatement(updateProductStockQuery)) {
                        updateProductStockPs.setInt(1, item.getQuantity());
                        updateProductStockPs.setInt(2, item.getItemId());
                        updateProductStockPs.executeUpdate();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            String deleteOrderItemsQuery = "DELETE FROM OrderItems WHERE order_no = ?";
            try (PreparedStatement deleteOrderItemsPs = connect().prepareStatement(deleteOrderItemsQuery)) {
                deleteOrderItemsPs.setInt(1, orderNo);
                deleteOrderItemsPs.executeUpdate();
            }

            String deleteOrderQuery = "DELETE FROM Orders WHERE order_no = ?";
            try (PreparedStatement deleteOrderPs = connect().prepareStatement(deleteOrderQuery)) {
                deleteOrderPs.setInt(1, orderNo);
                deleteOrderPs.executeUpdate();
            }

            connect().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT product_id, name, stock, price, taxed_price FROM products";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");
                double taxedPrice = rs.getDouble("taxed_price"); // Fetch taxed price

                // Create a Product object with taxed price
                Product product = new Product(productId, name, price, taxedPrice, stock);
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getItemsFromDb(String orderNo) {
        List<Product> items = new ArrayList<>();
        String query = "SELECT name, price_per_item, quantity FROM cart WHERE item_id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                double taxedPrice = rs.getDouble("price_per_item");
                int quantity = rs.getInt("quantity");

                // Create a Product object
                Product item = new Product(name, taxedPrice, quantity);
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }


    public static Movie fetchMovieById(int movieId) {
        String query = "SELECT * FROM movies WHERE movie_id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Movie(
                            rs.getInt("movie_id"),
                            rs.getString("moviesName"),
                            rs.getString("moviesGenre"),
                            rs.getString("moviesSummary"),
                            rs.getString("moviesImage")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<orderItem> fetchOrderItemsByOrderId(int orderNo) {
        List<orderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM OrderItems WHERE order_no = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, orderNo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderItemId = rs.getInt("orderitem_id");
                    String itemType = rs.getString("item_type");
                    int itemId = rs.getInt("orderitem_id");
                    int quantity = rs.getInt("quantity");
                    double pricePerItem = rs.getDouble("price_per_item");

                    orderItems.add(new orderItem(orderItemId, orderNo, itemType, itemId, quantity, pricePerItem));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItems;
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

    public static List<Time> getSessionTimes(Date selectedDay, int movieId) {
        List<Time> sessionTimes = new ArrayList<>();
        String query = "SELECT session_id, time FROM sessions WHERE day = ? AND movie_id = ? ORDER BY time";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDate(1, new java.sql.Date(selectedDay.getTime()));
            pstmt.setInt(2, movieId); // Filter by movie_id

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
        String query = "SELECT seat_number FROM tickets WHERE session_id = ?";

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

    public static List<String> getSessionHalls(Time selectedTime, int movieId) {
        List<String> sessionHalls = new ArrayList<>();
        String query = "SELECT DISTINCT hall FROM Sessions WHERE time = ? AND movie_id = ? ORDER BY hall";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setTime(1, new Time(selectedTime.getTime())); // Set the time
            pstmt.setInt(2, movieId); // Set the movie_id

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String hallName = rs.getString("hall");
                    sessionHalls.add(hallName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logging in production
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
    public void updateStock(String productName, int newStock)
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

    public void updateProductPrice(String productName, double price)
    {
        String query = "UPDATE products SET price = ? WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, price);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getTaxedPrice(String productName) {
        String query = "SELECT taxed_price FROM products WHERE name = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("taxed_price");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0; // Return 0.0 if no taxed price is found or an error occurs
    }


    public static double getTicketPriceFromDB() {
        String query = "SELECT price FROM products WHERE name = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "Ticket");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static double getDiscountRateByAge(int age) {
        String query = "SELECT discount_rate FROM discounts WHERE ? BETWEEN min_age AND max_age";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, age);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("discount_rate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0; // Return 0 if no discount is found
    }


    public static void addOrderItems(String orderNo, Product product) {
        String query = "INSERT INTO orderitems (order_no, item_type, item_name, quantity, price_per_item) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = connect(); //
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderNo);
            stmt.setString(2, "product");
            stmt.setString(3, product.getName());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getTaxedPrice());
            stmt.addBatch();


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding product to the database: " + e.getMessage());
        }
    }

    public static void addTicketItems(String orderNo, Ticket ticket) {
        String query = "INSERT INTO orderitems (order_no, item_type, item_name, quantity, price_per_item) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = connect(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderNo);
            stmt.setString(2, "ticket");
            stmt.setString(3, ticket.getMovieName());
            stmt.setInt(4, 1);
            stmt.setDouble(5, ticket.getTicketPrice());
            stmt.addBatch();


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Product added successfully!");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding product to the database: " + e.getMessage());
        }
    }


    public static void addTickets(String orderNo, Ticket ticket) {
        // SQL query to insert a ticket into the tickets table
        String query = "INSERT INTO tickets (session_id, hall, seat_number, name, surname, age, order_no) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Try-with-resources to ensure the Connection and PreparedStatement are closed automatically
        try (Connection conn = connect(); // Assuming connect() is a method that returns a database connection
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set values for the PreparedStatement using the Ticket object
            stmt.setInt(1, ticket.getSessionId());       // session_id
            stmt.setString(2, ticket.getHall());         // hall
            stmt.setInt(3, ticket.getSeat());            // seat_number
            stmt.setString(4, ticket.getName());         // name
            stmt.setString(5, ticket.getSurname());      // surname
            stmt.setInt(6, ticket.getAge());             // age
            stmt.setString(7, orderNo);                  // order_no


            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ticket added successfully!");
            } else {
                System.out.println("Failed to add ticket.");
            }
        } catch (Exception e) {

            e.printStackTrace();
            System.err.println("Error adding ticket to the database: " + e.getMessage());
        }
    }

    public static void buySeats(List<Ticket> tickets) {
        String query = "UPDATE seats SET is_available = 0 WHERE session_id = ? AND hall = ? AND seat_number = ?";

        try (Connection conn = connect()) {
            conn.setAutoCommit(false); // Disable auto-commit for transaction management

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                for (Ticket ticket : tickets) {
                    stmt.setInt(1, ticket.getSessionId()); // Set session_id
                    stmt.setString(2, ticket.getHall()); // Set hall
                    stmt.setInt(3, ticket.getSeat()); // Set seat_number
                    stmt.addBatch(); // Add the update to the batch
                }

                stmt.executeBatch(); // Execute the batch
                conn.commit(); // Commit the transaction
                System.out.println("Seats updated successfully.");
            } catch (SQLException e) {
                conn.rollback(); // Rollback in case of error
                e.printStackTrace();
                System.err.println("Error updating seats.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error connecting to the database.");
        }
    }

    public static Map<String, Double> calculateTaxAmount()
    {
        double totalTax = 0;
        double revenue = 0;
        String query = "SELECT price_per_item, item_type, quantity FROM orderitems ";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                double pricePerItem = rs.getDouble("price_per_item");
                String itemType = rs.getString("item_type");
                double quantity = rs.getInt("quantity");

                double taxRate;
                if ("product".equalsIgnoreCase(itemType)) {
                    taxRate = 0.10; // 10% tax for products
                } else if ("ticket".equalsIgnoreCase(itemType)) {
                    taxRate = 0.20; // 20% tax for tickets
                } else {
                    throw new IllegalArgumentException("Invalid item type: " + itemType);
                }

                revenue += pricePerItem * quantity;
                // Calculate the original price (before tax)
                double originalPrice = pricePerItem / (1 + taxRate);

                // Calculate the tax amount
                double taxAmount = pricePerItem - originalPrice;

                // Add it to the total tax amount
                totalTax += taxAmount*quantity;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Double> result = new HashMap<>();
        result.put("totalTax", totalTax);
        result.put("revenue", revenue);
        return result;
    }

    public static void createOrder(String orderNo,double total_price) {
        String query = "INSERT INTO orders (order_no, order_date, total_price) VALUES (?, NOW(), ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderNo);
            stmt.setDouble(2, total_price ); // Assuming you have a currentUser object
            stmt.executeUpdate();
            System.out.println("Order created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating order.");
        }
    }

    public static boolean addMovie(String name, String genre, String summary, String posterFilePath) {
        String query = "INSERT INTO movies (moviesName, moviesGenre, moviesSummary, moviesImage) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, genre);
            ps.setString(3, summary);
            ps.setString(4, posterFilePath);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Movie> fetchAllMovies() {
        List<Movie> moviesList = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int movieId = rs.getInt("movie_id");
                String moviesName = rs.getString("moviesName");
                String moviesGenre = rs.getString("moviesGenre");
                String moviesSummary = rs.getString("moviesSummary");
                String moviesImage = rs.getString("moviesImage");

                moviesList.add(new Movie(movieId, moviesName, moviesGenre, moviesSummary, moviesImage));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moviesList;
    }

    public static Schedule fetchScheduleById(int sessionId) {
        String query = "SELECT * FROM Sessions WHERE session_id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Schedule(
                            rs.getInt("session_id"),
                            rs.getInt("movie_id"),
                            rs.getString("hall"),
                            rs.getString("day"),
                            rs.getString("time")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateSchedule(int sessionId, int movieId, String hall, String day, String time) {
        String query = "UPDATE Sessions SET movie_id = ?, hall = ?, day = ?, time = ? WHERE session_id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, movieId);
            ps.setString(2, hall);
            ps.setString(3, day);
            ps.setString(4, time);
            ps.setInt(5, sessionId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Schedule> fetchAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM Sessions";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int sessionId = rs.getInt("session_id");
                int movieId = rs.getInt("movie_id");
                String hall = rs.getString("hall");
                String day = rs.getString("day");
                String time = rs.getString("time");

                schedules.add(new Schedule(sessionId, movieId, hall, day, time));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return schedules;
    }

    public static List<Order> fetchAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery())
        {

            while (rs.next()) {
                int orderNo = rs.getInt("order_no");
                String orderDate = rs.getString("order_date");
                double totalPrice = rs.getDouble("total_price");

                orders.add(new Order(orderNo, orderDate, totalPrice));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static boolean updateMovie(int movieId, String name, String genre, String summary, String posterFilePath) {
        String query = "UPDATE movies SET moviesName = ?, moviesGenre = ?, moviesSummary = ?, moviesImage = ? WHERE movie_id = ?";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, genre);
            ps.setString(3, summary);
            ps.setString(4, posterFilePath);
            ps.setInt(5, movieId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addSchedule(int movieId, String hall, String day, String time) {
        String query = "INSERT INTO Sessions (movie_id, hall, day, time) VALUES (?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, movieId);
            ps.setString(2, hall);
            ps.setString(3, day);
            ps.setString(4, time);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkSchedule(int movieId, String hall, String day, String time) {
        String query = "SELECT COUNT(*) FROM schedule WHERE movie_id = ? AND hall = ? AND day = ? AND time = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            stmt.setString(2, hall);
            stmt.setString(3, day);
            stmt.setString(4, time);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1); // Get the count of matching rows
                return count > 0; // Return true if at least one row matches
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error checking the schedule in the database.");
        }

        return false;
    }

    public boolean usernameCheck(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username); // Set the username parameter
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1); // Get the count of rows with the given username
                return count > 0; // Return true if the username exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Return false if the username does not exist or an error occurs
    }

    public void updateProductTaxedPrice(String name,double newPrice)
    {
        double taxRate = 0.10;
        if ("ticket".equalsIgnoreCase(name))
            taxRate = 0.20;

        double taxedPrice = newPrice * (1 + taxRate);

        String query = "UPDATE products SET taxed_price = ? WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, taxedPrice);
            pstmt.setString(2, name);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("No product found with the given name.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveDocumentToDatabase(String orderNo, String type, String htmlContent) {
        String query = "INSERT INTO documents (order_no, type, html_content) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, orderNo);
            pstmt.setString(2, type);
            pstmt.setString(3, htmlContent);
            pstmt.executeUpdate();
            System.out.println("Document saved to database: " + type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void decrementStock(String name, int quantity)
    {
        int stock = checkStock(name);
        stock -= quantity;

        String query = "UPDATE products SET stock = ? WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, stock);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}