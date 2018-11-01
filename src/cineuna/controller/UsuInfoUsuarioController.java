/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.UsuarioDto;
import cineuna.service.UsuarioService;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class UsuInfoUsuarioController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    @FXML
    private JFXPasswordField txtContrasenna1;
    @FXML
    private JFXPasswordField txtContrasenna2;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblPApellido;
    @FXML
    private Label lblSApellido;
    @FXML
    private VBox vbInfo;
    @FXML
    private VBox vbEditar;
    private UsuarioDto usuario;
    private UsuarioDto usuarioCopia;
    @FXML
    private ImageView imgUsuarioLbl;
    @FXML
    private ImageView imgUsuarioTxt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindUsuarioLbls();
    }    

    @Override
    public void initialize() {
        bindUsuarioLbls();
    }

    private void llenarInfoUsuario(){
        lblUsuario.setText("Desconocido");
        lblNombre.setText("Desconocido");
        lblPApellido.setText("Desconocido");
        lblSApellido.setText("Desconocido");
    }
    
    private void volver(MouseEvent event) {
        //String pantallaPrevia = (String) AppContext.getInstance().get("pantallaPrevia");
        FlowController.getInstance().goView("UsuCines");
    }

    private void bindUsuarioLbls(){
        vbEditar.setVisible(false);
        vbInfo.setVisible(true);
        unbindLbls();
        bindLbls();
        
    }
    
    private void bindLbls(){
       try{
       usuario=AppContext.getInstance().getUsuario();
       lblNombre.textProperty().bind(usuario.usuNombre);
       lblUsuario.textProperty().bind(usuario.usuUser);
       lblPApellido.textProperty().bind(usuario.usuPapellido);
       lblSApellido.textProperty().bind(usuario.usuSapellido);
       }
        catch(NullPointerException e){
            llenarInfoUsuario();
        }
    }
    
    private void unbindLbls(){
       lblNombre.textProperty().unbind();
       lblUsuario.textProperty().unbind();
       lblPApellido.textProperty().unbind();
       lblSApellido.textProperty().unbind();
    }
    
    private void bindUsuarioTf(){
        unbinddsTf();
        bindsTf();
    }
    
    private void bindsTf(){
        try{
        usuario=AppContext.getInstance().getUsuario();
        txtNombre.textProperty().bindBidirectional(usuario.usuNombre);
        txtUsuario.textProperty().bindBidirectional(usuario.usuUser);
        txtPApellido.textProperty().bindBidirectional(usuario.usuPapellido);
        txtSApellido.textProperty().bindBidirectional(usuario.usuSapellido);
        }
        catch(NullPointerException e){
            llenarInfoUsuario();
        }
    }
    
    private void unbinddsTf(){
       lblNombre.textProperty().unbindBidirectional(usuario.usuNombre);
       lblUsuario.textProperty().unbindBidirectional(usuario.usuUser);
       lblPApellido.textProperty().unbindBidirectional(usuario.usuPapellido);
       lblSApellido.textProperty().unbindBidirectional(usuario.usuSapellido);
    }
    
    private void cargarImagenUsuario(){
        
    }
    
    private Boolean validarContrasenna(){
        if(txtContrasenna1.getText().length()>=8){
            if(txtContrasenna1.getText().equals(txtContrasenna2.getText()))
                return true;
        }
        return false;
    }
    
    private void regresar(){
        bindUsuarioLbls();
    }
    
    @FXML
    private void confirmarCambios(ActionEvent event) {
        vbEditar.setVisible(false);
        vbInfo.setVisible(true);
        UsuarioService uService = new UsuarioService();
        try{
        uService.guardarUsuario(usuario);
        
        }
        catch(Exception e){
            
        }
    }

    @FXML
    private void editarInfo(ActionEvent event) {
        vbEditar.setVisible(true);
        vbInfo.setVisible(false);
        bindUsuarioTf();
    }

    @FXML
    private void cancelarCambios(ActionEvent event) {
        bindUsuarioLbls();
    }
    
}
