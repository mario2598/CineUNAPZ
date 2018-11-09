/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.MovieCard2;
import cineuna.model.MovieDto;
import cineuna.service.MovieService;
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
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminMoviesController extends Controller implements Initializable {

    @FXML
    private JFXButton btn1, btn2, btn3;
    @FXML
    private JFXTabPane tabPaneMovies;
    @FXML
    private Tab tabEnCartelera, tabProximamente;
    @FXML
    private TilePane tilePaneEnCartelera, tilePaneProximanete;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }
    
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
    }
    
    private void cargarProximasPeliculas(){
        for (int i = 0; i < 5; i++) {
            MovieDto movie = new MovieDto();
            movie.setMovieNombre("Ejemplo 1");
            movie.setMovieDate(LocalDate.now());
            movie.setMovieDuracion(new Long(120));
            movie.setMoviePortada("cineuna/resourses/images/VenomPoster.jpg");
            movie.setMovieEstado("A");
            MovieCard2 card = new MovieCard2(true, movie);
            card.initCard();
            tilePaneProximanete.getChildren().add(card);
        }
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
        FlowController.getInstance().goView("AdminNuevaMovie");
    }

    @FXML
    private void btn2Action(ActionEvent event) {
        //TODO
    }

    @FXML
    private void btn3Action(ActionEvent event) {
        //TODO
    }
    
}
