/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.MovieCard;
import cineuna.cards.MovieCard2;
import cineuna.model.UsuarioDto;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.LangUtils;
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
import javafx.scene.input.MouseEvent;
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
    private StackPane root;
    private Label lblNombreCine;
    private JFXListView<MovieCard2> tpCartelera;
    private JFXListView<MovieCard> tpProximas;
    private Hyperlink lblNumTel;
    private Hyperlink lblCorreo;
    private JFXButton btnAbre;
    private JFXButton btnCierra;
    @FXML
    private StackPane spDialogos;
    private FontAwesomeIconView btnOpcionesUsu;
    private Hyperlink hlNombreUsuario;
    private VBox vbOpcionesUsu;
    private JFXPopup popUp;
    private Label lblAbre;
    private Label lblCierra;
    @FXML
    private JFXListView<MovieCard2> listaCartelera;
    @FXML
    private JFXListView<MovieCard2> listaProximas;
    @FXML
    private Label lblCartelera;
    @FXML
    private Label lblProximas;
    private UsuarioDto usuario;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("spDialogos",spDialogos);
        //initContainers();
        //cargarIdioma();
    }    

    @Override
    public void initialize() {
        initContainers();
        cargarIdioma();
        llenarCartelera();
        llenarProximas();
    }
    
    private void initContainers(){
        this.popUp= new JFXPopup();
        this.vbOpcionesUsu=new VBox();
    }
    
    private void llenarCartelera(){
        listaCartelera.getItems().clear();
        for (int i = 0; i < 10; i++) {
            MovieCard2 card = new MovieCard2(true);
            card.initCard();
            listaCartelera.getItems().add(card);
        }
    }
    
    private void llenarProximas(){
        listaProximas.getItems().clear();
        for (int i = 0; i < 10; i++) {
            MovieCard2 card = new MovieCard2(false);
            card.initCard();
            listaProximas.getItems().add(card);
        }
    }

    private void volver(MouseEvent event) {
        FlowController.getInstance().goView("UsuSeleccionCines");
    }

    private void atrasCartelera(MouseEvent event) {
        listaCartelera.scrollTo(0);
    }

    private void adelanteCartelera(MouseEvent event) {
        listaCartelera.scrollTo(listaCartelera.getItems().size());
    }

    private void atrasProximas(MouseEvent event) {
        listaProximas.scrollTo(0);
    }

    private void adelanteProximas(MouseEvent event) {
        listaProximas.scrollTo(listaProximas.getItems().size());
    }
    
    private void cargarIdioma(){
        usuario=AppContext.getInstance().getUsuario();
        Integer idioma=Integer.valueOf(usuario.usuIdioma.getValue());
        if(idioma.equals(1)){
            LangUtils.getInstance().setLang("eng");
            System.out.println("español-> "+idioma);
        }
        else{
            System.out.println("inglés-> "+idioma);
            LangUtils.getInstance().setLang("eng");
        }
        
        LangUtils.getInstance().loadLabelLang(lblCartelera, "lblCartelera");
        LangUtils.getInstance().loadLabelLang(lblProximas, "lblProximas");
    }
}
