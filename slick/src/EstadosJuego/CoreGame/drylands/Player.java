/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
//import java.math.*;

/**
 * @author FairLight
 */
public class Player extends Personaje {


    public Dash dash;
    public Block block;
    private Animation animretroceso,animretrocesoi;




    private boolean solapando;

    public int getVidamax() {
        return vidamax;
    }


    private int vidamax;


    public boolean isSolapando() {
        return solapando;
    }

    public void setSolapando(boolean solapando) {
        this.solapando = solapando;
    }

    public Player(SpriteSheet sprites,int vidaplayer,Combo combo) {

        super(48, 60, 40, (float) 0.3, sprites, -1000, -1000, vidaplayer);

        dash = new Dash((float) 1.2, 1000, sprites,combo,250);
        block = new Block("Block",2000,1000,sprites,combo);
        this.vidamax=vidaplayer;
        animretroceso = new Animation();
        for (int i = 0; i < 5; i++) {
            animretroceso.addFrame(sprites.getSprite(i, 6), 80);
        }
        animretroceso.stopAt(4);
        animretrocesoi = new Animation();
        for (int i = 0; i < 5; i++) {
            animretrocesoi.addFrame(sprites.getSprite(i, 6).getFlippedCopy(true, false), 80);
        }
        animretrocesoi.stopAt(4);
    }


    public void setCorriendo() {
        corriendo = true;
    }

    public void setIdle() {
        corriendo = false;
    }


    public void calcNuevaPos(/*int ax, int ay, a*/int delta, Mapa mapa) {
        if (retroceso) {

            if(solapando){
                Vector2f despl = new Vector2f((double)anguloRetroceso);
                Vector2f posicion = new Vector2f(new float[]{posx,posy});

                this.setNewPosVector(posicion.x+1000*despl.x,posicion.y+1000*despl.y,(float)(getMaxstep(delta)*1.5));
            }else{
                contadorRetroceso-=delta;
                if(contadorRetroceso<=0){
                    retroceso=false;
                }
                else{
                    Vector2f despl = new Vector2f((double)anguloRetroceso);
                    Vector2f posicion = new Vector2f(new float[]{posx,posy});

                    this.setNewPosVector(posicion.x+1000*despl.x,posicion.y+1000*despl.y,(float)(getMaxstep(delta)*1.5*contadorRetroceso/msRetroceso));
                }
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





    public boolean estaAtacando() {
        return dash.activa;
    }
    public boolean estaBloqueando() {
        return block.activa;
    }

    public Animation getAnim() {
        if(vida<=0){
            return muerto;
        }
        
        else if (this.estaAtacando()) {
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
        else if(retrocediendo()){
            if (mirandoD)
                return animretroceso;
            else
                return animretrocesoi;
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
    @Override
    public void retroceder(float angulo) {
        retroceso = true;
        anguloRetroceso=angulo;
        contadorRetroceso=msRetroceso;
        if(dash.estaActiva()){
            dash.contarCD();
            dash.terminar();
        }

    }

    public void setCorriendo(boolean corriendo) {
        this.corriendo = corriendo;
    }
    public boolean cambiaEstado(){
        return (muerto.getFrame()==6)||(muertoi.getFrame()==6);
    }
}
