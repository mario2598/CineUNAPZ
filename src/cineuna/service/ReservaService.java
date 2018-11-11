/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.ReservaDto;
import cineuna.model.TandaDto;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author robri
 */
public class ReservaService {
    public Respuesta getListReservas(Long tandaId){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tandaId", tandaId);
            Request request = new Request("reservaController/getReservas","/{tandaId}",parametros);
            request.get();

            if (request.isError()) {
                System.out.println("error TandaService(Cliente)"+request.getError());
                return new Respuesta(false, request.getError(), "");
            }
            ArrayList<ReservaDto> reservaListT = (ArrayList<ReservaDto>) request.readEntity(new GenericType<ArrayList<ReservaDto>>() {
            });
            
            return new Respuesta(true, "", "", "ReservasList", reservaListT);
        } catch (Exception ex) {
            Logger.getLogger(TandaService.class.getName()).log(Level.SEVERE, "Error obteniendo lita tandas(TS Cliente)", ex);
            return new Respuesta(false, "Error obteniendo tandasM(Service Cliente).", "getTandasS " + ex.getMessage());
        }
    }
}
