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
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
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
    private final ReadOnlyDoubleProperty stageWidthProp = FlowController.getInstance().getStage().widthProperty();
    private final ReadOnlyDoubleProperty stageHeightProp = FlowController.getInstance().getStage().heightProperty();
    private Integer columnas;
    private Integer filas;
    private Boolean butacasDistribuidas;
    private ArrayList<AdminEspacioButaca> butacaList;
    private SalaDto sala;
    private Boolean editando = false;

    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        butacaList = new ArrayList<>();
        SpinnerValueFactory<Integer> columnasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 10);
        this.spnrColumnas.setValueFactory(columnasValueFactory);
        SpinnerValueFactory<Integer> filasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 5);
        this.spnrFilas.setValueFactory(filasValueFactory);
        cmbBoxTipo.getItems().addAll(
            "2D", "3D"
        );
        cmbBoxTipo.valueProperty().addListener((observale, oldValue, newValue) -> {
            if(newValue!=null && sala!=null){
                sala.setSalaTipo(newValue);
            }
        });
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
        spnrColumnas.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!editando){
                if(sala!=null){
                    columnas = newValue;
                    sala.setSalaCol(newValue.longValue());
                }
                aplicarDistribucion();
            } else {
                spnrColumnas.setDisable(true);
            }
        });
        spnrFilas.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!editando){
                if(sala!=null){
                    filas = newValue;
                    sala.setSalaFilas(newValue.longValue());
                    aplicarDistribucion();
                }
            } else{
                spnrFilas.setDisable(true);
            }
        });
        toggleBtnHabilitada.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null && sala!=null){
                sala.setSalaEstado(newValue ? "A":"I");
            }
        });
    }    

    /**
     * Initializer de la clase Controller
     */
    @Override
    public void initialize() {
        if(sala!=null)
            unbindSala();
        clearData();
        if(AppContext.getInstance().get("AdminEditingSala")!=null){
            sala = (SalaDto) AppContext.getInstance().get("AdminEditingSala");
            this.editando = true;
            bindSala();
            loadData();
        } else {
            sala = new SalaDto();
            this.editando = false;
            bindSala();
        }
        setDefaultData();
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
            Integer dimButaca;
            if(bpButacas.getWidth()>0){
                Double anchura = (bpButacas.getWidth()*0.82)/columnas;
                Double altura = (bpButacas.getHeight()*0.65)/filas;
                if(anchura > altura)
                    dimButaca = altura.intValue();
                else
                    dimButaca = anchura.intValue();
            } else {
                Double anchura = ((stageWidthProp.get()-241)*0.82)/columnas;
                Double altura = ((stageHeightProp.get()-110)*0.65)/filas;
                if(anchura > altura)
                    dimButaca = altura.intValue();
                else
                    dimButaca = anchura.intValue();
            }
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
            } else {
                System.out.println("No se ha aplicado la distribucion porque dimButaca <= 0");
            }
        }
    }

    private void bindSala() {
        txtNombre.textProperty().bindBidirectional(sala.salaNombre);
    }

    private void unbindSala() {
        txtNombre.textProperty().unbindBidirectional(sala.salaNombre);
    }

    private void loadData() {
        cmbBoxTipo.setValue(sala.getSalaTipo());
        spnrColumnas.getValueFactory().setValue(sala.getSalaCol().intValue());
        spnrFilas.getValueFactory().setValue(sala.getSalaFilas().intValue());
        toggleBtnHabilitada.setSelected(sala.getSalaEstado().equalsIgnoreCase("A"));
        aplicarDistribucion();
        butacasDistribuidas = true;
    }
    
    private void clearData(){
        this.tpButacas.getChildren().clear();
        this.butacaList.clear();
        this.butacasDistribuidas = false;
        this.editando = false;
        this.txtNombre.setText("");
        this.spnrColumnas.setDisable(false);
        this.spnrFilas.setDisable(false);
        this.spnrColumnas.getValueFactory().setValue(15);
        this.spnrFilas.getValueFactory().setValue(5);
    }
    
    private void setDefaultData(){
        toggleBtnHabilitada.setSelected(!toggleBtnHabilitada.isSelected());
        toggleBtnHabilitada.setSelected(!toggleBtnHabilitada.isSelected());
        cmbBoxTipo.getSelectionModel().select(0);
        spnrColumnas.getValueFactory().setValue(16);
        spnrFilas.getValueFactory().setValue(8);
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
    
    private void salir(){
        unbindSala();
        sala = null;
        FlowController.getInstance().goView("AdminSalas");
    }

    //FXML Methods
    @FXML
    private void btnCancelarAction(ActionEvent event) {
        salir();
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        if(validarDatosNecesarios()){
            if(!editando){
                this.butacaList.stream().map(AdminEspacioButaca::getButaca).forEach(butaca -> {
                    if(butaca!=null)
                        this.sala.getButacaList().add(butaca);
                });
            }
            this.sala.setCineId(AppContext.getInstance().getUsuario().getCineID());
            SalaService salaServ = new SalaService();
            Respuesta resp = salaServ.guardarSala(this.sala);
            if(resp.getEstado()){
                System.out.println("Se ha guardado la sala satisfactoriamente.");
            } else {
                System.out.println("Ha ocurrido un error guardando la sala\nError: " + resp.getMensaje());
            }
            salir();
        }
    }
    
}
