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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminNuevaMovieController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private Label lblDuracionInfo;
    @FXML
    private BorderPane root;
    @FXML
    private HBox hbPoster;
    @FXML
    private ImageView imgPoster;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXCheckBox chkBoxEsp1;
    @FXML
    private JFXCheckBox chkBoxEng1;
    @FXML
    private JFXDatePicker datePickEstreno;
    @FXML
    private JFXComboBox<String> cmboBoxEstado;
    @FXML
    private JFXComboBox<String> cmboBoxTipo;
    @FXML
    private JFXTextField txtDuracion;
    @FXML
    private JFXTextField txtEspNombre;
    @FXML
    private JFXTextArea txtEspSinopsis;
    @FXML
    private JFXTextField txtEspTrailer;
    @FXML
    private JFXTextField txtEngNombre;
    @FXML
    private JFXTextArea txtEngSinopsis;
    @FXML
    private JFXTextField txtEngTrailer;
    
    //Attributes
    private final MovieService movieService = new MovieService();
    private MovieDto movie;
    private Boolean editando;
    private String errorMensaje;
    
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
        if(AppContext.getInstance().getUsuario().getUsuIdioma()==1)
            cmboBoxEstado.getItems().addAll("Próximamente", "En Cartelera", "Inactiva");
        else
            cmboBoxEstado.getItems().addAll("Coming Soon", "Available", "Inactive");
        cmboBoxTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(movie!=null){
                movie.setMovieTipo(newValue);
            }
        });
        cmboBoxEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(movie!=null){
                switch(newValue){
                    case "Próximamente": case "Coming Soon":
                        movie.setMovieEstado("P");
                        break;
                    case "En Cartelera": case "Available":
                        movie.setMovieEstado("C");
                        break;
                    case "Inactiva": case "Inactive":
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
        chkBoxEsp1.selectedProperty().addListener(event -> {
            changeLanguaje();
        });
        chkBoxEsp1.selectedProperty().addListener(event -> {
            changeLanguaje();
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
       // cargarIdioma();
    }
    
    private void bindMovie(){
        txtEspNombre.textProperty().bindBidirectional(movie.movieNombre);
        txtEngNombre.textProperty().bindBidirectional(movie.movieNombreing);
        txtEspTrailer.textProperty().bindBidirectional(movie.movieUrlesp);
        txtEngTrailer.textProperty().bindBidirectional(movie.movieUrleng);
        txtEspSinopsis.textProperty().bindBidirectional(movie.movieResena);
        txtEngSinopsis.textProperty().bindBidirectional(movie.movieResenaing);
        txtDuracion.textProperty().bindBidirectional(movie.movieDuracion);
    }
    
    private void unbindMovie(){
        txtEspNombre.textProperty().unbindBidirectional(movie.movieNombre);
        txtEngNombre.textProperty().unbindBidirectional(movie.movieNombreing);
        txtEspTrailer.textProperty().unbindBidirectional(movie.movieUrlesp);
        txtEngTrailer.textProperty().unbindBidirectional(movie.movieUrleng);
        txtEspSinopsis.textProperty().unbindBidirectional(movie.movieResena);
        txtEngSinopsis.textProperty().unbindBidirectional(movie.movieResenaing);
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
        System.out.println("movie: " + movie.getMovieNombre() + ", idioma: " + movie.getMovieIdioma());
        if(movie.getMovieIdioma().equals(Long.valueOf("3")) || movie.getMovieIdioma().equals(Long.valueOf("1"))){
            chkBoxEsp1.setSelected(true);
        }
        if(movie.getMovieIdioma().equals(Long.valueOf("3")) || movie.getMovieIdioma().equals(Long.valueOf("2"))){
            chkBoxEng1.setSelected(true);
        }
    }
    
    private void changeLanguaje(){
        if(movie!=null){
            if(chkBoxEsp1.isSelected() && chkBoxEng1.isSelected()){
                movie.setMovieIdioma(new Long(3));
            } else if(chkBoxEsp1.isSelected()){
                 movie.setMovieIdioma(new Long(1));
            } else if(chkBoxEng1.isSelected()){
                movie.setMovieIdioma(new Long(2));
            } else {
                movie.setMovieIdioma(new Long(0));
            }
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
    
    private Boolean validadInfoNecesaria(){
        Boolean hayError = false;
        errorMensaje = "Se ha producido un error guardando la nueva sala, revisa los siguientes datos faltantes:";
        if(!chkBoxEsp1.isSelected() && !chkBoxEng1.isSelected()){
            hayError = true;
            errorMensaje = "\n\tDebes seleccionar al menos un idioma.";
        }
        if(datePickEstreno.getValue()==null){
            hayError = true;
            errorMensaje = "\n\tDebes registrar una fecha de estreno.";
        }
        if(txtDuracion.getText().isEmpty()){
            hayError = true;
            errorMensaje = "\n\tDebes registrar la duración en minutos.";
        } else{
            try{
                Integer tryCast = Integer.valueOf(txtDuracion.getText());
            } catch(NumberFormatException ex){
                hayError = true;
                errorMensaje = "\n\tEstas ingresando valores alfabéticos en el campo para la duración.";
            }
        }
        if(txtEspNombre.getText().isEmpty() || txtEspSinopsis.getText().isEmpty() || txtEspTrailer.getText().isEmpty()){
            hayError = true;
            errorMensaje = "\n\tFaltan datos de la película en español.";
        }
        if(txtEngNombre.getText().isEmpty() || txtEngSinopsis.getText().isEmpty() || txtEngTrailer.getText().isEmpty()){
            hayError = true;
            errorMensaje = "\n\tFaltan datos de la película en inglés.";
        }
        if(!hayError)
            errorMensaje = null;
        return !hayError;
    }
    
    private void salir(){
        unbindMovie();
        movie = null;
        FlowController.getInstance().goView("AdminMovies");
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
        salir();
    }

    @FXML
    private void btcGuardarAction(ActionEvent event) {
        if(validadInfoNecesaria()){
            unbindMovie();
            try{
                Respuesta resp = movieService.guardarMovie(movie);
                if(resp.getEstado()){
                    System.out.println("Se ha guardado la película satisfactoriamente.");
                    salir();
                } else {
                    System.out.println("Ha ocurrido un error guardando la película\nError: " + resp.getMensaje());
                    bindMovie();
                }
            } catch(Exception ex){
                System.out.println("Ha ocurrido un error en la parte del cliente guardando la película\nError: " + ex);
                bindMovie();
            }
        } else {
            System.out.println(errorMensaje);
        }
    }
    
    private void cargarIdioma(){
//        LangUtils.getInstance().setLang("es");
//        LangUtils.getInstance().loadLabelLang(lblLenguajes, "lblLenguajes");
//        LangUtils.getInstance().loadLabelLang(lblEstreno, "lblEstreno");
//        LangUtils.getInstance().loadLabelLang(lblEstado, "lblEstado");
//        LangUtils.getInstance().loadLabelLang(lblTipo, "lblTipo");
//        LangUtils.getInstance().loadLabelLang(lblDuracion, "lblDuracion");
//        LangUtils.getInstance().loadLabelLang(lblSegundos, "lblSegundos");
//        LangUtils.getInstance().loadCheckBoxLang(chkBoxEsp1, "chkBoxEsp1");
//        LangUtils.getInstance().loadCheckBoxLang(chkBoxEng1, "chkBoxEng1");
//        LangUtils.getInstance().loadTabLang(tabInfoEsp, "tabInfoEsp");
//        LangUtils.getInstance().loadTabLang(tabInfoEng, "tabInfoEng");
//        LangUtils.getInstance().loadButtonLang(btnGuardar, "btnGuardar");
//        LangUtils.getInstance().loadButtonLang(btnCancelar, "btnCancelar");
    }

    @FXML
    @SuppressWarnings("empty-statement")
    private void cargarPoster(MouseEvent event) throws FileNotFoundException, IOException {
        FileChooser fc=new FileChooser();
        File sel = fc.showOpenDialog(null);
        if(sel!=null){
            movie.guardarImagenByte(sel);
            imgPoster.setImage(new Image(sel.toURI().toString()));
          
        }
        else{
            System.out.println("imagen obtenida desde windows vacía");
        }
        //FileNameExtensionFilter filter;
    }
    
}
