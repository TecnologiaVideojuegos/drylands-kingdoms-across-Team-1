/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapasprocedurales;



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
        int altomapa=5,anchomapa=10,salas=10;
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
        Map pasillocruz = new Map(anchocelda,altocelda);
        Map pasillover = new Map(anchocelda,altocelda);
        Map pasillohor = new Map(anchocelda,altocelda);
        Map pasilloT = new Map(anchocelda,altocelda);
        Map pasilloTder = new Map(anchocelda,altocelda);
        Map pasilloTiz = new Map(anchocelda,altocelda);
        Map pasilloinfder = new Map(anchocelda,altocelda);
        Map pasilloinfiz = new Map(anchocelda,altocelda);
        Map pasilloTreves = new Map(anchocelda,altocelda);
        Map pasillosupder = new Map(anchocelda,altocelda);
        Map pasillosupiz = new Map(anchocelda,altocelda);
        //Cargamos los presets
        
        try{
            TMXMapReader lector = new TMXMapReader();
            salaabierta=lector.readMap("ficheros/salaabierta.tmx");
            salacerrada = lector.readMap("ficheros/salacerrada.tmx");
            pasillocruz=lector.readMap("ficheros/pasillocruz.tmx");
            pasillover=lector.readMap("ficheros/pasillover.tmx");
            pasillohor=lector.readMap("ficheros/pasillohor.tmx");
            pasilloT=lector.readMap("ficheros/pasilloT.tmx");
            pasilloTder=lector.readMap("ficheros/pasilloTder.tmx");
            pasilloTiz=lector.readMap("ficheros/pasilloTiz.tmx");
            pasilloinfder=lector.readMap("ficheros/pasilloinfder.tmx");
            pasilloinfiz=lector.readMap("ficheros/pasilloinfiz.tmx");
            pasilloTreves=lector.readMap("ficheros/pasilloTreves.tmx");
            pasillosupder=lector.readMap("ficheros/pasillosupder.tmx");
            pasillosupiz=lector.readMap("ficheros/pasillosupiz.tmx");
            
            
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
                    int combinacion=0;
                    if(mapalogico[j][i].getUp())    combinacion+=8;
                    if(mapalogico[j][i].getDown())    combinacion+=4;
                    if(mapalogico[j][i].getRight())    combinacion+=2;
                    if(mapalogico[j][i].getLeft())    combinacion+=1;
                       
                         switch(combinacion){
                            case 15://Cruz
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasillocruz.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasillocruz.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 7://T=7
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasilloT.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasilloT.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 14://Tder=14
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasilloTder.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasilloTder.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 11://Treves=11
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasilloTreves.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasilloTreves.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 13://Tiz=13
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasilloTiz.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasilloTiz.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 6://supiz=6
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasillosupiz.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasillosupiz.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 5://supder=5
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasillosupder.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasillosupder.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 9://infder=9
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasilloinfder.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasilloinfder.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 10://infiz=10
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasilloinfiz.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasilloinfiz.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 12://ver=12
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasillover.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasillover.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
                                    }
                                }
                                break;
                            case 3:
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        try{
                                            Tile tilecopiasuelo = new Tile();


                                            tilecopiasuelo.setTileSet(tileset);
                                            tilecopiasuelo.setId(((TileLayer)pasillohor.getLayer(0)).getTileAt(x, y).getId());
                                            suelo.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                        }catch(Exception e){}
                                        try{

                                            Tile tilecopiapared = new Tile();



                                            tilecopiapared.setTileSet(tileset);
                                            tilecopiapared.setId(((TileLayer)pasillohor.getLayer(1)).getTileAt(x, y).getId());
                                            paredes.setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiapared);

                                        }catch(Exception e){}
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
