/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.ButacaDto;
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
public class ButacaService {
    
    public Respuesta getButaca(Long id){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("butacaID", id);
            Request request = new Request("ButacaController/butaca", "/{butacaID}", parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            ButacaDto butacaDto = (ButacaDto) request.readEntity(ButacaDto.class);
            return new Respuesta(true, "", "", "Butaca", butacaDto);
        } catch (Exception ex) {
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Error cargando la butaca.", ex);
            return new Respuesta(false, "Error cargando la butaca.", "getButaca Exception: " + ex.getMessage());
        }
    }
    
    public Respuesta getListaButacas(){
        try{
            Request request = new Request("ButacaController/butacas");
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            ArrayList<ButacaDto> salaList = (ArrayList<ButacaDto>) request.readEntity(new GenericType<ArrayList<ButacaDto>>() {
            });
            return new Respuesta(true, "", "", "ButacaList", salaList);
        } catch (Exception ex) {
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Error cargando lista de butacas.", ex);
            return new Respuesta(false, "Error cargando lista de butacas.", "getListaButacas Exception: " + ex.getMessage());
        }
    }
    
    public Respuesta guardarButaca(ButacaDto dto){
        try {
            Request request = new Request("ButacaController/butaca");
            request.post(dto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            ButacaDto salaDto = (ButacaDto) request.readEntity(ButacaDto.class);
            return new Respuesta(true, "", "", "Butaca", salaDto);
        } catch (Exception ex) {
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Error guardando la butaca.", ex);
            return new Respuesta(false, "Error guardando la sala.", "guardarButaca " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarButaca(ButacaDto dto){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("butacaID", dto.getButId());
            Request request = new Request("ButacaController/butaca", "/{butacaID}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        }catch(Exception ex){
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Se ha producido un error eliminando la butaca.", ex);
            return new Respuesta(false, "Se ha producido un error eliminando la butaca.", ex.getMessage());
        }
    }
    
    public Respuesta eliminarListaButacas(Long salaID){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("salaID", salaID);
            Request request = new Request("ButacaController/butacas", "/{salaID}", parametros);
            request.delete(); 
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        }catch(Exception ex){
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Se ha producido un error eliminando la lista butacas.", ex);
            return new Respuesta(false, "Se ha producido un error eliminando la lista de butacas.", ex.getMessage());
        }
    }
    
    public Respuesta getListaButacasSala(Long salaID){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("salaID", salaID);
            Request request = new Request("ButacaController/butacas","/{salaID}",parametros);
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            ArrayList<ButacaDto> salaList = (ArrayList<ButacaDto>) request.readEntity(new GenericType<ArrayList<ButacaDto>>() {
            });
            return new Respuesta(true, "", "", "ButacaList", salaList);
        } catch (Exception ex) {
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Error cargando lista de butacas.", ex);
            return new Respuesta(false, "Error cargando lista de butacas.", "getListaButacas Exception: " + ex.getMessage());
        }
    }
    
}
