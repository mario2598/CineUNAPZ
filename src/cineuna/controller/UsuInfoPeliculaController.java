/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cineuna.controller;

import cineuna.model.MovieDto;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.LangUtils;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class UsuInfoPeliculaController extends Controller implements Initializable {

    //FXML Attributes
    @FXML
    private StackPane spPoster;
    @FXML
    private ImageView ivPoster;
    @FXML
    private JFXButton btnComprar;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblCalificacion;
    @FXML
    private Label lblSinopsis;
    @FXML
    private WebView webViewTrailer;
    @FXML
    private StackPane dialogPane;
    
    //Attributes
    private final ReadOnlyDoubleProperty stageWidthProp = FlowController.getInstance().getStage().widthProperty();
    private final ReadOnlyDoubleProperty stageHeightProp = FlowController.getInstance().getStage().heightProperty();
    private MovieDto movie;
    private Boolean bindedMovie;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindedMovie = false;
    }

    @Override
    public void initialize() {
        if(bindedMovie)
            unbindData();
        clearData();
        this.movie = (MovieDto) AppContext.getInstance().get("UserShowingMovie");
        if(movie!=null){
            bindData();
            loadData();
        }
        if(AppContext.getInstance().getUsuario().getUsuAdmin().equalsIgnoreCase("S") || 
                movie.getMovieEstado().equalsIgnoreCase("P"))
            btnComprar.setVisible(false);
        else 
            btnComprar.setVisible(true);
        tamannoInicialImg();
        //play();
    }
    
    //Methods
    private void bindData(){
        lblNombre.textProperty().bind(movie.movieNombre);
        lblSinopsis.textProperty().bind(movie.movieResena);
        bindedMovie = true;
        cargarImagen();
        play();
    }
    
    private void unbindData(){
        lblNombre.textProperty().unbind();
        lblSinopsis.textProperty().unbind();
        bindedMovie = false;
    }
    
    private void loadData(){
        lblTipo.setText(movie.getMovieTipo());
        loadPoster();
    }
    
    private void clearData(){
        lblTipo.setText("");
        clearPoster();
    }
    
    private void loadPoster(){
        try {
            String imgPath = "src\\cineuna\\resources\\images\\" + movie.getMovieNombre() +".jpg";
            File file = new File(imgPath);
            movie.crearImagenDesdeByte();
            if(file.exists())
                ivPoster = new ImageView(movie.abrirImagen());
            else
                ivPoster = new ImageView(new Image("cineuna/resources/images/VenomPoster.jpg"));
            ivPoster.setPreserveRatio(false);
            ivPoster.setFitWidth(stageWidthProp.get()*0.25);
            ivPoster.setFitHeight(stageHeightProp.get()*0.60);
        } catch (IOException ex) {
            Logger.getLogger(UsuInfoPeliculaController.class.getName()).log(Level.SEVERE, "Error generando el poster de la pelicula", ex);
        }
    }
    
    private void cargarImagen(){
        //this.ivPoster.
        try{
            this.ivPoster.setImage(movie.abrirImagen());
        }
        catch(NullPointerException e){
            
        }
    }
    
    private void clearPoster(){
        ivPoster.setImage(null);
    }
    
    private void cargarIdioma(){
        Integer idioma=Integer.valueOf(AppContext.getInstance().getUsuario().usuIdioma.getValue());
        if(idioma.equals(1))
            LangUtils.getInstance().setLang("es");
        else
            LangUtils.getInstance().setLang("eng");
    }    

    @FXML
    private void btnComprarAction(ActionEvent event) {
        AppContext.getInstance().set("UserShowingMovie", this.movie);
        FlowController.getInstance().goViewOnDialog("UserInfoCompra", dialogPane);
    }
    
    private void play(){
        try{
        String url = "";
        if(AppContext.getInstance().getUsuario().getUsuIdioma()==1)
            url = movie.getMovieUrlesp();
        else
            url = movie.getMovieUrleng();
        System.out.println("intentando reproducir: "+url);
        this.webViewTrailer.getEngine().load(url);
        }
        catch(Exception e){
            
        }
    }
    
    private void redimensionadoImg(){
        spPoster.widthProperty().addListener((observable, oldValue, newValue) -> {
            Double width = newValue.doubleValue();
            ivPoster.setFitWidth(width * 0.92);
            //System.out.println("Redimensionando:\n newValue: " + newValue + "\n imgPosterValue: " + imgPoster.getFitWidth());
        });
        spPoster.heightProperty().addListener((observable, oldValue, newValue) -> {
            Double width = newValue.doubleValue();
            ivPoster.setFitHeight(width * 0.92);
            //System.out.println("Redimensionando:\n newValue: " + newValue + "\n imgPosterValue: " + imgPoster.getFitHeight());
        });
    }
    
    
    private void tamannoInicialImg(){
        ivPoster.setFitWidth(spPoster.widthProperty().get() * 0.92);
        ivPoster.setFitHeight(spPoster.heightProperty().get() * 0.92);
    }
}
