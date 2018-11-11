/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.controller;

import cineuna.model.UsuarioDto;
import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robri
 */
public class ContainerController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    private Boolean isAdmin;
    private JFXButton b;
    @FXML
    private FontAwesomeIconView btnOpcionesUsu;
    @FXML
    private Hyperlink hlNombreUsuario;
    private JFXPopup popUp;
    @FXML
    private AnchorPane apTop;
    private VBox vbOpcionesUsu;
    @FXML
    private StackPane centerHP;
    private UsuarioDto usuario;
    @FXML
    private StackPane dialogsPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initContainers();
        isAdmin =(Boolean)(AppContext.getInstance().get("administrador"));
        llenarInfoUsuario();
        bindUsuario();
    }    

    @Override
    public void initialize() {
        isAdmin =(Boolean)(AppContext.getInstance().get("administrador"));
        llenarInfoUsuario();
        bindUsuario();
    }

    
    /**
     * llena el hyperlink del nombre de usuario y asigna el popup
     */
    public void llenarInfoUsuario(){
        //this.hlNombreUsuario.setText("usuario");
        this.hlNombreUsuario.setOnAction(e->{
            //System.out.println("asdasdasd");
            FlowController.getInstance().goView("UsuInfoUsuario");
        });
        this.btnOpcionesUsu.setOnMouseClicked(event ->{
            this.popUp.show(btnOpcionesUsu,JFXPopup.PopupVPosition.TOP,JFXPopup.PopupHPosition.LEFT,event.getX(),event.getY());
        });
        llenarOpcionesUsu();
        this.popUp.setPopupContent(this.vbOpcionesUsu);
    }
    
    /**
     * llena el vbox de las opciones del perfil de usuario
     */
    public void llenarOpcionesUsu(){
        usuario=AppContext.getInstance().getUsuario();
        Integer idioma=Integer.valueOf(usuario.usuIdioma.getValue());
        
        for (int i = 0; i < 10; i++) {
            Hyperlink hl;
            switch (i) {
                case 1:
                    hl= new Hyperlink();
                    if(idioma.equals(1))
                    hl.setText("Cerrar Sesión");
                    else
                       hl.setText("Log out"); 
                    hl.setOnAction(e->{
                        ((Stage) root.getScene().getWindow()).close();
                        FlowController.getInstance().goViewInWindow("LogIn");
                    });
                    this.vbOpcionesUsu.getChildren().add(hl);
                    break;
                case 2:
                    hl= new Hyperlink();
                    if(idioma.equals(1))
                    hl.setText("Información cine");
                    else
                       hl.setText("About us"); 
                    hl.setOnAction(e->{
                        FlowController.getInstance().goView("UsuInfoCine");
                    });
                    this.vbOpcionesUsu.getChildren().add(hl);
                    break;
                default:
                    break;
            }
        }
    }
    
    public void initContainers(){
        this.vbOpcionesUsu=new VBox();
        this.popUp= new JFXPopup();
    }
    
    private void bindUsuario(){
        unbinds();
        binds();
    }
    
    private void binds(){
        hlNombreUsuario.textProperty().bind(AppContext.getInstance().getUsuario().usuUser);
    }
    
    private void unbinds(){
        hlNombreUsuario.textProperty().unbind();
    }
    
    public StackPane getHolderPane(){
        return this.centerHP;
    }
    
    public void seleccionarUsuario(){
        FlowController.getInstance().goView("UsuInicio");
    }

    private void irInicio(MouseEvent event) {
        FlowController.getInstance().goView("UsuInicio");
    }
    
    private void btnVolverAction(ActionEvent event) {
        FlowController.getInstance().goView("AdminMenu");
    }
    
    public StackPane getDialogsPane(){
        return this.dialogsPane;

    }

    @FXML
    private void volverInicio(MouseEvent event) {
        if(isAdmin)
            FlowController.getInstance().goView("AdminMenu");
        else
            FlowController.getInstance().goView("UsuInicio");
            
    }
}
