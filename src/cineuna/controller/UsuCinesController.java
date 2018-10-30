/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.MovieCard;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuCinesController extends Controller implements Initializable {

    @FXML
    private HBox root;
    @FXML
    private Label lblNombreCine;
    @FXML
    private JFXListView<MovieCard> tpCartelera;
    @FXML
    private JFXListView<MovieCard> tpProximas;
    @FXML
    private Hyperlink lblNumTel;
    @FXML
    private Hyperlink lblCorreo;
    private JFXButton btnAbre;
    private JFXButton btnCierra;
    @FXML
    private StackPane spDialogos;
    private FontAwesomeIconView btnOpcionesUsu;
    private Hyperlink hlNombreUsuario;
    private VBox vbOpcionesUsu;
    private JFXPopup popUp;
    @FXML
    private Label lblAbre;
    @FXML
    private Label lblCierra;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("spDialogos",spDialogos);
        initContainers();
        //llenarInfoUsuario();
    }    

    @Override
    public void initialize() {
        initContainers();
        llenarCartelera();
        llenarProximas();
        llenarInfoCines();
        //llenarInfoUsuario();
    }
    
    private void initContainers(){
        this.popUp= new JFXPopup();
        this.vbOpcionesUsu=new VBox();
    }
    
    /**
     * llena el hyperlink del nombre de usuario y asigna el popup
     */
    public void llenarInfoUsuario(){
        this.hlNombreUsuario.setText((String) AppContext.getInstance().get("nombre"));
        this.hlNombreUsuario.setOnAction(e->{
            //FlowController.getInstance().goView("InfoUsuario");
        });
        
        this.btnOpcionesUsu.setOnMouseClicked(event ->{
            this.popUp.show(btnOpcionesUsu,JFXPopup.PopupVPosition.TOP,JFXPopup.PopupHPosition.LEFT,event.getX(),event.getY());
        });
        llenarOpcionesUsu();
        this.popUp.setPopupContent(this.vbOpcionesUsu);
    }
    
    /**
     * llena el vbox de las opciones del perfil de usuario
     */
    public void llenarOpcionesUsu(){
        for (int i = 0; i < 10; i++) {
            Hyperlink hl= new Hyperlink();
            switch (i) {
                case 1:
                    hl.setText("Cerrar Sesión");
                    hl.setOnAction(e->{
                        ((Stage) root.getScene().getWindow()).close();
                        FlowController.getInstance().goViewInWindow("LogIn");
                    });
                    this.vbOpcionesUsu.getChildren().add(hl);
                    break;
                default:
                    break;
            }
            
        }
    }
    
    private void llenarCartelera(){
        for (int i = 0; i < 10; i++) {
            MovieCard card = new MovieCard(true);
            card.initCard();
            tpCartelera.getItems().add(card);
        }
    }
    
    private void llenarProximas(){
        for (int i = 0; i < 10; i++) {
            MovieCard card = new MovieCard(false);
            card.initCard();
            tpProximas.getItems().add(card);
        }
    }
    
    private void llenarInfoCines(){
        lblNombreCine.setText("CineUNA");
        lblNumTel.setText("2772-6391");
        lblCorreo.setText("correo_prueba@cineuna.com");
        lblAbre.setText("8:00");
        lblCierra.setText("21:00");
    }
}
