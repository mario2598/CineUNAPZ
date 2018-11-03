/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.CineUNA;
import cineuna.cards.SalaCard;
import cineuna.model.SalaDto;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
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
    private JFXListView<SalaCard> listvSalas;
    @FXML
    private StackPane salasHolderPane;
    @FXML
    private JFXButton btnVolver, btnAgregar;
    //Attributes
    private HashMap<String, FXMLLoader> loaders;
    private ArrayList<SalaDto> salasList;
    private Boolean agregandoNuevaSala;
    
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listvSalas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(!agregandoNuevaSala){
                if(newValue!=null)
                    mostrarSala(newValue.getSala());
            } else {
                System.out.println("Actualmente estas agregando una nueva sala, guarda o cancela cambios antes de salir");
            }
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
        agregandoNuevaSala = false;
    }
    
    //Methods
    private void cargarSalas(){
        //Aqui mas adelante se deberan cargar las salas
        for (int i = 0; i < 3; i++) {
            SalaDto sala = new SalaDto();
            sala.setSalaNombre("Sala " + String.valueOf(i+1));
            salasList.add(sala);
        }
    }
    
    private void generarSalasCard(){
        salasList.stream().forEach(sala -> {
            SalaCard card = new SalaCard();
            card.setSala(sala);
            card.initCard();
            listvSalas.getItems().add(card);
        });
    }
    
    private void mostrarSala(SalaDto sala){
        AppContext.getInstance().set("manteSala", sala);
        FXMLLoader loader = getLoader("AdminManteSala");
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
    
    private void agregarNuevaSala(SalaDto sala){
        AppContext.getInstance().set("manteSala", sala);
        FXMLLoader loader = getLoader("AdminNuevaSala");
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
        agregandoNuevaSala = true;
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
    private void btnVolverAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminMenu");
        FlowController.getInstance().btnVolverVisible(false);
    }

    @FXML
    private void btnAgregarAction(ActionEvent event) {
        if(!agregandoNuevaSala){
            SalaDto sala = new SalaDto();
            sala.setSalaNombre("Sala " + String.valueOf(listvSalas.getItems().size()+1));
            salasList.add(sala);
            SalaCard card = new SalaCard();
            card.setSala(sala);
            card.initCard();
            listvSalas.getItems().add(card);
            agregarNuevaSala(sala);
        } else {
            System.out.println("Actualmente estas agregando una nueva sala, guarda o cancela cambios antes de salir");
        }
    }
    
}
