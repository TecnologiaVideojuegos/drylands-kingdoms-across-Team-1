/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.Animation;

/**
 * @author FairLight
 */
public abstract class Habilidad {
    private final String nombre;
    protected boolean activa,fail;
    protected int cdmax, cdrestante;
    Animation der, izq;
    Combo combo;


    public Habilidad(String nombre, int cdmax,Combo combo) {
        this.nombre = nombre;
        this.cdmax = cdmax;
        this.combo=combo;
    }

    public int getCdmax() {
        return cdmax;
    }

    public void setCdmax(int cdmax) {
        this.cdmax = cdmax;
    }

    public Animation getDAnim() {
        return der;
    }

    public Animation getIAnim() {
        return izq;
    }

    public void lowerCD(int delta) {
        if ((cdrestante - delta) > 0)
            cdrestante -= delta;
        else
            cdrestante = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void calcNuevaPos(Personaje pj, int delta);

    //public abstract void cast();
    public void terminar() {
        if(fail)
            combo.resetCombo();
        activa = false;

    }

    public void contarCD() {
        if(combo.getCombo()>20) {
            cdrestante = (int)(cdmax/5);
        }
        else if(combo.getCombo()>10) {
            cdrestante = (int)(cdmax/3);
        }
        else if(combo.getCombo()>5){
            cdrestante = (int)(cdmax/2);
        }
        else if(combo.getCombo()>0){
            cdrestante = (int)(cdmax/1.5);
        }
        else{
            cdrestante = cdmax;
            }
    }

    public boolean estaActiva() {
        return activa;
    }

    public int getCDRestante() {
        return cdrestante;
    }
    public void landed(){
        fail=false;
    }
}

