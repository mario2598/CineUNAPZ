/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "UsuarioDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class UsuarioDto {
    //Attributes
    @XmlTransient
    public SimpleStringProperty usuId;
    @XmlTransient
    public SimpleStringProperty usuUser;
    @XmlTransient
    public SimpleStringProperty usuNombre;
    @XmlTransient
    public SimpleStringProperty usuPapellido;
    @XmlTransient
    public SimpleStringProperty usuSapellido;
    @XmlTransient
    public SimpleStringProperty usuPassword;
    @XmlTransient
    public SimpleStringProperty usuEmail;
    @XmlTransient
    public SimpleStringProperty usuIdioma;
    @XmlTransient
    public SimpleStringProperty usuEstado;
    @XmlTransient
    public SimpleStringProperty usuAdmin;
    @XmlTransient
    public SimpleStringProperty usuNewpassword;
    @XmlTransient
    public SimpleStringProperty usuCambio;
    @XmlTransient
<<<<<<< HEAD
    public SimpleStringProperty usuCodAct;
   // @XmlTransient
   // List<CineDto> cineList;
     public UsuarioDto() {
=======
    public Long cineId;
    
    //Constructors
    public UsuarioDto() {
>>>>>>> Fallas
        this.usuId = new SimpleStringProperty();
        this.usuUser = new SimpleStringProperty();
        this.usuNombre = new SimpleStringProperty();
        this.usuPapellido = new SimpleStringProperty();
        this.usuSapellido = new SimpleStringProperty();
        this.usuPassword = new SimpleStringProperty();
        this.usuEmail = new SimpleStringProperty();
        this.usuIdioma = new SimpleStringProperty("1");
        this.usuEstado = new SimpleStringProperty("I");
        this.usuAdmin = new SimpleStringProperty("N");
        this.usuNewpassword = new SimpleStringProperty();
        this.usuCambio = new SimpleStringProperty("N");
        this.usuCodAct = new SimpleStringProperty();
        
    }    

    //Methods
    public void duplicateData(UsuarioDto u){
        this.usuId = u.usuId;
        this.usuUser = u.usuUser;
        this.usuNombre = u.usuNombre;
        this.usuPapellido = u.usuPapellido;
        this.usuSapellido = u.usuSapellido;
        this.usuPassword = u.usuPassword;
        this.usuEmail = u.usuEmail;
        this.usuIdioma = u.usuIdioma;
        this.usuEstado = u.usuEstado;
        this.usuAdmin = u.usuAdmin;
        this.usuNewpassword = u.usuNewpassword;
        this.usuCambio = u.usuCambio;
    }
    
    //Getters and Setters
    public Long getUsuId() {
        if(usuId.get()!=null && !usuId.get().isEmpty())
            return Long.valueOf(usuId.get());
        else
            return null;
    }

    public void setUsuId(Long usuId) {
        this.usuId.set(usuId.toString());
    }

    public String getUsuUser() {
        return usuUser.get();
    }

    public void setUsuUser(String usuUser) {
        this.usuUser.set(usuUser);
    }

    public String getUsuNombre() {
        return usuNombre.get();
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre.set(usuNombre);
    }

    public String getUsuPapellido() {
        return usuPapellido.get();
    }

    public void setUsuPapellido(String usuPapellido) {
        this.usuPapellido.set(usuPapellido);
    }

    public String getUsuSapellido() {
        return usuSapellido.get();
    }

    public void setUsuSapellido(String usuSapellido) {
        this.usuSapellido.set(usuSapellido);
    }

    public String getUsuPassword() {
        return usuPassword.get();
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword.set(usuPassword);
    }

    public String getUsuEmail() {
        return usuEmail.get();
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail.set(usuEmail);
    }

    public Long getUsuIdioma() {
        return Long.valueOf(usuIdioma.get());
    }

    public void setUsuIdioma(Long usuIdioma) {
        this.usuIdioma.set(usuIdioma.toString());
    }

    public String getUsuEstado() {
        return usuEstado.get();
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado.set(usuEstado);
    }

    public String getUsuAdmin() {
        return usuAdmin.get();
    }

    public void setUsuAdmin(String usuAdmin) {
        this.usuAdmin.set(usuAdmin);
    }

    public String getUsuNewpassword() {
        return usuNewpassword.get();
    }

    public void setUsuNewpassword(String usuNewpassword) {
        this.usuNewpassword.set(usuNewpassword);
    }

    public String getUsuCambio() {
        return usuCambio.get();
    }

    public void setUsuCambio(String usuCambio) {
        this.usuCambio.set(usuCambio);
    }
<<<<<<< HEAD
    public String getUsuCodAct() {
        return usuCodAct.get();
    }

    public void setUsuCodAct(String usuCodAct) {
        this.usuCodAct.set(usuCodAct);
    }
    
=======

    public Long getCineId() {
        return cineId;
    }

    public void setCineId(Long cineId) {
        this.cineId = cineId;
    }
>>>>>>> Fallas
     
}
