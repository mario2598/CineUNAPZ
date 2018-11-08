/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.CampoButaca;
import cineuna.cards.EspacioParaButaca;
import cineuna.model.ButacaDto;
import cineuna.model.TandaDto;
import cineuna.util.AppContext;
import cineuna.util.LangUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuSeleccionTandaController extends Controller implements Initializable {

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
    TandaDto tanda;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPantalla;
    private ArrayList<CampoButaca> butacaList;
    Integer filas;
    Integer columnas;
    private Boolean butacasDistribuidas;
    @FXML
    private BorderPane bpButacas;
    @FXML
    private HBox hbCont;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        butacasDistribuidas=false;
        butacaList = new ArrayList<>();
        redimensionado();
        cargarIdioma();
        cargarInfoTanda();
        cargarDistribucion();
        cargarButacas();
        
    }    

    @Override
    public void initialize() {
        cargarIdioma();
        cargarInfoTanda();
        cargarDistribucion();
        cargarButacas();
    }
    
    private void redimensionado(){
        lblPantalla.widthProperty().addListener((observable, oldValue, newValue)->{
            if(!butacaList.isEmpty() && newValue!=null){
                Double anchura = bpButacas.getWidth()*0.82;
                Integer dimButaca = ((anchura.intValue())/columnas);
                butacaList.stream().forEach(butaca -> {
                    butaca.cambiarDimension(dimButaca);
                });
            }
        });
        hbCont.prefWidthProperty().bind(root.widthProperty());
        hbCont.prefHeightProperty().bind(root.heightProperty());
        bpButacas.prefHeightProperty().bind(hbCont.heightProperty());
        bpButacas.prefWidthProperty().bind(hbCont.widthProperty());
    }
    
    private void cargarIdioma(){
        Integer idioma=Integer.valueOf(AppContext.getInstance().getUsuario().usuIdioma.getValue());
        if(idioma.equals(1))
            LangUtils.getInstance().setLang("es");
        else
            LangUtils.getInstance().setLang("eng");
        
        LangUtils.getInstance().loadLabelLang(lblMsjAsientos, "lblMsjAsientos");
        LangUtils.getInstance().loadLabelLang(lblMsjCosto, "lblMsjCosto");
        LangUtils.getInstance().loadButtonLang(btnReservar, "btnReservar");
        LangUtils.getInstance().loadButtonLang(btnCancelar, "btnCancelar");
        LangUtils.getInstance().loadLabelLang(lblPantalla, "lblPantalla");
    }
    
    private void cargarInfoTanda(){
        try{
            this.tanda=(TandaDto) AppContext.getInstance().get("tandaSeleccionada");
            this.lblSala.setText(tanda.getSalaId().getSalaNombre());
            this.lblCosto.setText(tanda.getTandaCobro().toString());
            System.out.println("Tanda sala: "+tanda.getSalaId().getSalaNombre());
        }
        catch(NullPointerException e){
            System.out.println("Ninguna tanda seleccionada");
        }
        
        //this.lblCine.setText(tanda.getC);
    }
    
    private void cargarDistribucion(){
        try{
        filas=tanda.getSalaId().getSalaFilas().intValue();
        columnas=tanda.getSalaId().getSalaCol().intValue();
        apReserva.setPrefColumns(columnas);
        apReserva.setPrefRows(filas);
        apReserva.getChildren().clear();
            //System.out.println("filas: "+filas+"\ncolumnas: "+columnas);
        }
        catch(NullPointerException e){
            System.out.println("Ocurrió un error al cargar filas y columnas");
        }
    }
    
    private void cargarButacas(){
        butacaList.clear();
            Double anchura = bpButacas.getWidth()*0.82;
            Integer dimButaca = ((anchura.intValue())/columnas);
//            System.out.println("Dimension de la butaca: " + dimButaca);
            if(dimButaca>0){
                for(int i = 0; i < this.filas; i++){
                    for (int j = 0; j < this.columnas; j++) {
                        CampoButaca espacioB = new CampoButaca(dimButaca);
                        ButacaDto butaca = new ButacaDto();
                        butaca.setButFila(new Long(i));
                        butaca.setButColumna(new Long(j));
                        butaca.setButLetra(getLetraFila(i) + String.valueOf(j+1));
                        butaca.setButActiva("A");
                        butaca.setButEstado("D");
                        espacioB.setButaca(butaca);
                        butacaList.add(espacioB);
                        apReserva.getChildren().add(espacioB);
                    }
                }
                this.butacasDistribuidas = true;
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
}
