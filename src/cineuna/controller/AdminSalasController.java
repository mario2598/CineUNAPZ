/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.CineUNA;
import cineuna.cards.AdminSalaCard;
import cineuna.model.SalaDto;
import cineuna.service.SalaService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
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
public class AdminSalasController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private JFXListView<AdminSalaCard> listvSalas;
    @FXML
    private StackPane salasHolderPane;
    @FXML
    private JFXButton btnRefrescar, btnAgregar;
    //Attributes
    private final SalaService salaService = new SalaService();
    private HashMap<String, FXMLLoader> loaders;
    private ArrayList<SalaDto> salasList;
    
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listvSalas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
                mostrarSala(newValue.getSala());
        });
    }       

    /**
     * Metodo abstracto de la clase Controller
     */
    @Override
    public void initialize() {
        salasList = new ArrayList<>();
        loaders = new HashMap<>();
        listvSalas.getItems().clear();
        salasHolderPane.getChildren().clear();
        cargarSalas();
        generarSalasCard();
    }
    
    //Methods
    private void cargarSalas(){
        Respuesta resp = salaService.getListaSalas();
        if(resp.getEstado()){
            ArrayList<SalaDto> salaList = (ArrayList<SalaDto>) resp.getResultado("SalaList");
            salaList.stream().forEach(sala -> {
                salasList.add(sala);
            });
        }
    }
    
    private void generarSalasCard(){
        salasList.stream().forEach(sala -> {
            AdminSalaCard card = new AdminSalaCard();
            card.setSala(sala);
            card.initCard();
            listvSalas.getItems().add(card);
        });
    }
    
    private void mostrarSala(SalaDto sala){
        AppContext.getInstance().set("AdminShowingSala", sala);
        FXMLLoader loader = getLoader("AdminInfoSala");
        Controller controller = loader.getController();
        controller.setAccion(null);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = this.getStage();
            controller.setStage(stage);
        }
        salasHolderPane.getChildren().clear();
        salasHolderPane.getChildren().add(loader.getRoot());
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
                    } catch (Exception ex) {
                        loader = null;
                        java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }

    //FXML Methods
    @FXML
    private void btnAgregarAction(ActionEvent event) {
        AppContext.getInstance().set("AdminEditingSala", null);
        FlowController.getInstance().goView("AdminNuevaSala");
    }

    @FXML
    private void btnRefrescarAction(ActionEvent event) {
        initialize();
    }
    
}
