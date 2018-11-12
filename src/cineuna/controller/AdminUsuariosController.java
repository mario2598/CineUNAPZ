/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.CineUNA;
import cineuna.cards.AdminUsuariosCard;
import cineuna.model.UsuarioDto;
import cineuna.service.UsuarioService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminUsuariosController extends Controller implements Initializable {

    //FXML Attributes
    @FXML
    private JFXButton btnRefrescar;
    @FXML
    private JFXListView<AdminUsuariosCard> listViewUsers;
    @FXML
    private StackPane holderPane;

    //Attributes
    private final UsuarioService usuarioService = new UsuarioService();
    private final SimpleBooleanProperty usuarioActualizado = new SimpleBooleanProperty();
    private HashMap<String, FXMLLoader> loaders;
    private ArrayList<UsuarioDto> usuarioList;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listViewUsers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                mostrarSala(newValue.getUsuario());
            }
        });
        usuarioActualizado.set(false);
        usuarioActualizado.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                btnRefrescarAction();
        });
        AppContext.getInstance().set("UsuarioActualizadoProp", usuarioActualizado);
    }

    @Override
    public void initialize() {
        loaders = new HashMap<>();
        usuarioList = new ArrayList<>();
        listViewUsers.getItems().clear();
        holderPane.getChildren().clear();
        cargarUsuarios();
        generarSalasCard();
    }
    
    //Methods
    private void cargarUsuarios(){
        Respuesta resp = usuarioService.getListaUsuarios();
        if(resp.getEstado()){
            ArrayList<UsuarioDto> usuDto = (ArrayList<UsuarioDto>) resp.getResultado("UsuarioList");
            usuDto.stream().forEach(usu -> {
                usuarioList.add(usu);
            });
        }
    }
    
    private void generarSalasCard(){
        usuarioList.stream().forEach(usu -> {
            AdminUsuariosCard card = new AdminUsuariosCard();
            card.setUsuario(usu);
            card.initCard();
            listViewUsers.getItems().add(card);
        });
    }
    
    private void mostrarSala(UsuarioDto usu){
        AppContext.getInstance().set("AdminShowingUser", usu);
        ((SimpleBooleanProperty) AppContext.getInstance().get("UsuarioActualizadoProp")).set(false);
        FXMLLoader loader = getLoader("AdminManteUsuario");
        Controller controller = loader.getController();
        controller.setAccion(null);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = this.getStage();
            controller.setStage(stage);
        }
        holderPane.getChildren().clear();
        holderPane.getChildren().add(loader.getRoot());
    }
    
    private FXMLLoader getLoader(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(CineUNA.class.getResource("view/" + name + ".fxml"), FlowController.getInstance().getIdioma());
                        loader.load();
                        loaders.put(name, loader);
                    } catch (IOException ex) {
                        loader = null;
                        java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }
    
    //FXMLMethods
    @FXML
    private void btnRefrescarAction() {
        initialize();
    }

    
}
