/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.MovieDto;
import cineuna.service.MovieService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminNuevaMovieController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private BorderPane root;
    @FXML
    private ImageView imgPoster;
    @FXML
    private HBox hbPoster;
    @FXML
    private JFXButton btnCancelar, btnGuardar;
    @FXML
    private JFXTextField txtEspNombre, txtEngNombre, txtEspTrailer, txtEngTrailer, txtDuracion;
    @FXML
    private JFXTextArea txtEspSinopsis, txtEngSinopsis;
    @FXML
    private JFXCheckBox chkBoxEsp1, chkBoxEng1;
    @FXML
    private JFXDatePicker datePickEstreno;
    @FXML
    private JFXComboBox<String> cmboBoxEstado, cmboBoxTipo;
    
    //Attributes
    private final MovieService movieService = new MovieService();
    private MovieDto movie;
    private Boolean editando;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgPoster.setImage(new Image("cineuna/resources/images/VenomPoster.jpg"));
        imgPoster.setPreserveRatio(true);
        //Size Listeners
        hbPoster.widthProperty().addListener((observable, oldValue, newValue) -> {
            Double width = newValue.doubleValue();
            imgPoster.setFitWidth(width * 0.92);
            System.out.println("Redimensionando:\n newValue: " + newValue + "\n imgPosterValue: " + imgPoster.getFitWidth());
        });
        hbPoster.heightProperty().addListener((observable, oldValue, newValue) -> {
            Double width = newValue.doubleValue();
            imgPoster.setFitHeight(width * 0.92);
            System.out.println("Redimensionando:\n newValue: " + newValue + "\n imgPosterValue: " + imgPoster.getFitHeight());
        });
        //Info Listeners
        cmboBoxTipo.getItems().addAll("2D", "3D");
        cmboBoxEstado.getItems().addAll("Próximamente", "En Cartelera", "Inactiva");
        cmboBoxTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(movie!=null){
                movie.setMovieTipo(newValue);
            }
        });
        cmboBoxEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(movie!=null){
                switch(newValue){
                    case "Próximamente":
                        movie.setMovieEstado("P");
                        break;
                    case "En Cartelera":
                        movie.setMovieEstado("C");
                        break;
                    case "Inactiva":
                        movie.setMovieEstado("I");
                        break;
                }
            }
        });
        datePickEstreno.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(movie!=null){
                movie.setMovieDate(newValue);
            }
        });
    }    

    @Override
    public void initialize() {
        if(movie!=null)
            unbindMovie();
        cleanData();
        if(AppContext.getInstance().get("AdminEditingMovie")!=null){
            movie = (MovieDto) AppContext.getInstance().get("AdminEditingMovie");
            this.editando = true;
            bindMovie();
            loadData();
        } else {
            movie = new MovieDto();
            this.editando = false;
            bindMovie();
            cmboBoxEstado.setValue("Próximamente");
            cmboBoxTipo.setValue("2D");
        }
    }

    private void imgPosterAction(MouseEvent event) {
        System.out.println("Cargar poster");
    }
    
    private void bindMovie(){
        txtEspNombre.textProperty().bindBidirectional(movie.movieNombre);
        txtEspTrailer.textProperty().bindBidirectional(movie.movieUrlesp);
        txtEspSinopsis.textProperty().bindBidirectional(movie.movieResena);
        txtDuracion.textProperty().bindBidirectional(movie.movieDuracion);
    }
    
    private void unbindMovie(){
        txtEspNombre.textProperty().unbindBidirectional(movie.movieNombre);
        txtEspTrailer.textProperty().unbindBidirectional(movie.movieUrlesp);
        txtEspSinopsis.textProperty().unbindBidirectional(movie.movieResena);
        txtDuracion.textProperty().unbindBidirectional(movie.movieDuracion);
    }
    
    private void loadData(){
        txtDuracion.setText(movie.getMovieDuracion()!=null ? movie.getMovieDuracion().toString():"");
        datePickEstreno.setValue(movie.getMovieDate());
        cmboBoxTipo.setValue(movie.getMovieTipo()!=null ? movie.getMovieTipo():"2D");
        switch(movie.getMovieEstado()){
            case "P":
                cmboBoxEstado.setValue("Próximamente");
                break;
            case "C":
                cmboBoxEstado.setValue("En Cartelera");
                break;
            case "I":
                cmboBoxEstado.setValue("Inactiva");
                break;
        }
    }
    
    private void cleanData(){
        txtEspNombre.setText("");
        txtEngNombre.setText("");
        txtEspTrailer.setText("");
        txtEngTrailer.setText("");
        txtEspSinopsis.setText("");
        txtEngSinopsis.setText("");
        txtDuracion.setText("");
        chkBoxEsp1.setSelected(false);
        chkBoxEng1.setSelected(false);
        datePickEstreno.setValue(null);
    }
    
    private void salir(){
        unbindMovie();
        movie = null;
        FlowController.getInstance().goView("AdminMovies");
    }
    
    private void printDto(){
        System.out.println("Info del MovieDto:"
                        + "\n\tNombre: " + movie.getMovieNombre()
                        + "\n\tReseña: " + movie.getMovieResena()
                        + "\n\tTipo: " + movie.getMovieTipo()
                        + "\n\tEstado: " + movie.getMovieEstado()
                        + "\n\tDuración: " + movie.getMovieDuracion()
                        + "\n\tEstreno: " + movie.getMovieDate()
        );
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
        salir();
    }

    @FXML
    private void btcGuardarAction(ActionEvent event) {
        unbindMovie();
        printDto();
        try{
            Respuesta resp = movieService.guardarMovie(movie);
            if(resp.getEstado()){
                System.out.println("Se ha guardado la película satisfactoriamente.");
                salir();
            } else {
                System.out.println("Ha ocurrido un error guardando la película\nError: " + resp.getMensaje());
            }
        } catch(Exception ex){
            System.out.println("Ha ocurrido un error en la parte del cliente guardando la película\nError: " + ex);
        }
    }

    @FXML
    private void buscarImagen(MouseEvent event) throws FileNotFoundException {
        FileChooser fc=new FileChooser();
        File sel = fc.showOpenDialog(null);
        if(fc!=null){
            movie.guardarImagenByte(sel);
        }
        else{
            System.out.println("imagen obtenida desde windows vacía");
        }
        //FileNameExtensionFilter filter;
    }
    
}
