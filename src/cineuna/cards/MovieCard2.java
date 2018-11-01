/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author robri
 */
public class MovieCard2 extends Card{
    private StackPane root;
    private ImageView poster;
    private VBox infoRoot;
    private Boolean disponible;
    
    public MovieCard2(Boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public void initCard() {
        this.getStylesheets().add("cineuna/cards/StyleCards.css");
        this.setId("root");
        initRoot();
    }
    
    private void initRoot(){
        root=new StackPane();
        root.setPrefSize(130, 150);
        root.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(root);
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
        Hyperlink titulo=new Hyperlink("Nombre");
        titulo.setAlignment(Pos.CENTER);
        infoRoot.getChildren().add(titulo);
    }
    
    private void addDescription(){
        JFXTextArea info=new JFXTextArea("asdddddddddddddddddddddddddddddddddddddddddd sddddddddddddddddd");
        info.setDisable(false);
        info.setEditable(false);
        infoRoot.getChildren().add(info);
    }
    
    private void addDate(){
        Label fecha=new Label("20/8/18");
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
            FlowController.getInstance().goViewOnDialog("UsuInfoPelicula", (StackPane) AppContext.getInstance().get("spDialogos"));
        });
        infoRoot.getChildren().add(icon);
    }
    
    private void eventoMostrarInfo(){
        
        infoRoot.setOnMouseExited(e->infoRoot.setVisible(false));
    }
    
    
}
