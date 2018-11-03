/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.CineDto;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chris
 */
public class CineService {
    
    public Respuesta getCine(){
        try {
            Request request = new Request("CineController/cine");
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            CineDto cineDto = (CineDto) request.readEntity(CineDto.class);
            return new Respuesta(true, "","", "Cine", cineDto);
        } catch (Exception ex) {
            Logger.getLogger(CineService.class.getName()).log(Level.SEVERE, "Error cargando el cine.", ex);
            return new Respuesta(false, "Error cargando el cine.", "getCine " + ex.getMessage());
        }
    }
    
    public Respuesta guardarCine(CineDto dto){
        try{
            Request request = new Request("CineController/cine");
            request.post(dto);
            if(request.isError()){
                return new Respuesta(false, "Se ha producido un error guardando el cine.", request.getError());
            }
            CineDto cine = (CineDto) request.readEntity(CineDto.class);
            return new Respuesta(true, "", "", "Cine", cine);
        } catch (Exception ex) {
            Logger.getLogger(CineService.class.getName()).log(Level.SEVERE, "Error guardando el cine.", ex);
            return new Respuesta(false, "Error guardando el cine.", "guardarCine " + ex.getMessage());
        }
    }
    
}
