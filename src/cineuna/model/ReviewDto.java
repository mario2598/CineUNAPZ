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
@XmlRootElement(name = "ReviewDto")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ReviewDto {
    //Attributes
    @XmlTransient
    private Long revId;
    @XmlTransient
    private Long revCalif;
    @XmlTransient
    private String revOpinion;
    @XmlTransient
    private Long movieId;
    @XmlTransient
    private Long usuId;
      
    //Constructors
    public ReviewDto() {
        
    }
    
    //Methods
    public void duplicateData(ReviewDto r){
        this.revId = r.getRevId();
        this.revCalif = r.getRevCalif();
        this.revOpinion = r.getRevOpinion();
        this.movieId = r.getMovieId();
        this.usuId = r.getUsuId();
    }

    //Setters and Getters
    public Long getRevId() {
        return revId;
    }

    public void setRevId(Long revId) {
        this.revId = revId;
    }

    public Long getRevCalif() {
        return revCalif;
    }

    public void setRevCalif(Long revCalif) {
        this.revCalif = revCalif;
    }

    public String getRevOpinion() {
        return revOpinion;
    }

    public void setRevOpinion(String revOpinion) {
        this.revOpinion = revOpinion;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getUsuId() {
        return usuId;
    }

    public void setUsuId(Long usuId) {
        this.usuId = usuId;
    }
    
}
