/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.ReservaDto;
import cineuna.util.DateUtil;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.time.LocalDate;
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
    
    public Respuesta getListReservas(Long tandaId, LocalDate date){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tandaId", tandaId);
            parametros.put("date", DateUtil.LocalDate2String(date));
            Request request = new Request("reservaController/getReservas","/{tandaId}/{date}",parametros);
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
    
    public Respuesta guardarReserva(ReservaDto dto){
        try {
            Request request = new Request("reservaController/reserva");
            request.post(dto);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            ReservaDto resDto = (ReservaDto) request.readEntity(ReservaDto.class);
            return new Respuesta(true, "", "", "Reserva", resDto);
        } catch (Exception ex) {
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Error guardando la reserva.", ex);
            return new Respuesta(false, "Error guardando la reserva.", "guardarReserva " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarReserva(ReservaDto dto){
        try{
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("resId", dto.getResId());
            Request request = new Request("reservaController/eliminarReserva", "/{resId}", parametros);
            request.delete();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        }catch(Exception ex){
            Logger.getLogger(ButacaService.class.getName()).log(Level.SEVERE, "Se ha producido un error eliminando la reserva.", ex);
            return new Respuesta(false, "Se ha producido un error eliminando la reserva.", ex.getMessage());
        }
    }
    
}
