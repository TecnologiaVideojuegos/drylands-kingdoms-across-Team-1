/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.drylands;

/**
 *
 * @author FairLight
 */
public abstract class Habilidad {
    private final String nombre;
    private int cdmax,cdrestante;
    
    public Habilidad(String nombre,int cdmax){
        this.nombre=nombre;
        this.cdmax=cdmax;
    }
    public void lowerCD(int delta){
        if((cdrestante-delta)>0)
            cdrestante-=delta;
        else
            cdrestante=0;
    }
    
}
