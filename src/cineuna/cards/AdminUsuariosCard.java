/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.cards;

import cineuna.model.UsuarioDto;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Chris
 */
public class AdminUsuariosCard extends Card{

    private UsuarioDto usuario;
    
    @Override
    public void initCard() {
        if(!this.isInitialized()){
            this.setPrefSize(280, 70);
            this.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
            this.getChildren().add(initNameLbl());
            this.setInitialized(true);
        }
    }
    
    private Label initNameLbl(){
        Label lbl = new Label();
        lbl.setPrefSize(280, 70);
        lbl.setStyle("-fx-text-fill: #bcbcbc; -fx-font-size: 20px;");
        lbl.setText(usuario.getUsuNombre() + " " + usuario.getUsuPapellido());
        lbl.setAlignment(Pos.CENTER);
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setWrapText(true);
        return lbl;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }
    
}
