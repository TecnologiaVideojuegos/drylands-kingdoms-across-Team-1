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
        int altomapa=5,anchomapa=10,salas=5;
        int altocelda=20,anchocelda=20;
        Celula[][] mapalogico = MetodosProcedural.GenerarMapaLogico(anchomapa, altomapa, salas);
        MetodosProcedural.mostrarMapa(mapalogico, anchomapa, altomapa);
        
        
        //Creamos el mapagrafico, con las medidas
        Map mapagrafico = new Map(anchomapa*anchocelda,altomapa*altocelda);
        TileSet tileset = new TileSet();
        tileset.setTileWidth(16);
        tileset.setTileHeight(16);
        try{
            tileset.importTileBitmap("ficheros/minas.png", new BasicTileCutter(16,16,0,0)); //importamos el tile
        }
        catch(IOException e){
            System.out.print("Error al abrir el fichero");
        }
        mapagrafico.addTileset(tileset);
        
        
        //Creamos las capas
        TileLayer suelo = new TileLayer();
        TileLayer paredes = new TileLayer();
        suelo.setName("suelo");
        paredes.setName("paredes");
        
        
        
        
        //Añadimos las capas al mapa
        mapagrafico.addLayer(suelo);
        mapagrafico.addLayer(paredes);
        
        //Escribimos el fichero
        TMXMapWriter writer = new TMXMapWriter();
        writer.settings.layerCompressionMethod = "gzip" ;
        try{
            writer.writeMap(mapagrafico, "ficheros/fichero.tmx");
        }
        catch(IOException e){
            System.out.print("Error al escribir el fichero");
        }
       
    }
}
