/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapasprocedurales;

/**
 *
 * @author FairLight
 */
public class ProceduralesMain {
    public static void main(String[] argv) {
        int alto=5,ancho=10,salas=5;
        Celula[][] mapa = MetodosProcedural.GenerarMapaLogico(ancho, alto, salas);
        MetodosProcedural.mostrarMapa(mapa, ancho, alto);
    }
}
