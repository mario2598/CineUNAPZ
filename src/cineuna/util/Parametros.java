package cineuna.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parametros {

    private static Parametros INSTANCE = null;

    private FileInputStream configFile;
    private Properties appProperties;

    public static String rutalog;
    public static String sistema;
    public static String resturl;
    //public static HashMap<String, String> parametros;

    /**
     * Constructor privado para evitar que se instancien objetos
     */
    private Parametros() {
    }

    /**
     * Metodo para crear una instancia
     */
    private static void createInstance() {
        if (INSTANCE == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized (Parametros.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (INSTANCE == null) {
                    INSTANCE = new Parametros();
                }
            }
        }
    }

    /**
     * Metodo para obtener una instancia
     *
     * @return Instacia del contexto de la aplicación
     */
    public static Parametros getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * El método "clone" es sobreescrito por el siguiente que arroja una
     * excepción:
     *
     * @return Error en caso de intentar clonar
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Boolean cargarParametros() {
        try {

            configFile = new FileInputStream("resources/properties.ini");
            appProperties = new Properties();
            appProperties.load(configFile);
            configFile.close();

            if (appProperties.getProperty("parametros.rutalog") != null) {
                rutalog = appProperties.getProperty("parametros.rutalog");
            }
            if (appProperties.getProperty("parametros.sistema") != null) {
                sistema = appProperties.getProperty("parametros.sistema");
            }
            if (appProperties.getProperty("parametros.resturl") != null) {
                resturl = appProperties.getProperty("parametros.resturl");
            }

            /*parametros = new HashMap<>();
            appProperties.forEach((key, value) -> {
                try {
                    parametros.put(((String) key).substring(((String) key).indexOf(".") + 1), (String) value);
                } catch (Exception ex) {
                    parametros.put((String) key, (String) value);
                }
            });*/
            return true;
        } catch (IOException io) {
            System.out.println("Archivo de configuración no encontrado");
            return false;
        }
    }

    private void actualizarParametros() {
        try {
            appProperties.store(new FileOutputStream("resources/properties.ini"), "Parámetros del sistema.");
        } catch (IOException ex) {
            Logger.getLogger(Parametros.class.getName()).log(Level.SEVERE, "Error actualizando los parámetros del sistema.", ex);
        }
    }

    public String getAppProperty(String nombre) {
        return appProperties.getProperty("parametros." + nombre);
    }

    public void setAppProperty(String nombre, String valor) {
        this.appProperties.put("parametros." + nombre, valor);
        actualizarParametros();
    }

}
