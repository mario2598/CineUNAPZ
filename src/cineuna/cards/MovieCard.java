/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author robri
 */
public class MovieCard extends Card {

    private ImageView imagenCine;
    private VBox rootVb;
    private HBox root;
    private VBox infoVb;
    private ImageView posterImg;
    private JFXButton reservarBtn;
    private Separator sep;
    
    @Override
    public void initCard() {
        if(!this.isInitialized()){
           this.setPrefSize(635, 170);
           initBtn();
           initInfo();
           initPoster();
           initSeparator();
           initRoot();
           this.setInitialized(true); 
           this.getChildren().add(rootVb);
           //asigna la hoja de estilos para el card
           this.getStylesheets().clear();
           this.getStylesheets().add("cineuna/cards/StyleCards.css");
           this.setId("root");
        }
    }
    
    /**
     * inicializa el contenedor principal
     */
    private void initRoot(){
        rootVb = new VBox();
        rootVb.setAlignment(Pos.CENTER);
        rootVb.setPrefSize(635, 170);
        //hoja de estilos que vaa usar
        rootVb.getStyleClass().add("root");
        //id único de la hoja de estilos usada
        rootVb.setId("root");
        root= new HBox();
        root.setPrefSize(635, 150);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);
        root.getChildren().addAll(posterImg,infoVb);
        //añade el root y el separador
        rootVb.getChildren().addAll(root, sep);
        rootVb.setSpacing(10);
    }

    /**
     * inicializa el contenedor de información de la película
     */
    private void initInfo(){
        infoVb = new VBox();
        infoVb.setAlignment(Pos.CENTER);
        infoVb.setPrefSize(290, 150);
        infoVb.setSpacing(15);
        infoVb.getChildren().addAll(cargarNombre(),cargarDescripcion(),reservarBtn);
    }
    
    /**
     * inicializa la imagen de la pelicula
     */
    private void initPoster(){
        posterImg= new ImageView();
        posterImg.setPreserveRatio(false);
        posterImg.setFitWidth(110);
        posterImg.setFitHeight(125);
        posterImg.setImage(new Image("cineuna/resources/images/Ejemplo.JPG"));
    }
    
    /**
     * inicializa el botón y su acción
     */
    private void initBtn(){
        reservarBtn = new JFXButton("Reservar");
    }
    
    /**
     * inicializa el separador al final
     */
    private void initSeparator(){
        sep=new Separator(Orientation.HORIZONTAL);
        
    }
    
    /**
     * inicializa el nombre de la película a partir del dto
     * @return 
     */
    private Hyperlink cargarNombre(){
        Hyperlink nombre = new Hyperlink("DeadPool");
        nombre.setAlignment(Pos.CENTER);
        return nombre;
    }
    
    /**
     * inicializa el text area con la descripción de la película a partir del movieDto
     * @return 
     */
    private JFXTextArea cargarDescripcion(){
        JFXTextArea descripcion = new JFXTextArea();
        descripcion.setText("Deadpool debe proteger a Russell, un adolescente mutante, de Cable un soldado del futuro genéticamente modificado. Deadpool se alía con otros superhéroes para poder derrotar al poderoso Cable.");
        descripcion.setPrefSize(400, 70);
        return descripcion;
    }
    
    
    
}
