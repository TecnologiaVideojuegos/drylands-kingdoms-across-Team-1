/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.drylands;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Rectangle;
//import java.math.*;

/**
 *
 * @author FairLight
 */
public class Player extends Personaje{




    private boolean retroceso;
    private int msRetrocesoMax=350,msRetroceso;
    private int retrocesoX,retrocesoY;

    public Dash dash;
    public Player(SpriteSheet sprites) {

        super(48,72,48,0.3,sprites,1000,500,10);

        dash = new Dash(1.2,1000, sprites);

    }

    

    

    public void setCorriendo() {
        corriendo = true;
    }

    public void setIdle() {
        corriendo = false;
    }

    

    public void calcNuevaPos(/*int ax, int ay, a*/int delta, Mapa mapa) {
        if(retroceso){

            msRetroceso-=delta;
            if(msRetroceso<=0){
                retroceso=false;
            }
            else{
                this.setNewPosVector(retrocesoX,retrocesoY,msRetroceso/75);
            }
        }
        else if (dash.activa) {
            dash.calcNuevaPos(this, delta);
        } else if (!this.tocaRaton(mapa.getAbsMouseX(), mapa.getAbsMouseY()) && this.isCorriendo()) {

            int ax = mapa.getAbsMouseX();
            int ay = mapa.getAbsMouseY();

            double maxstep = getMaxstep(delta);
            this.setNewPosVector(ax, ay, maxstep);
        }

    }

    

    public boolean tocaRaton(int rx, int ry) {

        return (rx >= posx) && (rx <= (posx + TAMX)) && (ry >= posy) && (ry <= (posy + TAMY));
    }

    public void lowerCDs(int delta) {
        dash.lowerCD(delta);
    }

    @Override
    public void resetX() {
        newx = posx;
        movAcumx = 0;
        this.dash.terminar();
        this.dash.contarCD();
    }
    @Override
    public void resetY() {
        newy = posy;
        movAcumy = 0;
        this.dash.terminar();
        this.dash.contarCD();
    }

    public void retroceder(int distancia) {
        retroceso=true;
        msRetroceso=msRetrocesoMax;
        retrocesoX=posx+100*(posx-newx);
        retrocesoY=posy+100*(posy-newy);
    }
    public boolean retrocediendo() {
        return retroceso;
    }
    public boolean atacando(){
        return dash.activa;
    }
}
