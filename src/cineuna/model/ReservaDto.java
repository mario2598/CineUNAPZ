/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineuna.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *

 * @author mario
 */
 @XmlRootElement(name = "ReservaDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ReservaDto {

    @XmlTransient
    private String resEstado;
    @XmlTransient
    private TandaDto tandaId;
    @XmlTransient
    private Long resId;
    @XmlTransient
    private ButacaDto butId;

    public ReservaDto() {
    
    }
    
    //Methods
    public void duplicateData(ReservaDto r){
        this.butId=r.getButId();
        this.resEstado=r.getResEstado();
        this.resId=r.getResId();
        this.tandaId=r.getTandaId();
   }


    public String getResEstado() {
        return resEstado;
    }

    public void setResEstado(String resEstado) {
        this.resEstado = resEstado;
    }

    public TandaDto getTandaId() {
        return tandaId;
    }

    public void setTandaId(TandaDto tandaId) {
        this.tandaId = tandaId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public ButacaDto getButId() {
        return butId;
    }

    public void setButId(ButacaDto butId) {
        this.butId = butId;
    }
}
