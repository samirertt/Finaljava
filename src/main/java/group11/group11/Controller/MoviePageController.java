package group11.group11.Controller;

import group11.group11.Facade;
import group11.group11.Main;
import group11.group11.Movie;
import group11.group11.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;




public class MoviePageController implements Initializable {

    private final Facade facade = new Facade();
    public Button movieSearch_windowClose_btn;

    private Users currentUser;
    private ObservableList<Movie> movieList;

    private Main mainApp;

    @FXML
    private Label movieSearch_profileName;

    @FXML
    private Label movieSearch_profileRole;

    @FXML
    private RadioButton movieSearch_searchByFullName;

    @FXML
    private RadioButton movieSearch_searchByGenre;

    @FXML
    private RadioButton movieSearch_searchByPartialName;

    @FXML
    private TextField movieSearch_searchMovie;

    @FXML
    private TableView<Movie> movieSearchTableView;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieTitle;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieGenre;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieDuration;

    @FXML
    private TableColumn<Movie, String> movieSearch_tableViewMovieShowingDate;

    @FXML
    private ImageView movieSearch_Image;

    @FXML
    private Label movieSearch_Name;

    @FXML
    private Label movieSearch_Genre;

    @FXML
    private Button movieSearch_windowMinimize_btn;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    @FXML
    private void handleLogout() {
        if (mainApp != null) {
            mainApp.showLoginPage();
        }
    }

    @FXML
    private void handleWindowClose() {
        System.exit(0);
    }
    public void movieSearch_windowClose_btn() {
        System.exit(0);
    }
    public void MovieSearch_windowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void handleWindowMinimize() {
        Stage stage = (Stage) movieSearch_windowMinimize_btn.getScene().getWindow();
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    public void setProfileDetails() {
        if (currentUser != null) {
            movieSearch_profileName.setText(currentUser.getUsername());
            movieSearch_profileRole.setText(currentUser.getrole());
        }
    }

    private void searchMovie(String searchType) {
        String searchQuery = movieSearch_searchMovie.getText().trim();
        if (searchQuery.isEmpty()) {
            movieList.clear();
            return;
        }

        List<Movie> movies = switch (searchType) {
            case "fullName" -> List.of(facade.searchMovieByFullName(searchQuery));
            case "genre" -> facade.searchMovieByGenre(searchQuery);
            case "partialName" -> facade.searchMovieByPartialName(searchQuery);
            default -> null;
        };

        if (movies != null) {
            updateTableWithMovies(movies);
        } else {
            movieList.clear();
        }
    }

    private void updateTableWithMovies(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
    }

    public void displayMovieDetails(Movie movie) {
        movieSearch_Name.setText(movie.getMovieName());
        movieSearch_Genre.setText(movie.getMovieGenre());
        System.out.println("path:");
        System.out.println(movie.getMovieImage());
        if(movie.getMovieImage() != null && !movie.getMovieImage().isEmpty())
        {
            try
            {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(movie.getMovieImage())));
                movieSearch_Image.setImage(image);
                //currentPosterpath = movie.getposterpath(); why ?
            }catch (Exception e)
            {
                e.printStackTrace();
                movieSearch_Image.setImage(null);
            }
        }else
        {
            movieSearch_Image.setImage(null);
            //currentPosterpath = null;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup searchToggleGroup = new ToggleGroup();
        movieSearch_searchByFullName.setToggleGroup(searchToggleGroup);
        movieSearch_searchByGenre.setToggleGroup(searchToggleGroup);
        movieSearch_searchByPartialName.setToggleGroup(searchToggleGroup);

        movieSearch_tableViewMovieTitle.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        movieSearch_tableViewMovieGenre.setCellValueFactory(new PropertyValueFactory<>("movieGenre"));
        movieSearch_tableViewMovieDuration.setCellValueFactory(new PropertyValueFactory<>("movieDuration"));
        movieSearch_tableViewMovieShowingDate.setCellValueFactory(new PropertyValueFactory<>("movieReleaseYear"));

        movieList = FXCollections.observableArrayList();
        movieSearchTableView.setItems(movieList);

        searchToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == movieSearch_searchByFullName) {
                searchMovie("fullName");
            } else if (newValue == movieSearch_searchByGenre) {
                searchMovie("genre");
            } else if (newValue == movieSearch_searchByPartialName) {
                searchMovie("partialName");
            }
        });

        movieSearchTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            displayMovieDetails(newSelection);
        });

        setProfileDetails();
    }
}
