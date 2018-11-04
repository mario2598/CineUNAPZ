/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.MovieDto;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author robri
 */
public class MovieCard2 extends Card{
    private VBox vbRoot;
    private StackPane root;
    private ImageView poster;
    private VBox infoRoot;
    private Boolean disponible;
    private MovieDto movie;
    
    public MovieCard2(Boolean disponible) {
        this.disponible = disponible;
    }
    
    public MovieCard2(Boolean disponible,MovieDto movie) {
        this.disponible = disponible;
        this.movie = movie;
    }
    
    @Override
    public void initCard() {
        this.getStylesheets().add("cineuna/cards/StyleCards.css");
        this.setId("root");
        initRoot();
    }
    
    private void initRoot(){
        vbRoot=new VBox();
        vbRoot.setAlignment(Pos.CENTER);
        root=new StackPane();
        root.setPrefSize(130, 150);
        root.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vbRoot.getChildren().add(root);
        this.getChildren().add(vbRoot);
        initPoster();
        initInfoRoot();
    }
    
    private void initPoster(){
        poster=new ImageView(new Image("cineuna/resources/images/Ejemplo.JPG"));
        poster.setFitWidth(130);
        poster.setFitHeight(150);
        poster.setPreserveRatio(false);
        root.getChildren().add(poster);
        root.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("entró");
                infoRoot.setVisible(true);
            }
        });
    }
    
    private void initInfoRoot(){
        infoRoot=new VBox();
        infoRoot.setPrefSize(130, 150);
        infoRoot.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        infoRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        infoRoot.setAlignment(Pos.CENTER);
        infoRoot.setSpacing(30);
        infoRoot.setVisible(false);
        infoRoot.getStyleClass().add("info-root");
        root.getChildren().add(infoRoot);
        infoRoot.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("salió");
                infoRoot.setVisible(false);
            }
        });
        addTitle();
        //addDescription();
        addButton();
        addDate();
    }
    
    private void addTitle(){
        VBox hb=new VBox();
        hb.setAlignment(Pos.CENTER);
        Label titulo=new Label(movie.getMovieNombre());
        titulo.setAlignment(Pos.CENTER);
        Label fecha=new Label(movie.getMovieDate().toString());
        fecha.setAlignment(Pos.CENTER);
        fecha.getStyleClass().add("label-small");
        hb.getChildren().add(titulo);
        hb.getChildren().add(fecha);
        vbRoot.getChildren().add(hb);
    }
    
    private void addDescription(){
        JFXTextArea info=new JFXTextArea("asddddddddddddd");
        info.setDisable(false);
        info.setEditable(false);
        infoRoot.getChildren().add(info);
    }
    
    private void addDate(){
        Label fecha=new Label(movie.getMovieDate().toString());
        fecha.setAlignment(Pos.CENTER);
        infoRoot.getChildren().add(fecha);
    }
    
    private void addButton(){
        FontAwesomeIconView icon;
        if (disponible)
           icon=new FontAwesomeIconView(FontAwesomeIcon.TICKET); 
        else
            icon=new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
        
        icon.setOnMouseClicked(e->{
            AppContext.getInstance().set("peliDisponible",disponible);
            AppContext.getInstance().set("peliculaSel",movie);
            FlowController.getInstance().goViewOnDialog("UsuInfoPelicula", (StackPane) AppContext.getInstance().get("spDialogos"));
        });
        infoRoot.getChildren().add(icon);
    }
    
    private void eventoMostrarInfo(){
        infoRoot.setOnMouseExited(e->infoRoot.setVisible(false));
    }
    
    
}
