/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminEspacioButaca;
import cineuna.cards.UserEspacioButaca;
import cineuna.model.ButacaDto;
import cineuna.model.ReservaDto;
import cineuna.model.SalaDto;
import cineuna.model.TandaDto;
import cineuna.model.UsuarioDto;
import cineuna.service.ButacaService;
import cineuna.service.ReservaService;
import cineuna.service.SalaService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuSeleccionTandaController extends Controller implements Initializable {

    //FXML Attributes
    @FXML
    private JFXDialogLayout root;
    @FXML
    private Label lblCine;
    @FXML
    private Label lblSala;
    @FXML
    private Label lblAsientos;
    @FXML
    private Label lblCosto;
    @FXML
    private TilePane apReserva;
    @FXML
    private Label lblMsjAsientos;
    @FXML
    private Label lblMsjCosto;
    @FXML
    private JFXButton btnReservar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPantalla;
    @FXML
    private BorderPane bpButacas;
    
    //Attributes
    private final ReadOnlyDoubleProperty stageWidthProp = FlowController.getInstance().getStage().widthProperty();
    private final ReadOnlyDoubleProperty stageHeightProp = FlowController.getInstance().getStage().heightProperty();
    private final ButacaService butacaService = new ButacaService();
    private final ReservaService reservaService = new ReservaService();
    private Integer columnas;
    private Integer filas;
    private Hilo hilo;
    private UsuarioDto usuario;
    private SalaDto sala;
    private TandaDto tanda;
    private ArrayList<UserEspacioButaca> espacioButacaList;
    private ArrayList<ButacaDto> butacaList;
    private ArrayList<ReservaDto> reservaList;
    
    public class Hilo extends Thread{
        private Boolean activado;
        
        public Hilo(){
            this.activado=true;
        }
        
        public void detener(){
            this.activado=false;
        }
        
        @Override
        public void run() {
            while(activado){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
//                        cargarListaReservas();
//                        distribuirReservas();
//                        eliminarReservasViejas();
//                        refrescarCamposButaca();
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UsuSeleccionTandaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        espacioButacaList = new ArrayList<>();
        butacaList = new ArrayList<>();
        reservaList = new ArrayList<>();
        bpButacas.widthProperty().addListener((observable, oldValue, newValue)->{
            if(!espacioButacaList.isEmpty() && newValue!=null){
                Double anchura = ((stageWidthProp.get()-350)*0.75)/columnas;
                Double altura = ((stageHeightProp.get()-100)*0.65)/filas;
                Integer dimButaca;
                if(anchura > altura)
                    dimButaca = altura.intValue();
                else
                    dimButaca = anchura.intValue();
                espacioButacaList.stream().forEach(butaca -> {
                    butaca.cambiarDimension(dimButaca);
                });
            }
        });
    }    

    @Override
    public void initialize() {
        this.usuario = AppContext.getInstance().getUsuario();
//        this.sala = (SalaDto) AppContext.getInstance().get("UserSelectedSala");
        this.tanda = (TandaDto) AppContext.getInstance().get("UserSelectedTanda");
        
        Respuesta resp = (new SalaService()).getSala(new Long(33));
        this.sala = ((SalaDto) resp.getResultado("Sala"));
        
        cargarListaButacas(sala.getSalaId());
        aplicarDistribucion();
        hilo=new Hilo();
        hilo.start();
    }
    
    //Methods
    private void aplicarDistribucion() {
        columnas = sala.getSalaCol().intValue();
        filas = sala.getSalaFilas().intValue();
        apReserva.setPrefColumns(columnas);
        apReserva.setPrefRows(filas);
        apReserva.getChildren().clear();
        espacioButacaList.clear();
        Integer dimButaca;
        Double anchura = ((stageWidthProp.get()-350)*0.75)/columnas;
        Double altura = ((stageHeightProp.get()-100)*0.65)/filas;
        if(anchura > altura)
            dimButaca = altura.intValue();
        else
            dimButaca = anchura.intValue();
        if(dimButaca>0){
            butacaList.sort((b1, b2) -> {
                if(b1.getButFila()>b2.getButFila())
                    return 1;
                else if(b1.getButFila()<b2.getButFila())
                    return -1;
                else if(b1.getButColumna()>b2.getButColumna())
                    return 1;
                else if(b1.getButColumna()<b2.getButColumna())
                    return -1;
                else 
                    return 0;
            });
            butacaList.stream().forEach(butaca -> {
                UserEspacioButaca espacioB = new UserEspacioButaca(dimButaca);
                espacioB.setButaca(butaca);
                espacioB.refreshStatus();
                espacioButacaList.add(espacioB);
                apReserva.getChildren().add(espacioB);
            });
        } else {
            System.out.println("No se ha aplicado la distribucion porque dimButaca <= 0");
        }
    }
    
    private void cargarListaButacas(Long salaID){
        Respuesta resp = butacaService.getListaButacasSala(salaID);
        if(resp.getEstado()){
            this.butacaList = ((ArrayList<ButacaDto>) resp.getResultado("ButacaList"));
        } else {
            System.out.println("Error cargando la lista de butacas.");
        }
    }

    @FXML
    private void reservar(ActionEvent event) {
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
    }
    
}
