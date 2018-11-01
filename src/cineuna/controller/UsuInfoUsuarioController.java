/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        llenarInfoUsuario();
    }

    private void llenarInfoUsuario(){
        lblUsuario.setText("Robrigo1504");
        lblNombre.setText("Rodrigo");
        lblPApellido.setText("Vargas");
        lblSApellido.setText("Abarca");
    }
    
    private void volver(MouseEvent event) {
        //String pantallaPrevia = (String) AppContext.getInstance().get("pantallaPrevia");
        FlowController.getInstance().goView("UsuCines");
    }

    @FXML
    private void confirmarCambios(ActionEvent event) {
    }

    @FXML
    private void editarInfo(ActionEvent event) {
    }
    
}
