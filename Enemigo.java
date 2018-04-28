/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.drylands;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.Animation;

/**
 *
 * @author FairLight
 */
public class Enemigo {
    private int vida;
    private int posx,posy,newx,newy;
    private final double VELOCIDAD;
    private boolean mirandoD;
    private SpriteSheet sprites;
    private Animation run,runi;
    public final int TAMX=48,TAMY=72;
    
    public Enemigo(int posx, int posy, int vida, double vel, SpriteSheet sprites){
        this.posx=this.newx=posx;
        this.posy=this.newy=posy;
        VELOCIDAD=vel;
        this.vida=vida;
        this.sprites=sprites;
        run=new Animation();
        for (int i = 0; i < 4; i++) {
            run.addFrame(sprites.getSprite(i,2), 150);
        }
        runi=new Animation();
        for (int i = 0; i < 4; i++) {
            runi.addFrame(sprites.getSprite(i,2).getFlippedCopy(true, false), 150);
        }
        mirandoD=false;
    }
    public Animation getAnim(String animacion){
        switch(animacion){
            case "run":
                return run;
            case "runi":
                return runi;
            default:
                return run;
        }
    }
    public int getX(){
        return posx;
    }
    public int getY(){
        return posy;
    }
    
    
    
}
