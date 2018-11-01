/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import cineuna.util.LocalDateAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "CineDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class CineDto { 
    //Attributes
    @XmlTransient
    private Long cineId;
    @XmlTransient
    private String cineNombre;
    @XmlTransient
    private Long cineTel;
    @XmlTransient
    private String cineEmail;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    private LocalDate cineAbre;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlTransient
    private LocalDate cineCierra;
    @XmlTransient
    private ArrayList<SalaDto> salaList;
    @XmlTransient
    private ArrayList<UsuarioDto> usuarioList;
    
    //Constructors
    public CineDto(){
        
    }
    
    //Methods
    public void duplicateData(CineDto c) {
        this.cineId = c.getCineId();
        this.cineNombre = c.getCineNombre();
        this.cineTel = c.getCineTel();
        this.cineEmail = c.getCineEmail();
        this.cineAbre = c.getCineAbre();
        this.cineCierra = c.getCineCierra();
        this.salaList = c.getSalaList();
        this.usuarioList = c.getUsuarioList();
    }
    
    //Setters and Getters
    public Long getCineId() {
        return cineId;
    }

    public void setCineId(Long cineId) {
        this.cineId = cineId;
    }

    public String getCineNombre() {
        return cineNombre;
    }

    public void setCineNombre(String cineNombre) {
        this.cineNombre = cineNombre;
    }

    public Long getCineTel() {
        return cineTel;
    }

    public void setCineTel(Long cineTel) {
        this.cineTel = cineTel;
    }

    public String getCineEmail() {
        return cineEmail;
    }

    public void setCineEmail(String cineEmail) {
        this.cineEmail = cineEmail;
    }

    public LocalDate getCineAbre() {
        return cineAbre;
    }

    public void setCineAbre(LocalDate cineAbre) {
        this.cineAbre = cineAbre;
    }

    public LocalDate getCineCierra() {
        return cineCierra;
    }

    public void setCineCierra(LocalDate cineCierra) {
        this.cineCierra = cineCierra;
    }

    public ArrayList<SalaDto> getSalaList() {
        return salaList;
    }

    public void setSalaList(ArrayList<SalaDto> salaList) {
        this.salaList = salaList;
    }

    public ArrayList<UsuarioDto> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(ArrayList<UsuarioDto> usuarioList) {
        this.usuarioList = usuarioList;
    }
    
}
