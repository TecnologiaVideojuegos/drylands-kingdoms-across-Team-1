package CoreGame.drylands;

import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.util.ArrayList;

public class Guardado {
    private SavedState partida_player;
    private SavedState partida_mapa;

    public Guardado(String nombrepartida) throws SlickException {

        partida_player = new SavedState(nombrepartida + "player");
        partida_mapa = new SavedState(nombrepartida + "mapa");

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
        partida_player.clear();
        partida_mapa.clear();
    }

    public void guardarMapa(Mapa mapa) throws IOException {
        int i = 0;
        partida_mapa.setNumber("offsetX", mapa.getOffX());
        partida_mapa.setNumber("offsetY", mapa.getOffY());
        if (mapa.getListaCentros().size() > 0) {
            for (i = 0; i < mapa.getListaCentros().size(); i++) {
                partida_mapa.setNumber("centroX" + i, mapa.getListaCentros().get(i).getX());
                partida_mapa.setNumber("centroY" + i, mapa.getListaCentros().get(i).getY());
            }
            partida_mapa.setNumber("centroX" + i, 0);
            partida_mapa.setNumber("centroY" + i, 0);
        }
        partida_mapa.setString("estado","guardado");
        partida_mapa.save();
    }

    public void cargarMapa(Mapa mapa) {
        int i = 0;
        ArrayList<Punto> listatemp = new ArrayList<Punto>();
        Punto puntotemp;
        mapa.setOffX((float) partida_mapa.getNumber("offsetX", mapa.getOffX()));
        mapa.setOffY((float) partida_mapa.getNumber("offsetY", mapa.getOffY()));
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
}
