/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 * @author FairLight
 */
public class Dash extends Habilidad {


    private float targetX, targetY, decelX, decelY,startX,startY;
    private float velocidadDash;


    private int rango;
    private int contadorMs;
    private int msAcel = 125, msDecel = 250;


    public Dash(float vel, int cd, SpriteSheet sprites) {
        super("Dash", cd);
        der = new Animation();
        izq = new Animation();
        for (int i = 0; i < 7; i++) {
            der.addFrame(sprites.getSprite(i, 3), 150);
        }
        for (int i = 0; i < 7; i++) {
            izq.addFrame(sprites.getSprite(i, 3).getFlippedCopy(true, false), 150);
        }
        rango = 250;
        contadorMs = 0;
        velocidadDash = vel;

    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public float getDecelX() {
        return decelX;
    }

    public void setDecelX(float decelX) {
        this.decelX = decelX;
    }

    public float getDecelY() {
        return decelY;
    }

    public void setDecelY(float decelY) {
        this.decelY = decelY;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    @Override
    public void calcNuevaPos(Personaje pj, int delta) {


        if (this.activa) {
            if (this.cdrestante != 0) {//ya ha llegado al punto y esta decelerando
                if (contadorMs - delta <= 0) {//ha terminado de acelerar
                    contadorMs = 0;
                    terminar();
                } else {
                    contadorMs -= delta;
                }
                pj.setNewPosVector(decelX, decelY, velocidadDash * delta * contadorMs / msAcel);
            } else {//no ha llegado al target
                if (contadorMs + delta >= msAcel) {//ha terminado de acelerar
                    contadorMs = msAcel;
                } else {
                    contadorMs += delta;
                }
                pj.setNewPosVector(targetX, targetY, velocidadDash * delta * contadorMs / msAcel);
                if (((pj.getX() + pj.TAMX / 2) == targetX) && ((pj.getY() + pj.TAMY / 2) == targetY)) {
                    contarCD();


                }
            }
        }
    }


    public void cast(Personaje pj, float targetX, float targetY) {

        this.startX = pj.getX()+pj.TAMX/2;
        this.startY = pj.getY()+pj.TAMY/2;
        this.targetX = targetX;
        this.targetY = targetY;
        this.decelX = 2 * targetX - (pj.getX() + pj.TAMX / 2);
        this.decelY = 2 * targetY - (pj.getY() + pj.TAMY / 2);
        activa = true;
        contadorMs = 0;


    }

    public int getRango() {
        return rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }



}
