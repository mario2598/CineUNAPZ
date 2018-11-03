/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.EspacioParaButaca;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminNuevaSalaController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXComboBox<String> cmbBoxTipo;
    @FXML
    private JFXToggleButton toggleBtnHabilitada;
    @FXML
    private Spinner<Integer> spnrColumnas, spnrFilas;
    @FXML
    private JFXButton btnAplicarDistribucion, btnCancelar, btnGuardar;
    @FXML
    private TilePane tpButacas;
    @FXML
    private BorderPane bpButacas;
    @FXML
    private Label lblPantalla;
    
    //Attributes
    private Integer columnas;
    private Integer filas;
    private ArrayList<EspacioParaButaca> butacaList;
    private ReadOnlyBooleanProperty stageMaximized;

    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        butacaList = new ArrayList<>();
        lblPantalla.widthProperty().addListener((observable, oldValue, newValue)->{
            if(!butacaList.isEmpty() && newValue!=null){
                Double anchura = lblPantalla.getWidth()*0.80;
                Integer dimButaca = ((anchura.intValue())/columnas);
                butacaList.stream().forEach(butaca -> {
                    butaca.cambiarDimension(dimButaca);
                });
            }
        });
//        this.stageMaximized = this.getStage().maximizedProperty();
//        stageMaximized.addListener(obs -> {
//            if(!butacaList.isEmpty()){
//                Double anchura = lblPantalla.getWidth()*0.80;
//                Integer dimButaca = ((anchura.intValue())/columnas);
//                butacaList.stream().forEach(butaca -> {
//                    butaca.cambiarDimension(dimButaca);
//                });
//            }
//        });
    }    

    /**
     * Initializer de la clase Controller
     */
    @Override
    public void initialize() {
        tpButacas.getChildren().clear();
        butacaList.clear();
        SpinnerValueFactory<Integer> columnasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 12);
        this.spnrColumnas.setValueFactory(columnasValueFactory);
        SpinnerValueFactory<Integer> filasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 6);
        this.spnrFilas.setValueFactory(filasValueFactory);
        cambiarDimension(12, 6);
    }
    
    //Methods
    private void cambiarDimension(Integer columnas, Integer filas){
        this.columnas = columnas;
        this.filas = filas;
        tpButacas.setPrefColumns(columnas);
        tpButacas.setPrefRows(filas);
        Integer butacas = columnas*filas;
        tpButacas.getChildren().clear();
        Double anchura = lblPantalla.getWidth()*0.80;
        Integer dimButaca = ((anchura.intValue())/columnas);
        if(dimButaca>0){
            for(int i = 0; i<butacas; i++){
                EspacioParaButaca espacioB = new EspacioParaButaca(dimButaca);
                butacaList.add(espacioB);
                tpButacas.getChildren().add(espacioB);
            }
        }
    }

    //FXML Methods
    @FXML
    private void btnAplicarDistribucionAction(ActionEvent event) {
        if(spnrColumnas.getValue()!=null && spnrFilas.getValue()!=null){
            Integer col = null, fil = null;
            try{
                col = spnrColumnas.getValue();
                fil = spnrFilas.getValue();
            } catch(Exception ex){
                System.out.println("Error cambiando la distribucion de las butacas\nError: " + ex);
            }
            if(col!=null && fil!=null){
                cambiarDimension(col, fil);
            }
        }
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
        
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        
    }
    
}
