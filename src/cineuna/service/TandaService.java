/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.MovieDto;
import cineuna.model.TandaDto;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author robri
 */
public class TandaService {
    /**
     * obtiene todas las películas
     * @return 
     */
    
    public Respuesta getTandasM(Long id){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("tandaController/getTandaListM","/{id}",parametros);
            request.get();

            if (request.isError()) {
                System.out.println("error TandaService(Cliente)"+request.getError());
                return new Respuesta(false, request.getError(), "");
            }
            List<TandaDto> tandasM = (List<TandaDto>) request.readEntity(new GenericType<List<TandaDto>>() {
            });
            
            return new Respuesta(true, "", "", "tandasM", tandasM);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo lita tandas(TS Cliente", ex);
            return new Respuesta(false, "Error obteniendo tandasM(Service Cliente).", "getTandasM " + ex.getMessage());
        }
    }
}