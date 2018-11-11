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
public class UsuInicioController extends Controller implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private Hyperlink hlCartelera;
    @FXML
    private Hyperlink hlProximas;
    @FXML
    private TilePane tpPeliculas;
    @FXML
    private StackPane spDialogos;
    private UsuarioDto usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("spDialogos",spDialogos);
    }    

    @FXML
    private void irCartelera(ActionEvent event) {
        cargarCartelera();
    }

    @FXML
    private void irProximas(ActionEvent event) {
        cargarProximas();
    }

    @Override
    public void initialize() {
        cargarIdioma();
        cargarCartelera();
    }
    
    private void llenarCartelera(List<MovieDto> movies){
        tpPeliculas.getChildren().clear();
        movies.stream().forEach(e->{
            MovieCard2 card = new MovieCard2(true,e);
            card.initCard();
            tpPeliculas.getChildren().add(card);
        }); 
    }
    
    private void cargarCartelera(){
        List<MovieDto> listaDto=new ArrayList<>();
        System.out.println("intentando cargar cartelera");
        MovieService ms = new MovieService();
        Respuesta r = ms.getMovies("C");
        
        if(r.getEstado()){
            
            listaDto=(List<MovieDto>) r.getResultado("Movies");
            System.out.println("true:"+listaDto.size());
            llenarCartelera(listaDto);
        }
        else{
            System.out.println("false");
        }
 
    }
    
    private void llenarProximas(List<MovieDto> movies){
        tpPeliculas.getChildren().clear();
        movies.stream().forEach(e->{
            MovieCard2 card = new MovieCard2(false,e);
            card.initCard();
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
            llenarProximas(listaDto);
        }
        else{
            System.out.println("false");
        }
        
        
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
        
        LangUtils.getInstance().loadHyperlinkLang(hlCartelera, "lblCartelera");
        LangUtils.getInstance().loadHyperlinkLang(hlProximas, "lblProximas");
    }
}
