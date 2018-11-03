/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.SalaDto;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Chris
 */
public class SalaService {
    
    public Respuesta getSala(Long id){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("salaID", id);
            Request request = new Request("SalaController/sala", "/{salaID}", parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            SalaDto sala = (SalaDto) request.readEntity(SalaDto.class);
            return new Respuesta(true, "", "", "Sala", sala);
        } catch (Exception ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, "Error cargando la sala.", ex);
            return new Respuesta(false, "Error cargando la sala.", "getSala Exception: " + ex.getMessage());
        }
    }
    
    public Respuesta getListaSalas(){
        try{
            Request request = new Request("SalaController/salas");
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            ArrayList<SalaDto> salaList = (ArrayList<SalaDto>) request.readEntity(new GenericType<ArrayList<SalaDto>>() {
            });
            return new Respuesta(true, "", "", "SalaList", salaList);
        } catch (Exception ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, "Error cargando la lista completa de sala.", ex);
            return new Respuesta(false, "Error cargando la lista completa de sala.", "getSala Exception: " + ex.getMessage());
        }
    }
    
    public Respuesta guardarSala(SalaDto dto){
        try {
            Request request = new Request("SalaController/sala");
            request.post(dto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            SalaDto salaDto = (SalaDto) request.readEntity(SalaDto.class);
            return new Respuesta(true, "", "", "Sala", salaDto);
        } catch (Exception ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, "Error guardando la sala.", ex);
            return new Respuesta(false, "Error guardando la sala.", "guardarSala " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarSala(SalaDto dto){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("salaID", dto.getSalaId());
            Request request = new Request("SalaController/sala", "/{salaID}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        }catch(Exception ex){
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, "Se ha producido un error eliminando la sala.", ex);
            return new Respuesta(false, "Se ha producido un error eliminando la sala.", ex.getMessage());
        }
    }
    
}
