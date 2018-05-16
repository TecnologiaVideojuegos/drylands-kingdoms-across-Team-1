package EstadosJuego.CoreGame.drylands;

import org.lwjgl.Sys;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.util.ArrayList;

public class Guardado {
    private SavedState partida_player;
    private SavedState partida_mapa;
    private SavedState partida_estado;

    public Guardado(String nombrepartida) throws SlickException {

        partida_player = new SavedState(nombrepartida + "player");
        partida_mapa = new SavedState(nombrepartida + "mapa");
        partida_estado = new SavedState(nombrepartida + "mapa");

    }

    public void guardarPlayer(Player player) throws IOException {
        partida_player.setNumber("posx", player.getX());
        partida_player.setNumber("posy", player.getY());
        partida_player.setNumber("vida", player.getVida());
        partida_player.setNumber("vel", player.getVel());
        partida_player.setNumber("dashcd", player.dash.getCdmax());
        partida_player.setNumber("dashrango", player.dash.getRango());


        partida_player.setString("estado","guardado");

        partida_player.save();

    }

    public void cargarPlayer(Player player) {
        player.setX((float) partida_player.getNumber("posx", player.getX()));
        player.setY((float) partida_player.getNumber("posy", player.getY()));
        player.setVida((int) partida_player.getNumber("vida", player.getVida()));
        player.setVel((float) partida_player.getNumber("vel", player.getVel()));
        player.dash.setCdmax((int) partida_player.getNumber("dashcd", player.dash.getCdmax()));
        player.dash.setRango((int) partida_player.getNumber("dashrango", player.dash.getRango()));

    }

    public void resetPartida() {
        partida_mapa.setString("estado","reset");
        partida_player.clear();
        guardarEstado(9);
        partida_mapa.clear();
        try {
            partida_mapa.save();
            partida_player.save();
        }
        catch(IOException e){
            System.out.println("Error al resetear la partida");
        };
    }

    public void guardarMapa(Mapa mapa) throws IOException {
        HiloGuardado hilo = new HiloGuardado(mapa,partida_mapa);
        Thread t = new Thread(hilo);
                t.start();
    }

    public void cargarMapa(Mapa mapa) {
        int i = 0;
        ArrayList<Punto> listatemp = new ArrayList<Punto>();
        Punto puntotemp;
        mapa.setOffX((float) partida_mapa.getNumber("offsetX", mapa.getOffX()));
        mapa.setOffY((float) partida_mapa.getNumber("offsetY", mapa.getOffY()));

        mapa.setInfoBitmap(partida_mapa.getString("bitmap"));
        mapa.refrescarInfo();
        do {
            puntotemp = new Punto((float) partida_mapa.getNumber("centroX" + i, 0), (float) partida_mapa.getNumber("centroY" + i, 0));
            System.out.println("PuntoX=" + puntotemp.getX() + "    PuntoY=" + puntotemp.getY());
            if ((puntotemp.getX() == 0) && (puntotemp.getY() == 0)) {
                puntotemp = null;
            } else {
                listatemp.add(puntotemp);
                i++;
            }

        } while (puntotemp != null);
        mapa.setListaCentros(listatemp);

    }
    public boolean esNueva(){
        String estado;
        estado=partida_mapa.getString("estado","nueva");

        return "nueva".equals(estado);
    }
    public void setCargada(){
        partida_player.setString("estado","guardado");
        partida_mapa.setString("estado","guardado");

    }
    public void guardarEstado(int estado){
        partida_estado.setNumber("numestado", estado);
        

    }
    public int getEstado(){
        return (int)partida_estado.getNumber("numestado",9);
        

    }
    
}
