/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import java.util.Comparator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mario
 */
@XmlRootElement(name = "ButacaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ButacaDto{
    //Attributes
    @XmlTransient
    private Long butId;
    @XmlTransient
    private Long butColumna;
    @XmlTransient
    private String butEstado;
    @XmlTransient
    private Long butFila;
    @XmlTransient
    private String butImg;
    @XmlTransient
    private String butLetra;
    @XmlTransient
    private Long salaId;
    @XmlTransient
    private String butActiva;
    
    //Constructors
    public ButacaDto() {
        
    }
    
    //Methods
    public void duplicateData(ButacaDto b){
        this.butId = b.getButId();
        this.butColumna = b.getButColumna();
        this.butEstado = b.getButEstado();
        this.butFila = b.getButFila();
        this.butImg = b.getButImg();
        this.butLetra = b.getButLetra();
        this.salaId = b.getSalaId();
        this.butActiva = b.getButActiva();
    }

    //Getters and Setters
    public Long getButId() {
        return butId;
    }

    public void setButId(Long butId) {
        this.butId = butId;
    }

    public Long getButColumna() {
        return butColumna;
    }

    public void setButColumna(Long butColumna) {
        this.butColumna = butColumna;
    }

    public String getButEstado() {
        return butEstado;
    }

    public void setButEstado(String butEstado) {
        this.butEstado = butEstado;
    }

    public Long getButFila() {
        return butFila;
    }

    public void setButFila(Long butFila) {
        this.butFila = butFila;
    }

    public String getButImg() {
        return butImg;
    }

    public void setButImg(String butImg) {
        this.butImg = butImg;
    }

    public String getButLetra() {
        return butLetra;
    }

    public void setButLetra(String butLetra) {
        this.butLetra = butLetra;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    public String getButActiva() {
        return butActiva;
    }

    public void setButActiva(String butActiva) {
        this.butActiva = butActiva;
    }

    /*comparador para ordenamiento por filas*/
    public static Comparator<ButacaDto> butFil = (ButacaDto b1, ButacaDto b2) -> {
        Long fil1 = b1.getButFila();
        Long fil2 = b2.getButFila();
        
        return (int) (fil1-fil2);
        
    };
    
    /*comparador para ordenamiento por filas*/
    public static Comparator<ButacaDto> butCol = (ButacaDto b1, ButacaDto b2) -> {
        Long fil1 = b1.getButColumna();
        Long fil2 = b2.getButColumna();
        
        return (int) (fil1-fil2);
        
    };
     
}
