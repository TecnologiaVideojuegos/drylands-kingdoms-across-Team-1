/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapasprocedurales;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.io.TMXMapWriter;
import org.mapeditor.util.BasicTileCutter;

import java.io.IOException;

/**
 *
 * @author FairLight
 */
public class ProceduralesMain {
    public static void main(String[] argv) {
        int altomapa=5,anchomapa=10,salas=5;
        int altocelda=15,anchocelda=15;
        Celula[][] mapalogico = MetodosProcedural.GenerarMapaLogico(anchomapa, altomapa, salas);
        MetodosProcedural.mostrarMapa(mapalogico, anchomapa, altomapa);
        
        
        //Creamos el mapagrafico, con las medidas
        Map mapagrafico = new Map(anchomapa*anchocelda,altomapa*altocelda);
        TileSet tileset = new TileSet();
        try{
            tileset.importTileBitmap("ficheros/0x72_16x16DungeonTileset.v4.png", new BasicTileCutter(16,16,0,0)); //importamos el tile
            tileset.setFirstgid(1);
            tileset.setName("0x72_16x16DungeonTileset.v4");
        }
        catch(IOException e){
            System.out.print("Error al abrir el fichero");
        }
        mapagrafico.addTileset(tileset);
        
        
        //Creamos las capas
        TileLayer suelo = new TileLayer(anchomapa*anchocelda,altomapa*altocelda);
        TileLayer paredes = new TileLayer(anchomapa*anchocelda,altomapa*altocelda);
        suelo.setName("suelo");
        paredes.setName("paredes");
        
        Map salaabierta = new Map(anchocelda,altocelda);
        Map salacerrada = new Map(anchocelda,altocelda);
        Map pasilloabierto = new Map(anchocelda,altocelda);
        Map pasillocerrado = new Map(anchocelda,altocelda);
        //Cargamos los presets
        
        try{
            TMXMapReader lector = new TMXMapReader();
            salaabierta=lector.readMap("ficheros/salaabierta.tmx");
            salacerrada = lector.readMap("ficheros/salacerrada.tmx");
            pasilloabierto=lector.readMap("ficheros/pasillocruz.tmx");
            pasillocerrado=lector.readMap("ficheros/pasillocerrado.tmx");
        }
        catch(Exception e){
            System.out.print("Error al abrir los presets");
            e.printStackTrace(System.out);
        }
        
        //Colocamos los presets
        for(int i=0;i<altomapa;i++){
            for(int j=0;j<anchomapa;j++){
                
                if("sala".equals(mapalogico[j][i].getTipo())){
                    //Trasladamos la sala
                    for (int y = 0; y < altocelda; y++) {
                        for (int x = 0; x < anchocelda; x++) {
                            
                            try{
                                Tile tilecopiasuelo = new Tile();
                                
                                
                                tilecopiasuelo.setTileSet(tileset);
                                tilecopiasuelo.setId(((TileLayer)salacerrada.getLayer(0)).getTileAt(x, y).getId());
                                suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);
                                
                                
                              
                            }catch(Exception e){}
                            try{
                                
                                Tile tilecopiapared = new Tile();
                                
                                
                                
                                tilecopiapared.setTileSet(tileset);
                                tilecopiapared.setId(((TileLayer)salacerrada.getLayer(1)).getTileAt(x, y).getId());
                                paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);
                              
                            }catch(Exception e){}
                        }
                    }
                    //Comprobamos las conexiones
                    if(mapalogico[j][i].getUp()){
                        for (int y = 0; y < 4; y++) {
                            for (int x = 0; x < anchocelda; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)salaabierta.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                    if(mapalogico[j][i].getDown()){
                        for (int y = (altocelda-4); y < altocelda; y++) {
                            for (int x = 0; x < anchocelda; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)salaabierta.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                    if(mapalogico[j][i].getLeft()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = 0; x < 4; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)salaabierta.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                    if(mapalogico[j][i].getRight()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = (anchocelda-4); x < anchocelda; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)salaabierta.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                }
                else if("pasillo".equals(mapalogico[j][i].getTipo())){
                    for (int y = 0; y < altocelda; y++) {
                        for (int x = 0; x < anchocelda; x++) {
                            
                            try{
                                Tile tilecopiasuelo = new Tile();
                                
                                
                                tilecopiasuelo.setTileSet(tileset);
                                tilecopiasuelo.setId(((TileLayer)pasillocerrado.getLayer(0)).getTileAt(x, y).getId());
                                suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);
                                
                                
                              
                            }catch(Exception e){}
                            try{
                                
                                Tile tilecopiapared = new Tile();
                                
                                
                                
                                tilecopiapared.setTileSet(tileset);
                                tilecopiapared.setId(((TileLayer)pasillocerrado.getLayer(1)).getTileAt(x, y).getId());
                                paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);
                              
                            }catch(Exception e){}
                        }
                    }
                    if(mapalogico[j][i].getUp()){
                        for (int y = 0; y < 7; y++) {
                            for (int x = 5; x < anchocelda-5; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)pasilloabierto.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)pasilloabierto.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                    if(mapalogico[j][i].getDown()){
                        for (int y = (altocelda-7); y < altocelda; y++) {
                            for (int x = 5; x < anchocelda-5; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)pasilloabierto.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)pasilloabierto.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                    if(mapalogico[j][i].getLeft()){
                        for (int y = 5; y < altocelda-5; y++) {
                            for (int x = 0; x < 7; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)pasilloabierto.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)pasilloabierto.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                    if(mapalogico[j][i].getRight()){
                        for (int y = 5; y < altocelda-5; y++) {
                            for (int x = (anchocelda-7); x < anchocelda; x++) {

                                try{
                                    Tile tilecopiasuelo = new Tile();


                                    tilecopiasuelo.setTileSet(tileset);
                                    tilecopiasuelo.setId(((TileLayer)pasilloabierto.getLayer(0)).getTileAt(x, y).getId());
                                    suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                }catch(Exception e){suelo.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                try{

                                    Tile tilecopiapared = new Tile();



                                    tilecopiapared.setTileSet(tileset);
                                    tilecopiapared.setId(((TileLayer)pasilloabierto.getLayer(1)).getTileAt(x, y).getId());
                                    paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                }catch(Exception e){paredes.setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                            }
                        } 
                    }
                }
                                            
            }
        }
        
        
        //Añadimos las capas al mapa
        mapagrafico.addLayer(suelo);
        mapagrafico.addLayer(paredes);
        
        //Escribimos el fichero
        TMXMapWriter writer = new TMXMapWriter();
        writer.settings.layerCompressionMethod = "gzip" ;
        try{
            writer.writeMap(mapagrafico, "ficheros/salida.tmx");
        }
        catch(IOException e){
            System.out.print("Error al escribir el fichero");
        }
       
    }
}
