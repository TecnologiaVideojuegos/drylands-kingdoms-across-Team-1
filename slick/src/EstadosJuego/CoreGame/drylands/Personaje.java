/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author FairLight
 */
public abstract class  Personaje {


    public final int TAMX, TAMY, ALTURACOLLIDER;
    protected int vida;
    protected boolean corriendo;

    public void setMirandoD(boolean mirandoD) {
        this.mirandoD = mirandoD;
    }

    protected boolean mirandoD;
    protected SpriteSheet sprites;
    protected Animation noanim, idle, run, jump, idlei, runi, jumpi, muerto, muertoi;
    protected float posx, posy, newx, newy;
    protected float velocidad;
    protected Rectangle hitbox;
    protected float angulo;

    protected boolean retroceso;
    protected float anguloRetroceso;
    protected int msRetroceso=800,contadorRetroceso;

    public Personaje(int tx, int ty, int altcollider, float vel, SpriteSheet sprs, int px, int py, int vida) {

        TAMX = tx;
        TAMY = ty;
        ALTURACOLLIDER = altcollider;
        this.vida = vida;
        hitbox = new Rectangle(px, py, tx, ty);

        velocidad = vel;
        sprites = sprs;

        corriendo = false;

        mirandoD = true;
        newx = posx = px;
        newy = posy = py;

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

    }
    public Personaje(int tx, int ty, int altcollider, float vel, int px, int py, int vida) {

        TAMX = tx;
        TAMY = ty;
        ALTURACOLLIDER = altcollider;
        this.vida = vida;
        hitbox = new Rectangle(px, py, tx, ty);

        velocidad = vel;


        corriendo = false;

        mirandoD = true;
        newx = posx = px;
        newy = posy = py;



    }

    public boolean isCorriendo() {
        return corriendo;
    }

    public void actHitbox() {
        hitbox.setX(newx);
        hitbox.setY(newy);
    }

    public boolean collidesCon(Shape forma) {
        return hitbox.intersects(forma);
    }

    public Shape getHitbox() {
        return hitbox;
    }

    public void rcvDmg(int puntos) {
        vida -= puntos;
        System.out.println("Lequedan" + vida);
    }

    public Animation getAnim(String nombre) {
        switch (nombre) {
            case "noanim":
                return noanim;

            case "idle":
                if (mirandoD)
                    return idle;
                else
                    return idlei;

            case "run":
                if (mirandoD)
                    return run;
                else
                    return runi;

            case "jump":
                if (mirandoD)
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
                if (mirandoD)
                    return muerto;
                else
                    return muertoi;
            case "muertoi":
                return muertoi;

            default:
                return noanim;

        }

    }
    public abstract  Animation getAnim();

    public float getX() {
        return posx;
    }

    public void setX(float posx) {
        this.newx = this.posx = posx;
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

    public float getAngulo(){return angulo;}

    public void setY(float posy) {
        this.newy = this.posy = posy;
    }

    public void updAngulo(){
        if (newx!=posx||newy!=posy){
            Vector2f vector = new Vector2f(new float[]{newx-posx,newy-posy});
            angulo=(float)vector.getTheta();
        }
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

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void resetX() {
        newx = posx;


    }

    public void resetY() {
        newy = posy;


    }

    public float getMaxstep(int delta) {

        return velocidad * delta;

    }

    public float getVel() {
        return velocidad;
    }

    public void setVel(float vel) {
        velocidad = vel;
    }

    public void setNewPosVector(float ax, float ay, float maxstep) {
        float difx = ax - (this.posx + (TAMX / 2));
        float dify = ay - (this.posy + (TAMY / 2));
        float difcuadrados = (difx * difx) + (dify * dify);
        float dist = (float) Math.sqrt(difcuadrados);
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
    public abstract boolean  estaAtacando();
    public abstract boolean  estaBloqueando();

    public boolean checkColX(Personaje player) {
        boolean colUpDer, colUpIz, colDownDer, colDownIz;

        colDownIz = hayPJ(player.getnewX(), player.getY() + player.TAMY);
        colUpIz = hayPJ(player.getnewX(), player.getY() + player.ALTURACOLLIDER);
        colDownDer = hayPJ(player.getnewX() + player.TAMX, player.getY() + player.TAMY);
        colUpDer = hayPJ(player.getnewX() + player.TAMX, player.getY() + player.ALTURACOLLIDER);

        return colUpDer || colUpIz || colDownDer || colDownIz;
    }

    public boolean checkColY(Personaje player) {
        boolean colUpDer, colUpIz, colDownDer, colDownIz;

        colDownIz = hayPJ(player.getX(), player.getnewY() + player.TAMY);
        colUpIz = hayPJ(player.getX(), player.getnewY() + player.ALTURACOLLIDER);
        colDownDer = hayPJ(player.getX() + player.TAMX, player.getnewY() + player.TAMY);
        colUpDer = hayPJ(player.getX() + player.TAMX, player.getnewY() + player.ALTURACOLLIDER);

        return colUpDer || colUpIz || colDownDer || colDownIz;
    }
    private boolean hayPJ(float x, float y) {

        return ((x>posx)&&(x<(posx+TAMX))&&(y>(posy+ALTURACOLLIDER))&&(y<(posy+TAMY)));
    }
    public static void renderOrdenados(Mapa mapa, ArrayList<Personaje> lista){
        Collections.sort(lista, new Comparator<Personaje>() {
            @Override
            public int compare(Personaje o1, Personaje o2) {
                return (int)(o1.getY()-o2.getY());
            }
        });
        for (Personaje personaje: lista) {
            personaje.getAnim().draw(personaje.getX() + mapa.getOffX(), personaje.getY() + mapa.getOffY());
        }
    }
    public boolean retrocediendo() {
        return retroceso;
    }
    public void retroceder(float angulo) {
        retroceso = true;
        anguloRetroceso=angulo;
        contadorRetroceso=msRetroceso;
    }
}
