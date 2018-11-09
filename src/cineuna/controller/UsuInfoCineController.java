/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.CineDto;
import cineuna.model.UsuarioDto;
import cineuna.service.CineService;
import cineuna.util.AppContext;
import cineuna.util.LangUtils;
import cineuna.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuInfoCineController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblNombreCine;
    @FXML
    private Hyperlink lblNumTel;
    @FXML
    private Hyperlink lblCorreo;
    @FXML
    private Label lblAbre;
    @FXML
    private Label lblCierra;
    @FXML
    private Label lblHorario;
    private UsuarioDto usuario;
    @FXML
    private Label lblMsjAbre;
    @FXML
    private Label lblMsjCierra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        cargarIdioma();
        llenaInfo();
    }
    
    private void llenaInfo(){
        CineService cs = new CineService();
        Respuesta res = cs.getCine();
        if(res.getEstado()){
            CineDto cine = (CineDto) res.getResultado("Cine");
            lblNombreCine.setText(cine.getCineNombre());
            lblCorreo.setText(cine.getCineEmail());
            lblNumTel.setText(cine.getCineTel().toString());
            lblAbre.setText(cine.getCineAbre().toString());
            lblCierra.setText(cine.getCineCierra().toString());
        }
        else{
            System.out.println("No se pudo obtener el cine para mostrar");
        }
        
    }
    
    private void cargarIdioma(){
        usuario=AppContext.getInstance().getUsuario();
        Integer idioma=Integer.valueOf(usuario.usuIdioma.getValue());
        if(idioma.equals(1)){
            LangUtils.getInstance().setLang("esp");
            //System.out.println("español-> "+idioma);
        }
        else{
            //System.out.println("inglés-> "+idioma);
            LangUtils.getInstance().setLang("eng");
        }
        
        LangUtils.getInstance().loadLabelLang(lblHorario, "lblHorario");
        LangUtils.getInstance().loadLabelLang(lblMsjAbre, "lblMsjAbre");
        LangUtils.getInstance().loadLabelLang(lblMsjCierra, "lblMsjCierra");
    }
}
