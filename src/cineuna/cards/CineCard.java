/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.util.FlowController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author robri
 */
public class CineCard extends Card{
    private ImageView imgCine;
    private Label lbl;
    private VBox root;

    @Override
    public void initCard() {
        initRoot();
        this.getChildren().add(root);
        this.getStylesheets().add("cineuna/cards/StyleCards.css");
        this.setId("root");
        initPoster();
        initLabel();
    }
    
    private void initRoot(){
        root=new VBox();
        root.setSpacing(10);
        this.setOnMouseClicked(e->{
            FlowController.getInstance().goView("UsuCines");
        });
    }
    
    private void initPoster(){
        imgCine= new ImageView(new Image("cineuna/resources/images/Ejemplo.JPG"));
        imgCine.setFitWidth(110);
        imgCine.setFitHeight(125);
        root.getChildren().add(imgCine);
    }
    
    private void initLabel(){
        lbl=new Label("nombre");
        root.getChildren().add(lbl);
        
    }
    
}
