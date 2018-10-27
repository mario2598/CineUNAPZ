/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.util;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author eflores
 */
public final class Request {

    private final Client client;
    private Invocation.Builder builder;
    private WebTarget webTarget;
    private Response response;
    // public String urlBase = null;
    public String urlBase = "http://DESKTOP-RCLJD2G:80/WsCineUNA/wsCine/";
   //public String urlBase = "http://localhost:8080/ElectricPosWs/wsPos/";

    /**
     * Constructor por defecto
     */
    public Request() {
        /*if (Parametros.getInstance().cargarParametros()) {
            urlBase = Parametros.resturl;
        }*/
        this.client = ClientBuilder.newClient();
    }

    /**
     * Constructor con la inicialización del objetivo de la petición
     *
     * @param target Objetivo de la petición
     */
    public Request(String target) {

        /*if (Parametros.getInstance().cargarParametros()) {
            urlBase = Parametros.resturl;
        }*/
        this.client = ClientBuilder.newClient();
        setTarget(target);
    }

    /**
     * Constructor con la inicialización del objetivo de la petición y los
     * parámetros de url
     *
     * @param target Objetivo de la petición
     * @param parametros String con los nombres de los parámetros de URL
     * @param valores Mapa con los parámetros de URL
     */
    public Request(String target, String parametros, Map<String, Object> valores) {
        /*   if (Parametros.getInstance().cargarParametros()) {
            urlBase = Parametros.resturl;
        }*/
        this.client = ClientBuilder.newClient();

        this.webTarget = client.target(urlBase + target).path(parametros).resolveTemplates(valores);
        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        if (!headers.containsKey("Authorization")) {
            if (AppContext.getInstance().get("Token") != null) {
            //    headers.add("Authorization", "Bearer " + ((TokenDto) AppContext.getInstance().get("Token")).getToken());
            } else {
                headers.add("Authorization", "");
            }
        }
        builder.headers(headers);
    }

    /**
     * Constructor con la inicialización del objetivo de la petición y los
     * parámetros de query
     *
     * @param target Objetivo de la petición
     * @param queryParams Mapa con los parámetros de Query
     */
    public Request(String target, Map<String, Object> queryParams) {
        /* if (Parametros.getInstance().cargarParametros()) {
            urlBase = Parametros.resturl;
        }*/
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target(Parametros.resturl + target);

        queryParams.forEach((String t, Object u) -> {

            if (u instanceof List) {
                for (String string : (List<String>) u) {
                    this.webTarget = this.webTarget.queryParam(t, string);
                }

            } else {
                this.webTarget = this.webTarget.queryParam(t, u);
            }
        });

        this.webTarget = this.webTarget.queryParam("", "");

        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        if (!headers.containsKey("Authorization")) {
            if (AppContext.getInstance().get("Token") != null) {
              //  headers.add("Authorization", "Bearer " + ((TokenDto) AppContext.getInstance().get("Token")).getToken());
            } else {
                headers.add("Authorization", "");
            }
        }
        builder.headers(headers);
    }

    /**
     * Ingresa el objetivo de la petición
     *
     * @param target Objetivo de la petición
     */
    public void setTarget(String target) {
        /*  if (Parametros.getInstance().cargarParametros()) {
            urlBase = Parametros.resturl;
        }*/
        this.webTarget = client.target(urlBase + target);
        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        if (!headers.containsKey("Authorization")) {
            if (AppContext.getInstance().get("Token") != null) {
              //  headers.add("Authorization", "Bearer " + ((TokenDto) AppContext.getInstance().get("Token")).getToken());
            } else {
                headers.add("Authorization", "");
            }
        }
        builder.headers(headers);

    }

    /**
     * Ingresa un valor al encabezado de la petición
     *
     * @param nombre Nombre del parámetro
     * @param valor Valor del parámetro
     */
    public void setHeader(String nombre, Object valor) {
        builder.header(nombre, valor);
    }

    /**
     * Ingresa una lista de valores al encabezado de la petición
     *
     * @param valores Mapa de parámetros
     */
    public void setHeader(MultivaluedMap<String, Object> valores) {
        valores.add("Content-Type", "application/json; charset=UTF-8");
        if (!valores.containsKey("Authorization")) {
            if (AppContext.getInstance().get("Token") != null) {
              //  valores.putSingle("Authorization", "Bearer " + ((TokenDto) AppContext.getInstance().get("Token")).getToken());
            } else {
                valores.putSingle("Authorization", "");
            }
        }
        builder.headers(valores);
    }

    /**
     * Ejecuta la petición con el metodo get
     *
     */
    public void get() {
        response = builder.get();
    }

    /**
     * Ejecuta la petición con el metodo post
     *
     * @param clazz Entidad a enviar
     */
    public void post(Object clazz) {
        Entity<?> entity = Entity.entity(clazz, "application/json; charset=UTF-8");
        response = builder.post(entity);
    }

    /**
     * Ejecuta la petición con el metodo put
     *
     * @param clazz Entidad a enviar
     */
    public void put(Object clazz) {
        Entity<?> entity = Entity.entity(clazz, "application/json; charset=UTF-8");
        response = builder.put(entity);
    }

    /**
     * Ejecuta la petición con el metodo delete
     *
     */
    public void delete() {
        response = builder.delete();
    }

    public int getStatus() {
        return response.getStatus();
    }

    public Boolean isError() {
        return getStatus() != HttpServletResponse.SC_OK;
    }

    public String getError() {
        if (response.getStatus() != HttpServletResponse.SC_OK) {
            String mensaje;
            if (response.getMediaType().equals(MediaType.TEXT_PLAIN_TYPE)) {
                return new String(response.readEntity(String.class));
            } else if (response.getMediaType().equals(MediaType.APPLICATION_JSON_TYPE)) {
                mensaje = response.readEntity(String.class);
            } else {
                mensaje = (response.getStatusInfo().getReasonPhrase());
            }
            return mensaje;
        }
        return null;
    }

    public Object readEntity(Class clazz) {
        return response.readEntity(clazz);
    }

    public Object readEntity(GenericType<?> genericType) {
        return response.readEntity(genericType);
    }

}
