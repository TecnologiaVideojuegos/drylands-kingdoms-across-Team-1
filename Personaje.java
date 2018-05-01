/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author FairLight
 */
public class  Personaje {


    protected int vida;
    public final int TAMX , TAMY , ALTURACOLLIDER ;
    protected boolean corriendo, mirandoD;
    protected SpriteSheet sprites;
    protected Animation noanim, idle, run, jump, idlei, runi, jumpi,muerto, muertoi;
    protected float posx, posy, newx, newy;
    protected final float VELOCIDAD;




    protected Rectangle hitbox;
    
    public Personaje(int tx,int ty,int altcollider, float vel, SpriteSheet sprs,int px, int py,int vida){
        
        TAMX=tx;
        TAMY=ty;
        ALTURACOLLIDER=altcollider;
        this.vida=vida;
        hitbox=new Rectangle(px,py,tx,ty);

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
        muerto = new Animation();
        for (int i = 0; i < 7; i++) {
            muerto.addFrame(sprites.getSprite(i, 4), 80);
        }
        muerto.stopAt(6);
        muertoi = new Animation();
        for (int i = 0; i < 7; i++) {
            muertoi.addFrame(sprites.getSprite(i, 4).getFlippedCopy(true, false), 80);
        }
        muertoi.stopAt(6);
        corriendo = false;

        mirandoD = true;
        newx = posx = px;
        newy = posy = py;
    }
    public boolean isCorriendo() {
        return corriendo;
    }
    public void actHitbox(){
        hitbox.setX(posx);
        hitbox.setY(posy);
    }
    public boolean collidesCon(Shape forma){
        return hitbox.intersects(forma);
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void dmg(int puntos){
        vida-=puntos;
        System.out.println("Lequedan"+vida);
    }
    public Animation getAnim(String nombre) {
        switch (nombre) {
            case "noanim":
                return noanim;

            case "idle":
                if(mirandoD)
                    return idle;
                else
                    return idlei;

            case "run":
                if(mirandoD)
                    return run;
                else
                    return runi;

            case "jump":
                if(mirandoD)
                    return jump;
                else
                    return jumpi;

            case "idlei":
                return idlei;

            case "runi":
                return runi;

            case "jumpi":
                return jumpi;
            case "muerto":
                if(mirandoD)
                    return muerto;
                else
                    return muertoi;
            case "muertoi":
                return muertoi;

            default:
                return noanim;

        }

    }

    public float getX() {
        return posx;
    }

    public float getnewX() {
        return newx;
    }

    public float getnewY() {
        return newy;
    }

    public boolean mirandoD() {
        return mirandoD;
    }
    

    public void addX(float incx) {

        this.newx = posx + incx;

    }

    public void addY(float incy) {
        this.newy = posy + incy;

    }

    public float getY() {
        return posy;
    }

    
    public void updPosX() {

        posx = newx;
    }

    public void updPosY() {
        posy = newy;

    }
    public int getVida() {
        return vida;
    }
    public void resetX() {
        newx = posx;


    }

    public void resetY() {
        newy = posy;


    }
    
    public float getMaxstep(int delta) {

        return VELOCIDAD * delta;

    }

    public void setNewPosVector(float ax, float ay, float maxstep) {
        float difx = ax - (this.posx + (TAMX / 2));
        float dify = ay - (this.posy + (TAMY / 2));
        float difcuadrados = (difx * difx) + (dify * dify);
        float dist = (float)Math.sqrt( difcuadrados);
        if ((difx > 0)) {
            mirandoD = true;
        }
        if ((difx < 0)) {
            mirandoD = false;
        }
        if (dist > (maxstep)) {
            float incx = (difx * maxstep / dist);



            addX(incx);
            float incy = (dify * maxstep / dist);

            addY(incy);

        } else {

            addX(difx);
            addY(dify);

        }

    }
}
