/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.cards.AdminEspacioButaca;
import cineuna.model.ButacaDto;
import cineuna.model.SalaDto;
import cineuna.service.ButacaService;
import cineuna.util.AppContext;
import cineuna.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminVerDistribucionButacasController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private BorderPane bpButacas;
    @FXML
    private Label lblPantalla;
    @FXML
    private TilePane tpButacas;
    
    //Attributes
    private final ButacaService butacaService = new ButacaService();
    private final ArrayList<AdminEspacioButaca> espacioButacaList= new ArrayList<>();
    private ArrayList<ButacaDto> butacaList = new ArrayList<>();
    private Integer columnas;
    private Integer filas;
    private SalaDto sala;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Nothing TODO
    }    

    @Override
    public void initialize() {
        this.sala = ((SalaDto) AppContext.getInstance().get("AdminShowingSalaDistribution"));
        if(sala!=null){
            cargarButacas();
        }
        espacioButacaList.clear();
        butacaList.clear();
    }
    
    private void aplicarDistribucion() {
        columnas = sala.getSalaCol().intValue();
        filas = sala.getSalaFilas().intValue();
        tpButacas.setPrefColumns(columnas);
        tpButacas.setPrefRows(filas);
        tpButacas.getChildren().clear();
        espacioButacaList.clear();
        Integer dimButaca;
        Integer anchura = 735/columnas;
        Integer altura = 335/filas;
        if(anchura > altura)
            dimButaca = altura;
        else
            dimButaca = anchura;
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
            AdminEspacioButaca espacioB = new AdminEspacioButaca(dimButaca);
            espacioB.setButaca(butaca);
            espacioB.refreshStatus();
            espacioB.setEditable(false);
            espacioButacaList.add(espacioB);
            tpButacas.getChildren().add(espacioB);
        });
    }
    
    public void cargarButacas(){
        try{
            Respuesta resp = butacaService.getListaButacasSala(sala.getSalaId());
            if(resp.getEstado()){
                this.butacaList = ((ArrayList<ButacaDto>) resp.getResultado("ButacaList"));
                aplicarDistribucion();
            }
        } catch(Exception ex){
            System.out.println("Error cargando la lista de butacas en la view \"VerDistribucionButacas\"");
        }
    }
    
}
