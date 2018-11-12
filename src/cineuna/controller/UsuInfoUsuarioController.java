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
import cineuna.util.LangUtils;
import cineuna.util.Mensaje;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

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
    @FXML
    private JFXButton btnEditar;
    @FXML
    private JFXButton btnConfirmar;
    @FXML
    private JFXButton btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindUsuarioLbls();
    }    

    @Override
    public void initialize() {
        try {
            cargarIdioma();
            bindUsuarioLbls();
            cargarImagen();
        } catch (IOException ex) {
            Logger.getLogger(UsuInfoUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if(validaCampos()){
            vbEditar.setVisible(false);
            vbInfo.setVisible(true);
            UsuarioService uService = new UsuarioService();
            try{
                uService.guardarUsuario(usuario);
            }
            catch(Exception e){
                System.out.println("problema guardando usuario:"+e.getMessage());
            }
        }
        else{
            Mensaje alert = new Mensaje();
            alert.show(Alert.AlertType.INFORMATION, "estado de actualización", "error");
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
    
    private void cargarIdioma(){
        Integer idioma=Integer.valueOf(AppContext.getInstance().getUsuario().usuIdioma.getValue());
        if(idioma.equals(1))
            LangUtils.getInstance().setLang("es");
        else
            LangUtils.getInstance().setLang("eng");
        
        LangUtils.getInstance().loadTextFieldLang(txtNombre,"txtNombre");
        LangUtils.getInstance().loadTextFieldLang(txtPApellido,"txtPApellido");
        LangUtils.getInstance().loadTextFieldLang(txtSApellido,"txtSApellido");
        LangUtils.getInstance().loadTextFieldLang(txtUsuario,"txtUsuario");
        LangUtils.getInstance().loadButtonLang(btnCancelar,"btnCancelar");
        LangUtils.getInstance().loadButtonLang(btnConfirmar,"btnConfirmar");
        LangUtils.getInstance().loadButtonLang(btnEditar,"btnEditar");
    }
    
    
    private void cargarImagen() throws IOException{
        String imgPath = "src\\cineuna\\resources\\images\\" + usuario.getUsuNombre()+".jpg";
        File file = new File(imgPath);
        if(usuario.getUsuImg()!=null){
        usuario.crearImagenDesdeByte();
        if(file.exists()){
            imgUsuarioLbl.setImage(usuario.abrirImagen());
            imgUsuarioTxt.setImage(usuario.abrirImagen());
        }
        else{
           imgUsuarioLbl.setImage(new Image("cineuna/resources/images/VenomPoster.jpg")); 
           imgUsuarioTxt.setImage(new Image("cineuna/resources/images/VenomPoster.jpg"));
        }
        }
        else{
            imgUsuarioLbl.setImage(new Image("cineuna/resources/images/VenomPoster.jpg")); 
            imgUsuarioTxt.setImage(new Image("cineuna/resources/images/VenomPoster.jpg"));
        } 
    }

    @FXML
    private void buscarImagen(MouseEvent event) throws FileNotFoundException {
        FileChooser fc=new FileChooser();
        File sel = fc.showOpenDialog(null);
        if(fc!=null){
            usuario.guardarImagenByte(sel);
            imgUsuarioTxt.setImage(new Image(sel.toURI().toString()));
          
        }
        else{
            System.out.println("imagen obtenida desde windows vacía");
        }
    }
    
    private Boolean validaCampos(){
        Boolean completo = true;
        String msj="";
        if(this.txtNombre.getText().isEmpty()){
            completo = false;
            msj+="nombre,";
        }
        if(this.txtUsuario.getText().isEmpty()){
            completo = false;
            msj+="usuario,";
        }
        if(this.txtPApellido.getText().isEmpty()){
            completo = false;
            msj+="primer apellido,";
        }
        if(this.txtSApellido.getText().isEmpty()){
            completo = false;
            msj+="segundo apellido,";
        }
        if(this.txtContrasenna1.getText().isEmpty()&&this.txtContrasenna1.getText().length()>8){
            completo = false;
            msj+="contraseña,";
        }
        else{
           if(this.txtContrasenna2.getText().isEmpty()){
            completo = false;
            msj+="repetir contraseña,";
            }
           else if(!this.txtContrasenna2.getText().equals(this.txtContrasenna1.getText())){
              completo = false;
              msj+="contraseñas no coinciden,";
           }
        Mensaje alert = new Mensaje();
        alert.showModal(Alert.AlertType.INFORMATION, "estado de actualización", null, msj);
        }
    return completo;    
    }
    
}
