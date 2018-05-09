/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MapasProc;



import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.io.TMXMapWriter;
import org.mapeditor.util.BasicTileCutter;
import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author FairLight
 */
public class ProceduralesMain {

    public static void generarMapa(int altomapa,int anchomapa,int salas,String nombremapa,String nombremapainfo) {

        int altocelda=17,anchocelda=27;
        int numcapas =7;
        Celula[][] mapalogico = MetodosProcedural.GenerarMapaLogico(anchomapa, altomapa, salas);
        MetodosProcedural.mostrarMapa(mapalogico, anchomapa, altomapa);
        
        
        //Creamos el mapagrafico, con las medidas
        Map mapagrafico = new Map(anchomapa*anchocelda,altomapa*altocelda);
        TileSet tileset = new TileSet();
        try{
            tileset.importTileBitmap("ficheros/MapasX3/tileModificadox3.png", new BasicTileCutter(48,48,0,0)); //importamos el tile
            tileset.setFirstgid(1);
            tileset.setName("TilesetX3");
        }
        catch(IOException e){
            System.out.print("Error al abrir el fichero");
        }
        mapagrafico.addTileset(tileset);
        
        
        //Creamos las capas
        ArrayList<TileLayer> listacapas = new ArrayList<TileLayer>();
        for (int i = 0; i < numcapas; i++) {
            listacapas.add( new TileLayer(anchomapa*anchocelda,altomapa*altocelda));
        }
        
        /*suelo.setName("suelo");
        paredes.setName("paredes");*/
        Map inicioabierta = new Map(anchocelda,altocelda);
        Map iniciocerrada = new Map(anchocelda,altocelda);
        Map finalabierta = new Map(anchocelda,altocelda);
        Map finalcerrada = new Map(anchocelda,altocelda);
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
            inicioabierta=lector.readMap("ficheros/MapasX3/Salas/SalaInicial.tmx");
            iniciocerrada = lector.readMap("ficheros/MapasX3/Salas/salaInicialCerrada.tmx");
            finalabierta=lector.readMap("ficheros/MapasX3/Salas/salaBoss.tmx");
            finalcerrada = lector.readMap("ficheros/MapasX3/Salas/salaBossCerrada.tmx");
            salaabierta=lector.readMap("ficheros/MapasX3/Salas/salaNormal.tmx");
            salacerrada = lector.readMap("ficheros/MapasX3/Salas/salaNormalCerrada.tmx");
            
            pasillocruz=lector.readMap("ficheros/MapasX3/Pasillos/pasilloCruz.tmx");
            pasillover=lector.readMap("ficheros/MapasX3/Pasillos/pasilloVer.tmx");
            pasillohor=lector.readMap("ficheros/MapasX3/Pasillos/pasilloHor.tmx");
            pasilloT=lector.readMap("ficheros/MapasX3/Pasillos/pasilloT.tmx");
            pasilloTder=lector.readMap("ficheros/MapasX3/Pasillos/pasilloTder.tmx");
            pasilloTiz=lector.readMap("ficheros/MapasX3/Pasillos/pasilloTIz.tmx");
            pasilloinfder=lector.readMap("ficheros/MapasX3/Pasillos/pasilloInfDer.tmx");
            pasilloinfiz=lector.readMap("ficheros/MapasX3/Pasillos/pasilloInfIz.tmx");
            pasilloTreves=lector.readMap("ficheros/MapasX3/Pasillos/pasilloTreves.tmx");
            pasillosupder=lector.readMap("ficheros/MapasX3/Pasillos/pasilloSupDer.tmx");
            pasillosupiz=lector.readMap("ficheros/MapasX3/Pasillos/pasilloSupIz.tmx");
            
            
        }
        catch(Exception e){
            System.out.print("Error al abrir los presets");
            e.printStackTrace(System.out);
        }
        
        //Colocamos los presets
        for(int i=0;i<altomapa;i++){
            for(int j=0;j<anchomapa;j++){
                
                if("final".equals(mapalogico[j][i].getTipo())){
                    //Trasladamos la sala
                    for (int y = 0; y < altocelda; y++) {
                        for (int x = 0; x < anchocelda; x++) {
                            for (int k = 0; k < numcapas; k++) {
                                try{
                                Tile tilecopiasuelo = new Tile();
                                
                                
                                tilecopiasuelo.setTileSet(tileset);
                                tilecopiasuelo.setId(((TileLayer)finalcerrada.getLayer(k)).getTileAt(x, y).getId());
                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);
                                
                                
                              
                                }catch(Exception e){}
                            }
                            
                            
                         
                        }
                    }
                    //Comprobamos las conexiones
                    if(mapalogico[j][i].getUp()){
                        for (int y = 0; y < 4; y++) {
                            for (int x = 0; x < anchocelda; x++) {
                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)finalabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }

                              
                            }
                        } 
                    }
                    if(mapalogico[j][i].getDown()){
                        for (int y = (altocelda-4); y < altocelda; y++) {
                            for (int x = 0; x < anchocelda; x++) {
                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)finalabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }
                            }
                        } 
                    }
                    if(mapalogico[j][i].getLeft()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = 0; x < 4; x++) {

                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)finalabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }
                            }
                        } 
                    }
                    if(mapalogico[j][i].getRight()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = (anchocelda-4); x < anchocelda; x++) {

                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)finalabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }
                            }
                        } 
                    }
                }
                
                else if("inicio".equals(mapalogico[j][i].getTipo())){
                    //Trasladamos la sala
                    for (int y = 0; y < altocelda; y++) {
                        for (int x = 0; x < anchocelda; x++) {
                            for (int k = 0; k < numcapas; k++) {
                                try{
                                Tile tilecopiasuelo = new Tile();
                                
                                
                                tilecopiasuelo.setTileSet(tileset);
                                tilecopiasuelo.setId(((TileLayer)iniciocerrada.getLayer(k)).getTileAt(x, y).getId());
                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);
                                
                                
                              
                                }catch(Exception e){}
                            }
                            
                            
                         
                        }
                    }
                    //Comprobamos las conexiones
                    if(mapalogico[j][i].getUp()){
                        for (int y = 0; y < 4; y++) {
                            for (int x = 0; x < anchocelda; x++) {
                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)inicioabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }

                              
                            }
                        } 
                    }
                    if(mapalogico[j][i].getDown()){
                        for (int y = (altocelda-4); y < altocelda; y++) {
                            for (int x = 0; x < anchocelda; x++) {
                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)inicioabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }
                            }
                        } 
                    }
                    if(mapalogico[j][i].getLeft()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = 0; x < 4; x++) {

                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)inicioabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }
                            }
                        } 
                    }
                    if(mapalogico[j][i].getRight()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = (anchocelda-4); x < anchocelda; x++) {

                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)inicioabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }
                            }
                        } 
                    }
                }
                
                else if("sala".equals(mapalogico[j][i].getTipo())){
                    //Trasladamos la sala
                    for (int y = 0; y < altocelda; y++) {
                        for (int x = 0; x < anchocelda; x++) {
                            for (int k = 0; k < numcapas; k++) {
                                try{
                                Tile tilecopiasuelo = new Tile();
                                
                                
                                tilecopiasuelo.setTileSet(tileset);
                                tilecopiasuelo.setId(((TileLayer)salacerrada.getLayer(k)).getTileAt(x, y).getId());
                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);
                                
                                
                              
                                }catch(Exception e){}
                            }                         
                        }
                    }
                    //Comprobamos las conexiones
                    if(mapalogico[j][i].getUp()){
                        for (int y = 0; y < 4; y++) {
                            for (int x = 0; x < anchocelda; x++) {
                                
                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                }                               
                            }
                        } 
                    }
                    if(mapalogico[j][i].getDown()){
                        for (int y = (altocelda-4); y < altocelda; y++) {
                            for (int x = 0; x < anchocelda; x++) {
                                
                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                } 
                            }
                        } 
                    }
                    if(mapalogico[j][i].getLeft()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = 0; x < 4; x++) {

                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                } 
                            }
                        } 
                    }
                    if(mapalogico[j][i].getRight()){
                        for (int y = 0; y < altocelda; y++) {
                            for (int x = (anchocelda-4); x < anchocelda; x++) {

                                for (int k = 0; k < numcapas; k++) {
                                    try{
                                        Tile tilecopiasuelo = new Tile();


                                        tilecopiasuelo.setTileSet(tileset);
                                        tilecopiasuelo.setId(((TileLayer)salaabierta.getLayer(k)).getTileAt(x, y).getId());
                                        listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                    }catch(Exception e){listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, null);}
                                } 
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
                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasillocruz.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 7://T=7
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasilloT.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 14://Tder=14
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasilloTder.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 11://Treves=11
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasilloTreves.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 13://Tiz=13
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasilloTiz.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 6://supiz=6
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasillosupiz.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 5://supder=5
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {
                                        
                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasillosupder.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }

                                        
                                    }
                                }
                                break;
                            case 9://infder=9
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasilloinfder.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 10://infiz=10
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasilloinfiz.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 12://ver=12
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasillover.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                                break;
                            case 3:
                                for (int y = 0; y < altocelda; y++) {
                                    for (int x = 0; x < anchocelda; x++) {

                                        for (int k = 0; k < numcapas; k++) {
                                            try{
                                                Tile tilecopiasuelo = new Tile();


                                                tilecopiasuelo.setTileSet(tileset);
                                                tilecopiasuelo.setId(((TileLayer)pasillohor.getLayer(k)).getTileAt(x, y).getId());
                                                listacapas.get(k).setTileAt(j*anchocelda+x, i*altocelda+y, tilecopiasuelo);



                                            }catch(Exception e){}
                                        }
                                    }
                                }
                            
                         }
                    
                }
                                            
            }
        }
        
        
        //Añadimos las capas al mapa
        for (int i = 0; i < listacapas.size(); i++) {
            mapagrafico.addLayer(listacapas.get(i));
        
        }
        Map mapainfo = new Map(anchomapa*anchocelda,altomapa*altocelda);
        mapainfo.addTileset(tileset);
        mapainfo.addLayer(listacapas.get(listacapas.size()-1));
        
        //Escribimos el fichero
        TMXMapWriter writer = new TMXMapWriter();
        writer.settings.layerCompressionMethod = "gzip" ;
        try{
            writer.writeMap(mapagrafico, "ficheros/"+nombremapa+".tmx");
            writer.writeMap(mapainfo, "ficheros/"+nombremapainfo+".tmx");
        }
        catch(IOException e){
            System.out.print("Error al escribir el fichero");
        }
       
    }
}
