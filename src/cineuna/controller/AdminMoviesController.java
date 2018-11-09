/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminMovieCard;
import cineuna.model.MovieDto;
import cineuna.service.MovieService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminMoviesController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private JFXButton btn1, btn2, btn3;
    @FXML
    private JFXTabPane tabPaneMovies;
    @FXML
    private Tab tabEnCartelera, tabProximamente;
    @FXML
    private TilePane tilePaneEnCartelera, tilePaneProximanete;

    //Attributes
    private final MovieService movieService = new MovieService();
    private final ArrayList<AdminMovieCard> CarteleraCardList = new ArrayList<>();
    private final ArrayList<AdminMovieCard> ProximasCardList = new ArrayList<>();
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabEnCartelera.selectedProperty().addListener((observable, oldValue, newValue) -> {
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
        //tilePaneEnCartelera.getChildren().clear();
        //tilePaneProximanete.getChildren().clear();
        //cargarPeliculasEnCartelera();
        //cargarProximasPeliculas();
        cargarCartelera();
        cargarProximas();
        tilePaneEnCartelera.getChildren().clear();
        tilePaneProximanete.getChildren().clear();
        cargarPeliculasEnCartelera();
        cargarProximasPeliculas();
        tabPaneMovies.getSelectionModel().selectFirst();
    }
    
    //Methods
    private void cargarPeliculasEnCartelera(){
        for (int i = 0; i < 5; i++) {
            MovieDto movie = new MovieDto();
            movie.setMovieNombre("Frozen");
            movie.setMovieDate(LocalDate.now());
            movie.setMovieDuracion(new Long(120));
            movie.setMoviePortada("cineuna/resourses/images/VenomPoster.jpg");
            movie.setMovieEstado("A");
            MovieCard2 card = new MovieCard2(true, movie);
            card.initCard();
            tilePaneEnCartelera.getChildren().add(card);
        }
        tilePaneEnCartelera.getChildren().clear();
        CarteleraCardList.clear();
        Respuesta resp = movieService.getMovies("C");
        ArrayList<MovieDto> movieList = new ArrayList<>((List<MovieDto>) resp.getResultado("Movies"));
        movieList.stream().forEach(movie -> {
            AdminMovieCard newCard = new AdminMovieCard(movie);
            newCard.initCard();
            CarteleraCardList.add(newCard);
            tilePaneEnCartelera.getChildren().add(newCard);
        });
    }
    
    private void cargarProximasPeliculas(){
        tilePaneProximanete.getChildren().clear();
        ProximasCardList.clear();
        Respuesta resp = movieService.getMovies("P");
        ArrayList<MovieDto> movieList = new ArrayList<>((List<MovieDto>) resp.getResultado("Movies"));
        movieList.stream().forEach(movie -> {
            AdminMovieCard newCard = new AdminMovieCard(movie);
            newCard.initCard();
            ProximasCardList.add(newCard);
            tilePaneProximanete.getChildren().add(newCard);
        });
    }
    
    private void deselectAllCards(ArrayList<AdminMovieCard> list){
        list.stream().forEach(card -> card.selectedStatus(false));
    }
    
    private void llenarCartelera(List<MovieDto> movies){
        tilePaneEnCartelera.getChildren().clear();
        
        //System.out.println("movies size:" + movies.size());
        movies.stream().forEach(e->{
            //System.out.println("peli");
            MovieCard2 card = new MovieCard2(true,e);
            card.initCard();
            tilePaneEnCartelera.getChildren().add(card);
        }); 
    }
    
    private void cargarCartelera(){
        List<MovieDto> listaDto=new ArrayList<>();

        MovieService ms = new MovieService();
        Respuesta r = ms.getMovies("C");
        
        if(r.getEstado()){
            //System.out.println("true");
            listaDto=(List<MovieDto>) r.getResultado("Movies");
            llenarCartelera(listaDto);
        }
        else{
            //System.out.println("false");
        }
 
    }
    
    private void llenarProximas(List<MovieDto> movies){
        tilePaneProximanete.getChildren().clear();
        //System.out.println("movies size:" + movies.size());
        movies.stream().forEach(e->{
            //System.out.println("peli");
            MovieCard2 card = new MovieCard2(false,e);
            card.initCard();
            //card.prefHeightProperty().bind(listaProximas.widthProperty());
            //card.prefHeightProperty().bind(listaProximas.heightProperty());
            tilePaneProximanete.getChildren().add(card);
        });
    }

     private void cargarProximas(){
        List<MovieDto> listaDto=new ArrayList<>();

        MovieService ms = new MovieService();
        Respuesta r = ms.getMovies("P");
        
        if(r.getEstado()){
            //System.out.println("true");
            listaDto=(List<MovieDto>) r.getResultado("Movies");
            llenarProximas(listaDto);
        }
        else{
            System.out.println("false");
        }
        
        
    }

    @FXML
    private void btn1Action(ActionEvent event) {
        AppContext.getInstance().set("AdminEditingMovie", null);
        FlowController.getInstance().goView("AdminNuevaMovie");
    }

    @FXML
    private void btn2Action(ActionEvent event) {
        ArrayList<AdminMovieCard> list;
        if(tabEnCartelera.isSelected()){
            list = this.CarteleraCardList;
        } else if(tabProximamente.isSelected()){
            list = this.ProximasCardList;
        } else {
            list = new ArrayList<>();
        }
        Long n = list.stream().filter(card -> card.isSelected()).count();
        if(n==0){
            System.out.println("Debes seleccionar la pelicula que deseas modificar");
        } else if(n>1){
            System.out.println("Debes seleccionar solamente una pelicula a modificar");
        } else {
            AdminMovieCard cardAux = list.stream().filter(card -> card.isSelected()).findFirst().get();
            AppContext.getInstance().set("AdminEditingMovie", cardAux.getMovie());
            FlowController.getInstance().goView("AdminNuevaMovie");
        }
    }

    @FXML
    private void btn3Action(ActionEvent event) {
        //TODO
    }
    
}
