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
    private int targetX,targetY,decelX,decelY;
    private double  velocidadDash;
    private int rango;
    private int contadorMs;
    private int msAcel=125,msDecel=250;
    
    
    public Dash(double vel,int cd,SpriteSheet sprites){
        super("Dash",cd);
        der=new Animation();
        izq=new Animation();
        for (int i = 0; i < 7; i++) {
            der.addFrame(sprites.getSprite(i,3), 150);
        }
        for (int i = 0; i < 7; i++) {
            izq.addFrame(sprites.getSprite(i,3).getFlippedCopy(true, false), 150);
        }
        rango=250;
        contadorMs=0;
        velocidadDash=vel;

    }
    @Override
    public void calcNuevaPos(Personaje pj,int delta){


        if(this.activa){
            if(this.cdrestante!=0){//ya ha llegado al punto y esta decelerando
                if(contadorMs-delta<=0){//ha terminado de acelerar
                    contadorMs=0;
                    terminar();
                }
                else{
                    contadorMs-=delta;
                }
                pj.setNewPosVector(decelX, decelY, velocidadDash*delta*contadorMs/msAcel);
            }
            else{//no ha llegado al target
                if(contadorMs+delta>=msAcel){//ha terminado de acelerar
                    contadorMs=msAcel;
                }
                else{
                    contadorMs+=delta;
                }
                pj.setNewPosVector(targetX, targetY, velocidadDash*delta*contadorMs/msAcel);
                if(((pj.getX()+pj.TAMX/2)==targetX)&&((pj.getY()+pj.TAMY/2)==targetY)){
                    contarCD();
                    System.out.println("Llegado al target , posx:"+pj.getX()+",targety:"+pj.getY());

                }
            }
        }
    }
    
    
    public void cast(Personaje pj,int targetX,int targetY){
        
        this.targetX=targetX;
        this.targetY=targetY;
        this.decelX=2*targetX-(pj.getX()+pj.TAMX);
        this.decelY=2*targetY-(pj.getY()+pj.TAMY);
        activa=true;
        contadorMs=0;
        System.out.println("Casteado dash, targetx:"+targetX+",targety:"+targetY);
        
        
    }
    public int getRango(){
        return rango;
    }
    
}
