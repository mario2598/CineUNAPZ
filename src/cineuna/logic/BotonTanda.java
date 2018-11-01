/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.logic;

import cineuna.util.AppContext;
import cineuna.util.FlowController;
import com.jfoenix.controls.JFXButton;

/**
 *
 * @author robri
 */
public class BotonTanda extends JFXButton{

    //private tandaDto tanda;
    
    
    public BotonTanda(String text) {
        super(text);
        asignarAccion();
    }

    public BotonTanda() {
        asignarAccion();
    }
    /*public BotonTanda(tandaDto tanda) {
    this.tanda = tanda;
    }*/
    
    private void asignarAccion(){
        this.setOnAction(e->{
            //AppContext.getInstance().set("tandaSeleccionada",tanda);
            FlowController.getInstance().goView("UsuSeleccionTanda");
        });
    }
    
    
    
}
