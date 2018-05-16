package EstadosJuego.Narrativa;

import EstadosJuego.CoreGame.drylands.Mapa;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;

import java.io.EOFException;
import java.util.ArrayList;

public class Dialogo {
    private ArrayList<Frase> lista;
    private final int msporCaracter=100;
    private int iCaracter,iFrase,contadorMs;
    private boolean esperando,terminado,rapido;
    UnicodeFont fuente;
    Sound snd;

    public Dialogo(ArrayList<Frase> lista, UnicodeFont font,Sound snd) {
        this.lista = lista;
        iCaracter=0;
        iFrase=0;
        contadorMs=0;
        rapido=terminado=esperando=false;
        fuente=font;
        this.snd=snd;
    }
    public void render(Graphics g, Mapa mapa){
        if(!terminado){
            g.setFont(fuente);
            int tam = fuente.getWidth(lista.get(iFrase).getString().substring(0,iCaracter));
            g.drawString(lista.get(iFrase).getString().substring(0,iCaracter),lista.get(iFrase).getPj().getX()+lista.get(iFrase).getPj().TAMX/2-tam/2+mapa.getOffX(),lista.get(iFrase).getPj().getY()+mapa.getOffY()+lista.get(iFrase).getPj().TAMY+20);

        }

    }
    public void update(int delta){
        int mspcTemp;
        if(rapido)
            mspcTemp=msporCaracter/2;
        else
            mspcTemp=msporCaracter;
        if(!esperando){
            contadorMs+=delta;
            if(contadorMs>=mspcTemp){


                if(lista.get(iFrase).getString().length()>iCaracter){
                    if(lista.get(iFrase).getString().charAt(iCaracter)!=' ')
                        snd.play();
                    iCaracter++;
                    contadorMs-=mspcTemp;
                }
                else{
                    esperando=true;
                    contadorMs=0;
                }

            }
        }


    }

    public void go() throws EOFException {
        if(esperando){
            esperando=false;
            if(lista.size()>(iFrase+1)) {
                iFrase++;
                iCaracter=0;
                rapido=false;
            }
            else {
                terminado = true;
                throw new EOFException();
            }
        }
        else{
            rapido=true;
        }
    }

    public void kill() throws EOFException {
        terminado = true;
        throw new EOFException();
    }

    public int getiFrase() {
        return iFrase;
    }

    public boolean isTerminado() {
        return terminado;
    }
    
}
