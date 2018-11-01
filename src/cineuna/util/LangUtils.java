/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

/**
 *
 * @author robri
 */
public class LangUtils {
     private static LangUtils INSTANCE = null;
     private static ResourceBundle propFile;
     private static Locale locale;
     private static String localPath;
     private static String lang;

    public LangUtils() {
        localPath="cineuna/resources/lang.lang";//base para el archivo propFile que quiera seleccionar (a este se le agrega el tag)
        lang="es";
    }
     
    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (LangUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LangUtils();
                }
            }
        }
    }

    public static LangUtils getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        LangUtils.lang = lang;
    }
    
    /**
     * recibe un botón y le aplica el texto de un tag 
     * @param button botón sobre el cual cargar texto
     * @param tag etiqueta del property a la cual quiere acceder
     */
    public void loadButtonLang(JFXButton button,String tag){
        locale = new Locale(lang);
        propFile = ResourceBundle.getBundle(localPath,locale);
        button.setText(propFile.getString(tag));
    }
    
    /**
     * recibe un label y le aplica el texto de un tag
     * @param lbl label sobre el cual cargar texto
     * @param tag etiqueta del property a la cual quiere acceder
     */
    public void loadLabelLang(Label lbl,String tag){
        locale = new Locale(lang);
        propFile = ResourceBundle.getBundle(localPath,locale);
        lbl.setText(propFile.getString(tag));
    }
    
    /**
     * recibe un text fiel y le aplica el texto de un tag
     * @param lbl label sobre el cual cargar texto
     * @param tag etiqueta del property a la cual quiere acceder
     */
    public void loadTFLang(JFXTextField lbl,String tag){
        locale = new Locale(lang);
        propFile = ResourceBundle.getBundle(localPath,locale);
        lbl.setPromptText(propFile.getString(tag));
    }
    
    /**
     * recibe un Passwor field y le aplica el texto de un tag
     * @param lbl label sobre el cual cargar texto
     * @param tag etiqueta del property a la cual quiere acceder
     */
    public void loadPFLang(JFXPasswordField lbl,String tag){
        locale = new Locale(lang);
        propFile = ResourceBundle.getBundle(localPath,locale);
        lbl.setPromptText(propFile.getString(tag));
    }
    
}
