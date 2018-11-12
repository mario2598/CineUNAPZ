/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.UsuarioDto;
import cineuna.service.UsuarioService;
import cineuna.util.AppContext;
import cineuna.util.Respuesta;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdminManteUsuarioController extends Controller implements Initializable {
    //FXML Attributes
    @FXML
    private Label lblNombre, lblPapeliido, lblSapellido, lblEmail, lblDerechos;
    @FXML
    private JFXTextField txtNombre, txtPapellido, txtSapellido, txtEmail;
    @FXML
    private HBox hbPicture;
    @FXML
    private JFXButton btnEliminar, btnGuardar;
    @FXML
    private ImageView ivPicture;
    @FXML
    private JFXToggleButton toggleBtnDerechos;

    //Attributes
    private final UsuarioService usuarioService = new UsuarioService();
    private final SimpleBooleanProperty hayCambios = new SimpleBooleanProperty();
    private UsuarioDto usuario;
    private Boolean bindedUser;
    private String mensajeError;
    
    //Initializers
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindedUser = false;
        hayCambios.set(false);
        ivPicture.setOnMouseClicked(event -> {
            try {
                if(event.getButton().equals(MouseButton.PRIMARY))
                    cambiarImagenPerfil();
                event.consume();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AdminManteUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        toggleBtnDerechos.selectedProperty().addListener((observable, oldValue, newValue) -> {
            usuario.setUsuAdmin(newValue ? "S":"N");
        });
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!hayCambios.get())
                hayCambios.set(true);
        });
        txtPapellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!hayCambios.get())
                hayCambios.set(true);
        });
        txtSapellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!hayCambios.get())
                hayCambios.set(true);
        });
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!hayCambios.get())
                hayCambios.set(true);
        });
        toggleBtnDerechos.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(!hayCambios.get())
                hayCambios.set(true);
        });
        hayCambios.addListener((observable, oldValue, newValue) -> btnGuardar.setDisable(!newValue));
    }

    @Override
    public void initialize() {
        btnGuardar.setDisable(true);
        if(bindedUser)
            unbindUsuario();
        clearData();
        UsuarioDto usuarioOriginal = (UsuarioDto) AppContext.getInstance().get("AdminShowingUser");
        usuario = new UsuarioDto(usuarioOriginal);
        if(usuario!=null){
            bindUsuario();
            loadData();
        }
        hayCambios.set(false);
    }

    //Methods
    private void bindUsuario(){
        txtNombre.textProperty().bindBidirectional(usuario.usuNombre);
        txtPapellido.textProperty().bindBidirectional(usuario.usuPapellido);
        txtSapellido.textProperty().bindBidirectional(usuario.usuSapellido);
        txtEmail.textProperty().bindBidirectional(usuario.usuEmail);
        bindedUser = true;
    }
    
    private void unbindUsuario(){
        txtNombre.textProperty().unbindBidirectional(usuario.usuNombre);
        txtPapellido.textProperty().unbindBidirectional(usuario.usuPapellido);
        txtSapellido.textProperty().unbindBidirectional(usuario.usuSapellido);
        txtEmail.textProperty().unbindBidirectional(usuario.usuEmail);
        bindedUser = false;
    }
    
    private void loadData(){
        toggleBtnDerechos.setSelected(usuario.getUsuAdmin().equalsIgnoreCase("S"));
        //TODO load profil picture
    }
    
    private void clearData(){
        toggleBtnDerechos.setSelected(false);
        //TODO clean profil picture
    }
    
    private Boolean validadDatosNecesarios(){
        mensajeError = "Han faltado los siguientes datos por rellenar:";
        Boolean huboError = false;
        if(txtNombre.getText().isEmpty()){
            huboError = true;
            mensajeError += "\n\tCampo del nombre vacío";
        }
        if(txtPapellido.getText().isEmpty()){
            huboError = true;
            mensajeError += "\n\tCampo del nombre vacío";
        }
        if(txtSapellido.getText().isEmpty()){
            huboError = true;
            mensajeError += "\n\tCampo del nombre vacío";
        }
        if(txtEmail.getText().isEmpty()){
            huboError = true;
            mensajeError += "\n\tCampo del nombre vacío";
        }
        if(!huboError)
            mensajeError = null;
        return !huboError;
    }
    
    private void cambiarImagenPerfil() throws FileNotFoundException{
        System.out.println("File Chooser para imagen de perfil del usuario");
        FileChooser fc=new FileChooser();
        File sel = fc.showOpenDialog(null);
        if(fc!=null){
            usuario.guardarImagenByte(sel);
            ivPicture.setImage(new Image(sel.toURI().toString()));
          
        }
        else{
            System.out.println("imagen obtenida desde windows vacía");
        }
    }
    
    //FXML Methods
    @FXML
    private void btnEliminarAction(ActionEvent event) {
        //TODO (faltan metodos funcionales para eliminar usuario)
    }

    @FXML
    private void btnGuardarAction(ActionEvent event) {
        if(validadDatosNecesarios()){
            try{
                Respuesta resp = usuarioService.guardarUsuario(usuario);
                if(resp.getEstado()){
                    hayCambios.set(false);
                    ((SimpleBooleanProperty) AppContext.getInstance().get("UsuarioActualizadoProp")).set(true);
                    System.out.println("Se han actualizado los datos del usuario correctamente.");
                } else {
                    System.out.println("Se ha producido un error actualizando los datos del usuario.");
                }
            } catch(Exception ex){
                System.out.println("Se ha producido un error actualizando los datos del usuario.\nError: " + ex);
            }
        } else {
            System.out.println(mensajeError);
        }
    }
    

    @FXML
    private void buscaImg(MouseEvent event) throws FileNotFoundException {
        
    }
    
}
