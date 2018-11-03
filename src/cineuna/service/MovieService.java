/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.MovieDto;
import cineuna.model.UsuarioDto;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author robri
 */
public class MovieService {
    
    /**
     * obtiene todas las películas
     * @return 
     */
    
    public Respuesta getMovies(){
        try {
            Request request = new Request("movieController/movies");
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<MovieDto> movies = (List<MovieDto>) request.readEntity(new GenericType<List<MovieDto>>() {
            });
            
            return new Respuesta(true, "", "", "Movies", movies);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            return new Respuesta(false, "Error obteniendo películas(Service Cliente).", "getMovies " + ex.getMessage());
        }
    }
}
