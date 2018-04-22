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
    private TiledMap mapa;
    private int numtilesRenderX,numtilesRenderY;
    private int offsetX,offsetY;
    private int tamTileX,tamTileY;
    
    public Mapa(String ruta,String dependencias,int SCREENRESX,int SCREENRESY) throws SlickException{
        
        mapa = new TiledMap(ruta,dependencias);
        
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
            
            return (mapa.getTileId((x+offsetX)/tamTileX, (y+offsetY)/tamTileY,1)!=0);
            
       }
       catch(Exception e){return false;}
    }
}
