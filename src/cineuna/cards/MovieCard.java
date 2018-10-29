/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    private Boolean disponible;

    public MovieCard(Boolean disponible) {
        this.disponible = disponible;
    }
    
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
        infoVb.getChildren().addAll(cargarNombre(),cargarIdioma(),cargarFecha(),reservarBtn);
    }
    
    /**
     * inicializa la imagen de la pelicula
     */
    private void initPoster(){
        posterImg= new ImageView();
        posterImg.setPreserveRatio(false);
        posterImg.setFitWidth(110);
        posterImg.setFitHeight(125);
        posterImg.setTranslateX(-20);
        posterImg.setImage(new Image("cineuna/resources/images/Ejemplo.JPG"));
    }
    
    /**
     * inicializa el botón y su acción
     */
    private void initBtn(){
        if (disponible) {
           reservarBtn = new JFXButton("Reservar");
           reservarBtn.setOnAction(e->{
               FlowController.getInstance().goViewOnDialog("UsuInfoPelicula", (StackPane) AppContext.getInstance().get("spDialogos"));
           });
        }
        else{
           reservarBtn = new JFXButton("Información");
           reservarBtn.setOnAction(e->{
               FlowController.getInstance().goViewOnDialog("UsuInfoPelicula", (StackPane) AppContext.getInstance().get("spDialogos"));
           });
        }
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
    
    private Label cargarIdioma(){
        Label idioma = new Label("Idioma: ");
        idioma.setAlignment(Pos.CENTER);
        idioma.setText(idioma.getText()+"Español");
        return idioma;
    }
    
    private Label cargarFecha(){
       Label fecha = new Label();
       fecha.setAlignment(Pos.CENTER);
       if(disponible){
           fecha.setText("En cartelera hasta: "+ 28/10/18);
       }
       else{
           fecha.setText("Disponible hasta: "+ 28/10/18);
       }
       return fecha; 
    }
    
    /**
     * inicializa el text area con la descripción de la película a partir del movieDto
     * @return 
     */
    private Label cargarDescripcion(){
        Label descripcion = new Label("Fecha de estreno: ");
        descripcion.setText(descripcion.getText()+"28/10/18");
        descripcion.setPrefSize(500, 70);
        descripcion.autosize();
        
        return descripcion;
    }
    
    
    
}
