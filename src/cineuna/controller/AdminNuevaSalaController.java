/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminEspacioButaca;
import cineuna.model.ButacaDto;
import cineuna.model.SalaDto;
import cineuna.service.SalaService;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    private JFXButton btnCancelar, btnGuardar;
    @FXML
    private TilePane tpButacas;
    @FXML
    private BorderPane bpButacas;
    @FXML
    private Label lblPantalla;
    
    //Attributes
    private Integer columnas;
    private Integer filas;
    private Boolean butacasDistribuidas;
    private ArrayList<AdminEspacioButaca> butacaList;

    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        butacaList = new ArrayList<>();
        SpinnerValueFactory<Integer> columnasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 15);
        this.spnrColumnas.setValueFactory(columnasValueFactory);
        SpinnerValueFactory<Integer> filasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 8);
        this.spnrFilas.setValueFactory(filasValueFactory);
        cmbBoxTipo.getItems().addAll(
            "2D", "3D"
        );
        cmbBoxTipo.getSelectionModel().selectFirst();
        lblPantalla.widthProperty().addListener((observable, oldValue, newValue)->{
            if(!butacaList.isEmpty() && newValue!=null){
                Double anchura = (bpButacas.getWidth()*0.82)/columnas;
                Double altura = (bpButacas.getHeight()*0.65)/filas;
                Integer dimButaca;
                if(anchura > altura)
                    dimButaca = altura.intValue();
                else
                    dimButaca = anchura.intValue();
                butacaList.stream().forEach(butaca -> {
                    butaca.cambiarDimension(dimButaca);
                });
            }
        });
        spnrColumnas.valueProperty().addListener(event -> {
            this.columnas = spnrColumnas.getValue();
            aplicarDistribucion();
        });
        spnrFilas.valueProperty().addListener(event -> {
            this.filas = spnrFilas.getValue();
            aplicarDistribucion();
        });
    }    

    /**
     * Initializer de la clase Controller
     */
    @Override
    public void initialize() {
        tpButacas.getChildren().clear();
        butacaList.clear();
        this.butacasDistribuidas = false;
        this.txtNombre.setText("");
        this.spnrColumnas.getValueFactory().setValue(16);
        this.spnrFilas.getValueFactory().setValue(8);
    }
    
    //Methods
    private void aplicarDistribucion() {
        if(spnrColumnas.getValue()!=null && spnrFilas.getValue()!=null){
            this.columnas = spnrColumnas.getValue();
            this.filas = spnrFilas.getValue();
            tpButacas.setPrefColumns(columnas);
            tpButacas.setPrefRows(filas);
            tpButacas.getChildren().clear();
            butacaList.clear();
            Double anchura = (bpButacas.getWidth()*0.82)/columnas;
            Double altura = (bpButacas.getHeight()*0.65)/filas;
            Integer dimButaca;
            if(anchura > altura)
                dimButaca = altura.intValue();
            else
                dimButaca = anchura.intValue();
//            System.out.println("Dimension de la butaca: " + dimButaca);
            if(dimButaca>0){
                for(int i = 0; i < this.filas; i++){
                    for (int j = 0; j < this.columnas; j++) {
                        AdminEspacioButaca espacioB = new AdminEspacioButaca(dimButaca);
                        ButacaDto butaca = new ButacaDto();
                        butaca.setButFila(new Long(i));
                        butaca.setButColumna(new Long(j));
                        butaca.setButLetra(getLetraFila(i) + String.valueOf(j+1));
                        butaca.setButActiva("A");
                        butaca.setButEstado("D");
                        espacioB.setButaca(butaca);
                        butacaList.add(espacioB);
                        tpButacas.getChildren().add(espacioB);
                    }
                }
                this.butacasDistribuidas = true;
            }
        }
    }
    
    private String getLetraFila(Integer fila){
        if( fila<0 ) {
            return "-" + getLetraFila(-fila-1);
        }
        int quot = fila/26;
        int rem = fila%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return getLetraFila(quot-1) + letter;
        }
    }
    
    private Boolean validarDatosNecesarios(){
        Boolean accepted = true;
        if(this.txtNombre.getText().isEmpty()){
            accepted = false;
            System.out.println("Debes ingresar un nombre para la nueva sala.");
        }
        if(!this.butacasDistribuidas){
            accepted = false;
            System.out.println("Debes organizar la distribucion de las butacas para la nueva sala.");
        }
        return accepted;
    }

    //FXML Methods
    @FXML
    private void btnCancelarAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminSalas");
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        if(validarDatosNecesarios()){
            SalaDto newSala = new SalaDto();
            newSala.setSalaNombre(this.txtNombre.getText());
            newSala.setSalaCol(new Long(this.spnrColumnas.getValue()));
            newSala.setSalaFilas(new Long(this.spnrFilas.getValue()));
            newSala.setSalaEstado(this.toggleBtnHabilitada.isSelected() ? "A":"I");
            newSala.setSalaTipo(this.cmbBoxTipo.getValue());
            this.butacaList.stream().map(AdminEspacioButaca::getButaca).forEach(butaca -> {
                newSala.getButacaList().add(butaca);
            });
            newSala.setCineId(new Long(3));
            SalaService salaServ = new SalaService();
            Respuesta resp = salaServ.guardarSala(newSala);
            if(resp.getEstado()){
                System.out.println("Se ha guardado la sala satisfactoriamente.");
            } else {
                System.out.println("Ha ocurrido un error guardando la sala\nError: " + resp.getMensaje());
            }
            FlowController.getInstance().goView("AdminSalas");
        }
    }
    
}
