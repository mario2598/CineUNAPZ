/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.service;

import cineuna.model.MovieDto;
import cineuna.util.Request;
import cineuna.util.Respuesta;
import java.time.LocalDate;
import java.util.Date;
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
    
<<<<<<< HEAD
    public Respuesta getReport(LocalDate f1,LocalDate f2){
=======
        public Respuesta getReport(String f1,String f2){
>>>>>>> master5_mario/7/11
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("date1", f1);
            parametros.put("date2", f2);
            Request request = new Request("movieController/moviesReport", "/{date1}/{date2}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }    
            byte[] b = (byte[]) request.readEntity(new GenericType<byte[]>() {});
            return new Respuesta(true, "", "", "reporte",b);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error con comprobantes.", ex);
            return new Respuesta(false, "Error obteniendo películas(Service Cliente).", "getMovies " + ex.getMessage());
        }
    }
        
     public Respuesta getReport(Long id){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("movieController/moviesReport", "/{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }    
            byte[] b = (byte[]) request.readEntity(new GenericType<byte[]>() {});
            return new Respuesta(true, "", "", "reporte",b);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error con comprobantes.", ex);
            return new Respuesta(false, "Error obteniendo películas(Service Cliente).", "getMovies " + ex.getMessage());
        }
    }
    
    public Respuesta getMovies(String estado){
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("estado", estado);
            Request request = new Request("movieController/getMovieList","/{estado}",parametros);
            request.get();

            if (request.isError()) {
                //System.out.println("error");
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
    
    public Respuesta guardarMovie(MovieDto dto){
        try{
            Request request = new Request("movieController/guardarMovie");
            request.post(dto);
            if(request.isError()){
                return new Respuesta(false, "Se ha producido un error guardando el cine.", request.getError());
            }
            MovieDto Movie = (MovieDto) request.readEntity(MovieDto.class);
            return new Respuesta(true, "", "", "Movie", Movie);
        } catch (Exception ex) {
            Logger.getLogger(CineService.class.getName()).log(Level.SEVERE, "Error guardando Movie.", ex);
            return new Respuesta(false, "Error guardando Movie.", "guardarCine " + ex.getMessage());
        }
    }
    
}
