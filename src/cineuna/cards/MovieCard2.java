/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;

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
        root.setPrefSize(140, 180);
        root.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getChildren().add(root);
        initPoster();
        initInfoRoot();
    }
    
    private void initPoster(){
        poster=new ImageView(new Image("cineuna/resources/images/Ejemplo.JPG"));
        poster.setFitWidth(140);
        poster.setFitHeight(180);
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
        infoRoot.setPrefSize(140, 180);
        infoRoot.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        infoRoot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        infoRoot.setAlignment(Pos.BOTTOM_CENTER);
        infoRoot.setSpacing(5);
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
        infoRoot.setOnMouseClicked(e->{
            AppContext.getInstance().set("peliDisponible",disponible);
            FlowController.getInstance().goViewOnDialog("UsuInfoPelicula", (StackPane) AppContext.getInstance().get("spDialogos"));
        });
        addTitle();
        addDescription();
    }
    
    private void addTitle(){
        Hyperlink titulo=new Hyperlink("Nombre");
        titulo.setAlignment(Pos.CENTER);
        infoRoot.getChildren().add(titulo);
    }
    
    private void addDescription(){
        JFXTextArea info=new JFXTextArea("asdddddddddddddddddddddddddddddddddddddddddd sddddddddddddddddd");
        info.setDisable(true);
        infoRoot.getChildren().add(info);
    }
    
    private void eventoMostrarInfo(){
        
        infoRoot.setOnMouseExited(e->infoRoot.setVisible(false));
    }
    
    
}
