/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.drylands;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
/**
 *
 * @author FairLight
 */
public class Dash extends Habilidad{
    private int targetX,targetY;
    private int MSDURACIONDASH=210;
    private int rango;
    private int msAcum;
    
    
    public Dash(int cd,SpriteSheet sprites){
        super("Dash",cd);
        der=new Animation();
        izq=new Animation();
        for (int i = 0; i < 7; i++) {
            der.addFrame(sprites.getSprite(i,3), MSDURACIONDASH/7);
        }
        for (int i = 0; i < 7; i++) {
            izq.addFrame(sprites.getSprite(i,3).getFlippedCopy(true, false), MSDURACIONDASH/7);
        }
        rango=250;
        msAcum=0;
    }
    @Override
    public void calcNuevaPos(Personaje pj,int delta){
        if(this.activa){
            pj.setNewPosVector(targetX, targetY, rango*delta/MSDURACIONDASH);
            msAcum+=delta;
            if(msAcum>=MSDURACIONDASH){
                terminar();
                msAcum=0;
            }
        }
        
    }
    
    
    public void cast(int targetX,int targetY){
        
        this.targetX=targetX;
        this.targetY=targetY;
        activa=true;
        
        
    }
    public int getRango(){
        return rango;
    }
    
}
