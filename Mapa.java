/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.newdawn.slick.tests;
    import org.newdawn.slick.tiled.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;


/**
 *
 * @author FairLight
 */
public class Mapa {
    private final int MSCAMARA=500;
    private TiledMap mapa;
    private int numtilesRenderX,numtilesRenderY;
    private int offsetX,offsetY;
    private int tamTileX,tamTileY;
    private int contadorCamara=0;
    private int SCREENRESX,SCREENRESY;
    private double movAcumx=(double)0.0,movAcumy=(double)0.0;
    
    public Mapa(String ruta,String dependencias,int SCREENRESX,int SCREENRESY) throws SlickException{
        
        mapa = new TiledMap(ruta,dependencias);
        
        this.SCREENRESX=SCREENRESX;
        this.SCREENRESY=SCREENRESY;
        
        numtilesRenderX=(SCREENRESX/mapa.getTileWidth())+1;
        numtilesRenderY=(SCREENRESY/mapa.getTileHeight())+1;
        offsetX=0;
        offsetY=0;
        tamTileX=mapa.getTileWidth();
        tamTileY=mapa.getTileHeight();
        
    }
    public TiledMap getTiled(){
        return mapa;
    }
    public void render(){
        mapa.render(offsetX,offsetY,0,0,numtilesRenderX,numtilesRenderY);
    }
    public boolean checkColX(Player player){
        boolean colUpDer,colUpIz,colDownDer,colDownIz;
        
        colDownIz=hayMuro(player.getnewX(),player.getY()+player.TAMY);
        colUpIz=hayMuro(player.getnewX(),player.getY()+player.ALTURACOLLIDER);
        colDownDer=hayMuro(player.getnewX()+player.TAMX,player.getY()+player.TAMY);
        colUpDer=hayMuro(player.getnewX()+player.TAMX,player.getY()+player.ALTURACOLLIDER);
        
        return colUpDer||colUpIz||colDownDer||colDownIz;
    }
    public boolean checkColY(Player player){
        boolean colUpDer,colUpIz,colDownDer,colDownIz;
        
        colDownIz=hayMuro(player.getX(),player.getnewY()+player.TAMY);
        colUpIz=hayMuro(player.getX(),player.getnewY()+player.ALTURACOLLIDER);
        colDownDer=hayMuro(player.getX()+player.TAMX,player.getnewY()+player.TAMY);
        colUpDer=hayMuro(player.getX()+player.TAMX,player.getnewY()+player.ALTURACOLLIDER);
        
        return colUpDer||colUpIz||colDownDer||colDownIz;
    }
    private boolean hayMuro(int x, int y){
        try{
            
            return (mapa.getTileId((x-offsetX)/tamTileX, (y-offsetY)/tamTileY,1)!=0);
            
       }
       catch(Exception e){return false;}
    }
    public void actCamara(int delta,Player player){
        if(((player.getX()+player.TAMX/2)>(SCREENRESX*0.4))&&((player.getX()+player.TAMX/2)<(SCREENRESX*0.6))&&((player.getY()+player.TAMY/2)>(SCREENRESY*0.4))&&((player.getY()+player.TAMY/2)<(SCREENRESY*0.6))){
            contadorCamara=0;
            
        }
        else{
            
            if((contadorCamara+delta)>MSCAMARA)
                contadorCamara=MSCAMARA;
            else
                contadorCamara+=delta;
            
            double maxDesp=player.getMaxstep(delta)*contadorCamara/MSCAMARA;
            int difx=(player.getX()+player.TAMX/2)-(SCREENRESX/2);
            int dify=(player.getY()+player.TAMY/2)-(SCREENRESY/2);
            int difcuadrados = (difx*difx)+(dify*dify);
            double dist = Math.sqrt((double)difcuadrados);
            if(dist>(maxDesp)){
                double incx = (difx*maxDesp/dist)+movAcumx;

                int intincx = (int)Math.round(incx);
                movAcumx=incx-intincx;
                player.addX(-intincx);
                offsetX-=intincx;
                double incy = (dify*maxDesp/dist)+movAcumy;
                int intincy = (int)Math.round(incy);
                movAcumy=incy-intincy;
                player.addY(-intincy);
                offsetY-=intincy;
                System.out.println("contadorcamara="+contadorCamara+"\t\tmaxDesp="+maxDesp+"\t\tdifx="+difx+"\t\tdify="+dify+"\t\tdist="+dist);
                System.out.println("intincy="+intincy+", intincx="+intincx);
            
            }
            else{
            
                player.setX(player.getX()-difx);
                offsetX+=difx;

                player.setY(player.getY()-dify);
                offsetY+=dify;
            
                //atacando=false;
            System.out.println("contadorcamara="+contadorCamara+"\t\tmaxDesp="+maxDesp+"\t\tdifx="+difx+"\t\tdify="+dify+"\t\tdist="+dist);
            
            }
            
        }
    }
}