/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoreGame.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
//import java.math.*;

/**
 * @author FairLight
 */
public class Player extends Personaje {


    public Dash dash;
    public Block block;
    private boolean retroceso;
    private int msRetrocesoMax = 350, msRetroceso;
    private float retrocesoX, retrocesoY;

    public Player(SpriteSheet sprites) {

        super(48, 60, 48, (float) 0.3, sprites, 1000, 500, 10);

        dash = new Dash((float) 1.2, 1000, sprites);
        block = new Block("Block",3000,1000,sprites);

    }


    public void setCorriendo() {
        corriendo = true;
    }

    public void setIdle() {
        corriendo = false;
    }


    public void calcNuevaPos(/*int ax, int ay, a*/int delta, Mapa mapa) {
        if (retroceso) {

            msRetroceso -= delta;
            if (msRetroceso <= 0) {
                retroceso = false;
            } else {
                this.setNewPosVector(retrocesoX, retrocesoY, msRetroceso / 75);
            }
        } else if (dash.activa) {
            dash.calcNuevaPos(this, delta);
        }
         else if (block.activa) {
            block.calcNuevaPos(this, delta);
     }

        else if (!this.tocaRaton(mapa.getAbsMouseX(), mapa.getAbsMouseY()) && this.isCorriendo()) {

            float ax = mapa.getAbsMouseX();
            float ay = mapa.getAbsMouseY();

            float maxstep = getMaxstep(delta);
            this.setNewPosVector(ax, ay, maxstep);
        }

    }


    public boolean tocaRaton(float rx, float ry) {

        return (rx >= posx) && (rx <= (posx + TAMX)) && (ry >= posy) && (ry <= (posy + TAMY));
    }

    public void lowerCDs(int delta) {
        dash.lowerCD(delta);
        block.lowerCD(delta);
    }

    @Override
    public void resetX() {
        newx = posx;

        this.dash.terminar();
        this.dash.contarCD();
    }

    @Override
    public void resetY() {
        newy = posy;

        this.dash.terminar();
        this.dash.contarCD();
    }

    public void retroceder(int distancia) {
        retroceso = true;
        msRetroceso = msRetrocesoMax;
        retrocesoX = posx + 100 * (posx - newx);
        retrocesoY = posy + 100 * (posy - newy);
    }

    public boolean retrocediendo() {
        return retroceso;
    }

    public boolean estaAtacando() {
        return dash.activa;
    }
    public boolean estaBloqueando() {
        return block.activa;
    }

    public Animation getAnim() {
        if (this.estaAtacando()) {
            if (dash.activa) {
                if (mirandoD)
                    return dash.getDAnim();
                else
                    return dash.getIAnim();
            }

        } else if(estaBloqueando()){
            if (mirandoD)
                return block.getDAnim();
            else
                return block.getIAnim();
        }
        else {
            if (corriendo) {
                if (mirandoD) return run;
                else return runi;
            } else {
                if (mirandoD) return idle;
                else return idlei;
            }
        }
        return noanim;

    }
    @Override
    public Shape getHitbox() {

        if (dash.estaActiva())

            return new Line(dash.getStartX(),dash.getStartY(),posx+TAMX/2,posy+TAMY/2);

        else
            return hitbox;

    }
}
