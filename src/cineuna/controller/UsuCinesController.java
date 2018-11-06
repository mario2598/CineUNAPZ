/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.MovieCard2;
import cineuna.model.MovieDto;
import cineuna.model.UsuarioDto;
import cineuna.service.MovieService;
import cineuna.util.AppContext;
import cineuna.util.LangUtils;
import cineuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuCinesController extends Controller implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private StackPane spDialogos;
    private UsuarioDto usuario;
    @FXML
    private Hyperlink hlCartelera;
    @FXML
    private Hyperlink hlProximas;
    @FXML
    private TilePane tpPeliculas;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("spDialogos",spDialogos);
    }    

    @Override
    public void initialize() {
        //initContainers();
        cargarIdioma2();
        cargarCartelera();
    }
    
    private void llenarCartelera2(List<MovieDto> movies){
        tpPeliculas.getChildren().clear();
        
        //System.out.println("movies size:" + movies.size());
        movies.stream().forEach(e->{
            //System.out.println("peli");
            MovieCard2 card = new MovieCard2(true,e);
            card.initCard();
            tpPeliculas.getChildren().add(card);
        }); 
    }
    
    private void cargarCartelera(){
        List<MovieDto> listaDto=new ArrayList<>();

        MovieService ms = new MovieService();
        Respuesta r = ms.getMovies("C");
        
        if(r.getEstado()){
            //System.out.println("true");
            listaDto=(List<MovieDto>) r.getResultado("Movies");
            llenarCartelera2(listaDto);
        }
        else{
            System.out.println("false");
        }
 
    }
    
    private void llenarProximas2(List<MovieDto> movies){
        tpPeliculas.getChildren().clear();
        //System.out.println("movies size:" + movies.size());
        movies.stream().forEach(e->{
            //System.out.println("peli");
            MovieCard2 card = new MovieCard2(false,e);
            card.initCard();
            //card.prefHeightProperty().bind(listaProximas.widthProperty());
            //card.prefHeightProperty().bind(listaProximas.heightProperty());
            tpPeliculas.getChildren().add(card);
        });
    }

     private void cargarProximas(){
        List<MovieDto> listaDto=new ArrayList<>();

        MovieService ms = new MovieService();
        Respuesta r = ms.getMovies("P");
        
        if(r.getEstado()){
            //System.out.println("true");
            listaDto=(List<MovieDto>) r.getResultado("Movies");
            llenarProximas2(listaDto);
        }
        else{
            System.out.println("false");
        }
        
        
    }
    
    private void cargarIdioma2(){
        usuario=AppContext.getInstance().getUsuario();
        Integer idioma=Integer.valueOf(usuario.usuIdioma.getValue());
        if(idioma.equals(1)){
            LangUtils.getInstance().setLang("es");
            //System.out.println("español-> "+idioma);
        }
        else{
           // System.out.println("inglés-> "+idioma);
            LangUtils.getInstance().setLang("eng");
        }
        
        LangUtils.getInstance().loadHyperlinkLang(hlCartelera, "lblCartelera");
        LangUtils.getInstance().loadHyperlinkLang(hlProximas, "lblProximas");
    }

    @FXML
    private void irCartelera(ActionEvent event) {
        cargarCartelera();
    }

    @FXML
    private void irProximas(ActionEvent event) {
        cargarProximas();
    }
}
