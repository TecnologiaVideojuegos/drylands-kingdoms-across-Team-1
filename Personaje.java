/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author FairLight
 */
public class  Personaje {
    protected int vida;
    public final int TAMX , TAMY , ALTURACOLLIDER ;
    protected boolean corriendo, mirandoD;
    protected SpriteSheet sprites;
    protected Animation noanim, idle, run, jump, idlei, runi, jumpi;
    protected int posx, posy, newx, newy;
    protected final double VELOCIDAD;
    protected double movAcumx = (double) 0.0, movAcumy = (double) 0.0;
    
    public Personaje(int tx,int ty,int altcollider, double vel, SpriteSheet sprs,int px, int py,int vida){
        
        TAMX=tx;
        TAMY=ty;
        ALTURACOLLIDER=altcollider;
        
        VELOCIDAD=vel;
        sprites=sprs;
        
        noanim = new Animation();
        noanim.addFrame(sprites.getSprite(0, 0), 150);

        idle = new Animation();
        for (int i = 0; i < 4; i++) {
            idle.addFrame(sprites.getSprite(i, 1), 150);
        }

        run = new Animation();
        for (int i = 0; i < 4; i++) {
            run.addFrame(sprites.getSprite(i, 2), 120);
        }
        jump = new Animation();
        for (int i = 0; i < 7; i++) {
            jump.addFrame(sprites.getSprite(i, 3), 120);
        }

        idlei = new Animation();
        for (int i = 0; i < 4; i++) {
            idlei.addFrame(sprites.getSprite(i, 1).getFlippedCopy(true, false), 150);
        }

        runi = new Animation();
        for (int i = 0; i < 4; i++) {
            runi.addFrame(sprites.getSprite(i, 2).getFlippedCopy(true, false), 120);
        }
        jumpi = new Animation();
        for (int i = 0; i < 7; i++) {
            jumpi.addFrame(sprites.getSprite(i, 3).getFlippedCopy(true, false), 120);
        }
        corriendo = false;

        mirandoD = true;
        newx = posx = px;
        newy = posy = py;
    }
    public boolean isCorriendo() {
        return corriendo;
    }
    public Animation getAnim(String nombre) {
        switch (nombre) {
            case "noanim":
                return noanim;

            case "idle":
                return idle;

            case "run":
                return run;

            case "jump":
                return jump;

            case "idlei":
                return idlei;

            case "runi":
                return runi;

            case "jumpi":
                return jumpi;

            default:
                return noanim;

        }

    }
    public int getX() {
        return posx;
    }

    public int getnewX() {
        return newx;
    }

    public int getnewY() {
        return newy;
    }

    public boolean mirandoD() {
        return mirandoD;
    }
    public void setX(int posx) {
        this.posx = posx;
    }

    public void addX(int incx) {

        this.newx = posx + incx;

    }

    public void addY(int incy) {
        this.newy = posy + incy;

    }

    public int getY() {
        return posy;
    }

    public void setY(int posy) {
        this.posy = posy;
    }
    public void updPosX() {

        posx = newx;
    }

    public void updPosY() {
        posy = newy;

    }

    public void resetX() {
        newx = posx;
        movAcumx = 0;
        /*atacando=false;*/
    }

    public void resetY() {
        newy = posy;
        movAcumy = 0;
        /*atacando=false;*/
    }
    
    public double getMaxstep(int delta) {

        return VELOCIDAD * delta;

    }

    public void setNewPosVector(int ax, int ay, double maxstep) {
        int difx = ax - (this.posx + (TAMX / 2));
        int dify = ay - (this.posy + (TAMY / 2));
        int difcuadrados = (difx * difx) + (dify * dify);
        double dist = Math.sqrt((double) difcuadrados);
        if ((difx > 0)) {
            mirandoD = true;
        }
        if ((difx < 0)) {
            mirandoD = false;
        }
        if (dist > (maxstep)) {
            double incx = (difx * maxstep / dist) + movAcumx;

            int intincx = (int) Math.round(incx);
            movAcumx = incx - intincx;
            this.newx += intincx;
            double incy = (dify * maxstep / dist) + movAcumy;
            int intincy = (int) Math.round(incy);
            movAcumy = incy - intincy;
            this.newy += intincy;

        } else {

            this.newx += difx;
            this.newy += dify;

        }

    }
}
