/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.UsuarioDto;
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
 * @author mario
 */
public class UsuarioService {
     public Respuesta guardarUsuario(UsuarioDto usu) {
        try {
            Request request = new Request("UsuarioController/usuario");
            request.post(usu);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            UsuarioDto usuDto = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", usuDto);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            return new Respuesta(false, "Error guardando el usuario.", "guardarUsuario " + ex.getMessage());
        }
    }
     
    public Respuesta getUsuario(String usuario,String clave) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuario", usuario);
            parametros.put("clave", clave);
            Request request = new Request("UsuarioController/usuario", "/{usuario}/{clave}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto usuDto = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", usuDto);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error cargando el usuario.", ex);
            return new Respuesta(false, "Error cargando el usuario.", "guardarUsuario " + ex.getMessage());
        }
    }
    public Respuesta getUsuarioUsu(String user) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("user", user);
            Request request = new Request("UsuarioController/usuario", "/{user}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            UsuarioDto usuDto = (UsuarioDto) request.readEntity(UsuarioDto.class);
            return new Respuesta(true, "", "", "Usuario", usuDto);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error cargando el usuario.", ex);
            return new Respuesta(false, "Error cargando el usuario.", "guardarUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta getListaUsuarios(){
        try{
            Request request = new Request("UsuarioController/usuarios");
            request.get();
            if(request.isError()){
                return new Respuesta(false, request.getError(), "");
            }
            ArrayList<UsuarioDto> salaList = (ArrayList<UsuarioDto>) request.readEntity(new GenericType<ArrayList<UsuarioDto>>() {
            });
            return new Respuesta(true, "", "", "UsuarioList", salaList);
        } catch (Exception ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, "Error cargando la lista completa de usuarios.", ex);
            return new Respuesta(false, "Error cargando la lista completa de usuarios.", "getListaUsuarios Exception: " + ex.getMessage());
        }
    }
    
}
