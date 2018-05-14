/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EstadosJuego.CoreGame.drylands;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author FairLight
 */
public class Mapa implements Serializable {
    private final int tamSalaX = 27, tamSalaY = 17;
    private final int MSCAMARA = 500;
    private final double PORCENTAJEX = 0.05, PORCENTAJEY = 0.05;
    private TiledMap mapa;
    private TiledMap info;
    private float offsetX, offsetY;
    private int tamTileX, tamTileY;
    private int contadorCamara = 0;
    private int SCREENRESX, SCREENRESY;
    private ArrayList<Punto> listaCentros;
    private float centroSalaX;

    public float getCentroSalaX() {
        return centroSalaX;
    }

    public float getCentroSalaY() {
        return centroSalaY;
    }

    private float centroSalaY;
    private BitSet bitmap;

    private float escalatab;

    public Mapa(String ruta, String rutainfo, String dependencias, int SCREENRESX, int SCREENRESY) throws SlickException {

        mapa = new TiledMap(ruta, dependencias);
        info = new TiledMap(rutainfo, dependencias);

        this.SCREENRESX = SCREENRESX;
        this.SCREENRESY = SCREENRESY;

        offsetX = 10000;
        offsetY = 10000;
        tamTileX = mapa.getTileWidth();
        tamTileY = mapa.getTileHeight();
        centroSalaX = centroSalaY = 0;
        listaCentros = new ArrayList<Punto>();
        float escalaw = (float) SCREENRESX / (float) (mapa.getWidth() * mapa.getTileWidth());
        float escalah = (float) SCREENRESY / (float) (mapa.getHeight() * mapa.getTileHeight());
        escalatab = Math.min(escalah, escalaw);
        bitmap = new BitSet(mapa.getWidth() * mapa.getHeight());

        bitmap.clear();


    }

    public float getAbsMouseX() {
        return Mouse.getX() - offsetX;
    }

    public float getAbsMouseY() {
        return SCREENRESY - Mouse.getY() - offsetY;
    }

    public ArrayList<Punto> getListaCentros() {
        return listaCentros;
    }

    public void setListaCentros(ArrayList<Punto> listaCentros) {
        this.listaCentros = listaCentros;
    }

    public TiledMap getTiled() {
        return mapa;
    }

    public float getOffX() {
        return offsetX;
    }

    public void setOffX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffY() {
        return offsetY;
    }

    public void setOffY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void render() {


        mapa.render(Math.round(offsetX), Math.round(offsetY), 0, 0, mapa.getWidth(), mapa.getHeight(), false);


    }
    public void renderAt(Mapa map2) {
        mapa.render(Math.round(map2.getOffX()), Math.round(map2.getOffY()), 0, 0, mapa.getWidth(), mapa.getHeight(), false);


    }

    public void renderTab() {


        info.render((int) ((SCREENRESX - escalatab * (mapa.getWidth() * mapa.getTileWidth())) / 2), (int) ((SCREENRESY - escalatab * (mapa.getHeight() * mapa.getTileHeight())) / 2), 0, 0, mapa.getWidth(), mapa.getHeight(), false);


    }

    public float getEscalaTab() {

        return escalatab;

    }

    public boolean checkColX(Personaje player) {
        boolean colUpDer, colUpIz, colDownDer, colDownIz;

        colDownIz = hayMuro(player.getnewX(), player.getY() + player.TAMY);
        colUpIz = hayMuro(player.getnewX(), player.getY() + player.ALTURACOLLIDER);
        colDownDer = hayMuro(player.getnewX() + player.TAMX, player.getY() + player.TAMY);
        colUpDer = hayMuro(player.getnewX() + player.TAMX, player.getY() + player.ALTURACOLLIDER);

        return colUpDer || colUpIz || colDownDer || colDownIz;
    }

    public boolean checkColY(Personaje player) {
        boolean colUpDer, colUpIz, colDownDer, colDownIz;

        colDownIz = hayMuro(player.getX(), player.getnewY() + player.TAMY);
        colUpIz = hayMuro(player.getX(), player.getnewY() + player.ALTURACOLLIDER);
        colDownDer = hayMuro(player.getX() + player.TAMX, player.getnewY() + player.TAMY);
        colUpDer = hayMuro(player.getX() + player.TAMX, player.getnewY() + player.ALTURACOLLIDER);

        return colUpDer || colUpIz || colDownDer || colDownIz;
    }
    public boolean checkColCombateX(Personaje player) {
        boolean colUpDer, colUpIz, colDownDer, colDownIz;

        colDownIz = hayMuroCombate(player.getnewX(), player.getY() + player.TAMY);
        colUpIz = hayMuroCombate(player.getnewX(), player.getY() + player.ALTURACOLLIDER);
        colDownDer = hayMuroCombate(player.getnewX() + player.TAMX, player.getY() + player.TAMY);
        colUpDer = hayMuroCombate(player.getnewX() + player.TAMX, player.getY() + player.ALTURACOLLIDER);

        return colUpDer || colUpIz || colDownDer || colDownIz;
    }

    public boolean checkColCombateY(Personaje player) {
        boolean colUpDer, colUpIz, colDownDer, colDownIz;

        colDownIz = hayMuroCombate(player.getX(), player.getnewY() + player.TAMY);
        colUpIz = hayMuroCombate(player.getX(), player.getnewY() + player.ALTURACOLLIDER);
        colDownDer = hayMuroCombate(player.getX() + player.TAMX, player.getnewY() + player.TAMY);
        colUpDer = hayMuroCombate(player.getX() + player.TAMX, player.getnewY() + player.ALTURACOLLIDER);

        return colUpDer || colUpIz || colDownDer || colDownIz;
    }

    private boolean hayMuro(float x, float y) {
        try {

            return (mapa.getTileId((Math.round(x)) / tamTileX, (Math.round(y)) / tamTileY, 1) != 0);

        } catch (Exception e) {
            return false;
        }
    }
    private boolean hayMuroCombate(float x, float y) {
        try {

            return (mapa.getTileId((Math.round(x)) / tamTileX, (Math.round(y)) / tamTileY, 5) != 0);

        } catch (Exception e) {
            return false;
        }
    }

    private boolean esSala(float x, float y) {
        try {

            return (mapa.getTileId((Math.round(x)) / tamTileX, (Math.round(y)) / tamTileY, 2) != 0);

        } catch (Exception e) {
            return false;
        }
    }

    public boolean playerEnInicio(Player player) {
        float x = player.getX();
        float y = player.getY();
        try {

            return (mapa.getTileId((Math.round(x)) / tamTileX, (Math.round(y)) / tamTileY, 4) != 0);

        } catch (Exception e) {
            return false;
        }
    }

    public boolean playerEnFinal(Player player) {
        float x = player.getX();
        float y = player.getY();
        try {

            return (mapa.getTileId((Math.round(x)) / tamTileX, (Math.round(y)) / tamTileY, 3) != 0);

        } catch (Exception e) {
            return false;
        }
    }

    public boolean playerEnSala() {
        return (centroSalaY != 0) && (centroSalaX != 0);
    }

    public boolean playerEnSalaNueva() {
        for (Punto punto : listaCentros) {
            if ((punto.getX() == centroSalaX) && (punto.getY() == centroSalaY))
                return false;

        }
        return true;
    }

    public void actListaCentros() {
        listaCentros.add(new Punto(centroSalaX, centroSalaY));
    }

    public void actCamara(int delta, Player player) {

        for (int x = (int) -offsetX / tamTileX; x < (int) -offsetX / tamTileX + SCREENRESX / tamTileX; x++) {
            for (int y = (int) -offsetY / tamTileY; y < (int) -offsetY / tamTileY + SCREENRESY / tamTileY; y++) {
                try {
                    info.setTileId(x, y, 0, mapa.getTileId(x, y, 0));
                    bitmap.set(x * mapa.getHeight() + y);
                }catch (Exception e){}

            }
        }

        if (esSala(player.getX() + player.TAMX / 2, player.getY() + player.TAMY / 2)) {


            if (centroSalaX == 0 && centroSalaY == 0) {//buscamos el centro

                float startx = player.getX() + player.TAMX / 2;
                float starty = player.getY() + player.TAMY / 2;
                int tamxder = 0, tamxiz = 0, tamyup = 0, tamydown = 0, tilesX = 0, tilesY = 0;

                //ejex
                while (esSala(startx + tamxder * tamTileX, starty)) {
                    tamxder++;
                    tilesX++;

                }
                while (esSala(startx - tamxiz * tamTileX, starty)) {
                    tamxiz++;
                    tilesX++;
                }
                while (esSala(startx, starty + tamydown * tamTileY)) {
                    tamydown++;
                    tilesY++;
                }
                while (esSala(startx, starty - tamyup * tamTileY)) {
                    tamyup++;
                    tilesY++;
                }
                //el centro relativo desde la esquina superior izquierda + distancia desde el centro del jugador a esa esquina
                centroSalaX = ((int) (startx / tamTileX) * tamTileX) - tamxiz * tamTileX + (tilesX + 1) * tamTileX / 2;
                centroSalaY = ((int) (starty / tamTileY) * tamTileY) - tamyup * tamTileY + (tilesY + 1) * tamTileY / 2;


            }
            if (((centroSalaX + offsetX) > (SCREENRESX * (0.5 - PORCENTAJEX))) && ((centroSalaX + offsetX) < (SCREENRESX * (0.5 + PORCENTAJEX))) && ((centroSalaY + player.TAMY / 2 + offsetY) > (SCREENRESY * (0.5 - PORCENTAJEY))) && ((centroSalaY + player.TAMY / 2 + offsetY) < (SCREENRESY * (0.5 + PORCENTAJEY)))) {
                if ((contadorCamara - delta) < 0)
                    contadorCamara = 0;
                else
                    contadorCamara -= delta;

            } else {

                if ((contadorCamara + delta) > MSCAMARA)
                    contadorCamara += delta / 5;
                else
                    contadorCamara += delta;
            }
            float maxDesp = player.getMaxstep(delta) * contadorCamara / MSCAMARA;
            float difx = (centroSalaX + offsetX) - (SCREENRESX / 2);
            float dify = (centroSalaY + offsetY) - (SCREENRESY / 2);
            float difcuadrados = (difx * difx) + (dify * dify);
            float dist = (float) Math.sqrt(difcuadrados);
            if (dist > (maxDesp)) {
                float incx = (difx * maxDesp / dist);

                offsetX -= incx;

                float incy = (dify * maxDesp / dist);

                offsetY -= incy;

            } else {

                offsetX -= difx;

                offsetY -= dify;

            }
        } else {
            centroSalaX = centroSalaY = 0;
            if (((player.getX() + player.TAMX / 2 + offsetX) > (SCREENRESX * (0.5 - PORCENTAJEX))) && ((player.getX() + player.TAMX / 2 + offsetX) < (SCREENRESX * (0.5 + PORCENTAJEX))) && ((player.getY() + player.TAMY / 2 + offsetY) > (SCREENRESY * (0.5 - PORCENTAJEY))) && ((player.getY() + player.TAMY / 2 + offsetY) < (SCREENRESY * (0.5 + PORCENTAJEY)))) {
                if ((contadorCamara - delta) < 0)
                    contadorCamara = 0;
                else
                    contadorCamara -= delta;

            } else {

                if ((contadorCamara + delta) > MSCAMARA)
                    contadorCamara += delta / 5;
                else
                    contadorCamara += delta;
            }
            if (contadorCamara > 0) {
                float maxDesp = player.getMaxstep(delta) * contadorCamara / MSCAMARA;
                float difx = (player.getX() + player.TAMX / 2 + offsetX) - (SCREENRESX / 2);
                float dify = (player.getY() + player.TAMY / 2 + offsetY) - (SCREENRESY / 2);
                float difcuadrados = (difx * difx) + (dify * dify);
                float dist = (float) Math.sqrt(difcuadrados);
                if (dist > (maxDesp)) {
                    float incx = (difx * maxDesp / dist);

                    offsetX -= incx;

                    float incy = (dify * maxDesp / dist);

                    offsetY -= incy;


                } else {

                    offsetX -= difx;

                    offsetY -= dify;


                }

            }

        }

        if (offsetX > 0) {
            offsetX=0;
        }
        else if (offsetX<-((mapa.getWidth())*mapa.getTileWidth()-SCREENRESX)){
            offsetX=-((mapa.getWidth())*mapa.getTileWidth()-SCREENRESX);
        }
        if (offsetY > 0) {
            offsetY=0;
        }
        else if (offsetY<-(mapa.getHeight()*mapa.getTileHeight()-SCREENRESY)){
            offsetY=-(mapa.getHeight()*mapa.getTileHeight()-SCREENRESY);
        }
    }

    public Punto getRandinSala() {
        float x, y;
        x = ThreadLocalRandom.current().nextInt((int) centroSalaX - (tamSalaX / 2 - 3) * tamTileX, (int) centroSalaX + (tamSalaX / 2 - 3) * tamTileX);
        y = ThreadLocalRandom.current().nextInt((int) centroSalaY - (tamSalaY / 2 - 3) * tamTileY, (int) centroSalaY + (tamSalaY / 2 - 3) * tamTileY);
        return new Punto(x, y);
    }

    public boolean camaraFijada() {
        return ((centroSalaX + offsetX) == (SCREENRESX / 2)) && ((centroSalaY + offsetY) == (SCREENRESY / 2));
    }

    public Punto getInicio() {
        boolean encontrado = false;

        int x = 0, y = 0;
        for (x = 0; (x < mapa.getWidth()) && !encontrado; x++) {
            for (y = 0; (y < mapa.getHeight()) && !encontrado; y++) {

                if (mapa.getTileId(x, y, 4) != 0) {
                    System.out.println("rompo bucle");
                    encontrado = true;
                }


            }

        }

        return new Punto((float) (x + (tamSalaX - 4) / 2) * tamTileX, (float) (y + (tamSalaY - 4) / 2) * tamTileY);

    }

    public String getInfoBitmap() {

        byte[] encoded = Base64.getEncoder().encode(bitmap.toByteArray());
        System.out.println(new String(encoded));
        return new String(encoded);

    }

    public void setInfoBitmap(String cadena) {
        System.out.println(cadena);
        byte[] decoded = Base64.getDecoder().decode(cadena);
        bitmap = BitSet.valueOf(decoded);

    }

    public void refrescarInfo() {
        int i = 0;
        for (int x = 0; x < mapa.getWidth(); x++) {
            for (int y = 0; y < mapa.getHeight(); y++) {

                if (bitmap.get(i) == true) {
                    info.setTileId(x, y, 0, mapa.getTileId(x, y, 0));
                }
                i++;

            }
        }
    }

    public void forzarCentro(Player player) {
        float difx;
        float dify;
        if(centroSalaX!=0&&centroSalaY!=0){
            difx = (centroSalaX + offsetX) - (SCREENRESX / 2);
            dify = (centroSalaY + offsetY) - (SCREENRESY / 2);


        }
        else {
            difx = (player.getX() + player.TAMX / 2 + offsetX) - (SCREENRESX / 2);
            dify = (player.getY() + player.TAMY / 2 + offsetY) - (SCREENRESY / 2);
        }



        offsetX -= difx;

        offsetY -= dify;

    }
}



