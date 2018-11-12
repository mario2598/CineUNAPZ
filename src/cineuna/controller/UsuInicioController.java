/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.UserMovieCard;
import cineuna.model.MovieDto;
import cineuna.model.UsuarioDto;
import cineuna.service.MovieService;
import cineuna.util.AppContext;
import cineuna.util.LangUtils;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuInicioController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private Tab tabCartelera;
    @FXML
    private TilePane tpCartelera;
    @FXML
    private Tab tabProximamente;
    @FXML
    private TilePane tpProximamente;
    @FXML
    private JFXTabPane tabPaneMovies;
    
    //Attributes
    private UsuarioDto usuario;
    private final MovieService movieService = new MovieService();
    private final ArrayList<UserMovieCard> CarteleraCardList = new ArrayList<>();
    private final ArrayList<UserMovieCard> ProximasCardList = new ArrayList<>();
    private final ArrayList<UserMovieCard> InactivasCardList = new ArrayList<>();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabCartelera.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue)
                deselectAllCards(CarteleraCardList);
        });
        tabProximamente.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue)
                deselectAllCards(ProximasCardList);
        });
        tabPaneMovies.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                deselectAllCards(CarteleraCardList);
                deselectAllCards(ProximasCardList);
            }
            event.consume();
        });  
    } 

    /**
     * Metodo abstracto de la clase Controller
     */
    @Override
    public void initialize() {
        tpCartelera.getChildren().clear();
        tpProximamente.getChildren().clear();
        cargarPeliculasEnCartelera();
        cargarProximasPeliculas();
        tabPaneMovies.getSelectionModel().selectFirst();
    }
    
    //Methods
    private void cargarPeliculasEnCartelera(){
        tpCartelera.getChildren().clear();
        CarteleraCardList.clear();
        Respuesta resp = movieService.getMovies("C");
        ArrayList<MovieDto> movieList = new ArrayList<>((List<MovieDto>) resp.getResultado("Movies"));
        movieList.stream().forEach(movie -> {
            UserMovieCard newCard = new UserMovieCard(movie);
            newCard.initCard();
            CarteleraCardList.add(newCard);
            tpCartelera.getChildren().add(newCard);
        });
    }
    
    private void cargarProximasPeliculas(){
        tpProximamente.getChildren().clear();
        ProximasCardList.clear();
        Respuesta resp = movieService.getMovies("P");
        ArrayList<MovieDto> movieList = new ArrayList<>((List<MovieDto>) resp.getResultado("Movies"));
        movieList.stream().forEach(movie -> {
            UserMovieCard newCard = new UserMovieCard(movie);
            newCard.initCard();
            ProximasCardList.add(newCard);
            tpProximamente.getChildren().add(newCard);
        });
    }
    
    private void cargarIdioma(){
        usuario=AppContext.getInstance().getUsuario();
        Integer idioma=Integer.valueOf(usuario.usuIdioma.getValue());
        if(idioma.equals(1)){
            LangUtils.getInstance().setLang("es");
        }
        else{
            LangUtils.getInstance().setLang("eng");
        }
    }
    
    private void deselectAllCards(ArrayList<UserMovieCard> list){
        list.stream().forEach(card -> card.selectedStatus(false));
    }
    
}
