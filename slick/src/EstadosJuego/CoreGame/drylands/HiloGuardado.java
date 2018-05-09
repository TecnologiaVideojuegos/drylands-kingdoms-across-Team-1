package EstadosJuego.CoreGame.drylands;

import org.newdawn.slick.SavedState;

import java.io.IOException;

public class HiloGuardado implements Runnable {
    Mapa mapa;
    SavedState partida_mapa;

    public HiloGuardado(Mapa mapa,SavedState partida_mapa){
        this.mapa=mapa;
        this.partida_mapa=partida_mapa;
    }

    public void run()  {
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

        partida_mapa.setString("bitmap",mapa.getInfoBitmap());
        try{
            partida_mapa.save();
        }catch (IOException e){
            System.out.println("Error al guardar");
        }


    }
}
