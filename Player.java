/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.tests;

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
public class Player {
    
    private boolean corriendo,atacando,mirandoD;
    private SpriteSheet sprites;
    private Animation noanim,idle,run,jump, idlei,runi,jumpi;
    private int posx,posy,newx,newy;
    public final int TAMX=48,TAMY=72,ALTURACOLLIDER=48,MSCDDASH=300,SCREENRESX,SCREENRESY;
    public final float VELOCIDAD=(float)0.3;
    private Rectangle hitbox;
    private double movAcumx=(double)0.0,movAcumy=(double)0.0;
    private int msDash=150,rangoDash=300,cdDash=0;
    private int targetDashX,targetDashY;
    
    

    public int getX() {
        return posx;
    }
    public int getnewX() {
        return newx;
    }
    public int getnewY() {
        return newy;
    }
    public boolean mirandoD(){
        return mirandoD;
    }
    public void setX(int posx) {
        this.posx = posx;
    }
    public void addX(int incx){
        
        this.newx = posx+incx;
        
    }
    public void addY(int incy){
        this.newy = posy+incy;
        
    }
    public int getY() {
        return posy;
    }

    public void setY(int posy) {
        this.posy = posy;
    }
    
    public Player(String rutasprites,int resx, int resy){
    try{    
        sprites = new SpriteSheet(rutasprites,TAMX,TAMY);
        noanim=new Animation();
        noanim.addFrame(sprites.getSprite(0,0), 150);
        
        idle=new Animation();
        for (int i = 0; i < 4; i++) {
            idle.addFrame(sprites.getSprite(i,1), 150);
        }
        
        run=new Animation();
        for (int i = 0; i < 4; i++) {
            run.addFrame(sprites.getSprite(i,2), 150);
        }
        jump=new Animation();
        for (int i = 0; i < 7; i++) {
            jump.addFrame(sprites.getSprite(i,3), 150);
        }
        
        idlei=new Animation();
        for (int i = 0; i < 4; i++) {
            idlei.addFrame(sprites.getSprite(i,1).getFlippedCopy(true, false), 150);
        }
        
        runi=new Animation();
        for (int i = 0; i < 4; i++) {
            runi.addFrame(sprites.getSprite(i,2).getFlippedCopy(true, false), 150);
        }
        jumpi=new Animation();
        for (int i = 0; i < 7; i++) {
            jumpi.addFrame(sprites.getSprite(i,3).getFlippedCopy(true, false), 150);
        }
        
    }
    catch(SlickException e){System.out.print(e);}
        corriendo = false;
        atacando = false;
        mirandoD=true;
        SCREENRESX=resx;
        SCREENRESY=resy;
        newx=posx=resx/2-TAMX/2;
        newy=posy=resy/2-TAMY/2;
        System.out.println("posx="+posx+",posy="+posy);
        hitbox=new Rectangle(posx,posy,TAMX,TAMY);
    
    }
    public boolean isCorriendo(){
        return corriendo;
    }
    public int getTargetDashX(){
        return targetDashX;
    }
    public int getTargetDashY(){
        return targetDashY;
    }
    public Animation getAnim(String nombre){
        switch(nombre){
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
    public void setCorriendo(){
        corriendo=true;
    }
    public void setDash(int ax, int ay){
        System.out.println("cd : "+cdDash);
        if(!atacando&&cdDash==0){
            atacando=true;
            int difx=ax-(this.posx+(TAMX/2));
            int dify=ay-(this.posy+(TAMY/2));
            int difcuadrados = (difx*difx)+(dify*dify);
            double dist = Math.sqrt((double)difcuadrados);
            if (dist>rangoDash){
                targetDashX=this.posx+(int)Math.round(difx*rangoDash/dist);
                targetDashY=this.posy+(int)Math.round(dify*rangoDash/dist);

            }
            else{
                targetDashX=ax;
                targetDashY=ay;
            }
        }
        
    }
    
    public boolean isDash(){
        return atacando;
    }
    public void setIdle(){
        corriendo=false;
    }
    public double getMaxstep(int delta){
        if (atacando==true){
            return rangoDash*delta/msDash;
        }
        else if(corriendo==true) {
            return VELOCIDAD*delta;
        }
        else{
            return VELOCIDAD*delta;
        }
    }
    public void calcNuevaPos(/*int ax, int ay, a*/int delta){
        if(!this.tocaRaton(Mouse.getX(),SCREENRESY-Mouse.getY())&&this.isCorriendo()){
            
            int ax=Mouse.getX();
            int ay=SCREENRESY-Mouse.getY();
            
            double maxstep = getMaxstep(delta);
            int difx=ax-(this.posx+(TAMX/2));
            int dify=ay-(this.posy+(TAMY/2));
            int difcuadrados = (difx*difx)+(dify*dify);
            double dist = Math.sqrt((double)difcuadrados);
            if((difx>0)){
                mirandoD=true;
            }
            if((difx<0)){
                mirandoD=false;
            }
            if(dist>(maxstep)){
                double incx = (difx*maxstep/dist)+movAcumx;
                
                int intincx = (int)Math.round(incx);
                movAcumx=incx-intincx;
                this.newx+=intincx;
                double incy = (dify*maxstep/dist)+movAcumy;
                int intincy = (int)Math.round(incy);
                movAcumy=incy-intincy;
                this.newy+=intincy;
                
            }
            else{
                
                this.newx+=difx;
                this.newy+=dify;
                
                
                
            }
        }
        
        
    }
    public void updPosX(){
        
        posx=newx;
    }
    public void updPosY(){
        posy=newy;
        
    }
    public void resetX(){
        newx=posx;
        movAcumx=0;
        atacando=false;
    }
    public void resetY(){
        newy=posy;
        movAcumy=0;
        atacando=false;
    }
    
    
    public void updHitbox(){
        hitbox.setX(this.posx);
        hitbox.setY(this.posy);
    }
    public boolean tocaRaton(int rx,int ry){
        
        return (rx>=posx)&&(rx<=(posx+TAMX))&&(ry>=posy)&&(ry<=(posy+TAMY));
    }
    public void lowerCDs(int delta){
        if(cdDash>delta){
            cdDash-=delta;
        }
        else{
            cdDash=0;
        }
    }
    
    
}
