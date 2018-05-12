package EstadosJuego.Narrativa;

import EstadosJuego.CoreGame.drylands.Mapa;
import EstadosJuego.CoreGame.drylands.Personaje;
import EstadosJuego.CoreGame.drylands.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Pensamientos {
    private ArrayList<Frase> listanormal;
    private ArrayList<Frase> listahp1;
    private ArrayList<Frase> listahp2;
    private ArrayList<Frase> listahp3;
    private ArrayList<Frase> listahp4;
    private Player pj;
    private UnicodeFont font;
    private enum estado{esperando,creciendo,estable};
    private estado estado;
    private Frase actual;
    private int iCaracter;
    private final int msporCaracter=100;
    private Sound sound;

    private int msEspera, msFrase,contadorMs;
    public Pensamientos(Player pj, UnicodeFont font, Sound snd){
        listahp1=new ArrayList<>();
        listahp2=new ArrayList<>();
        listahp3=new ArrayList<>();
        listahp4=new ArrayList<>();
        listanormal=new ArrayList<>();
        this.pj=pj;
        this.font=font;
        estado=estado.esperando;
        iCaracter=0;
        sound = snd;
    }
    public void meterFrase(String string) {
        listanormal.add(new Frase(string,pj));
    }
    public void meterFraseHP1(String string) {
        listahp1.add(new Frase(string,pj));
    }
    public void meterFraseHP2(String string) {
        listahp2.add(new Frase(string,pj));
    }
    public void meterFraseHP3(String string) {
        listahp3.add(new Frase(string,pj));
    }
    public void meterFraseHP4(String string) {
        listahp4.add(new Frase(string,pj));
    }
    public void update(int delta){
        contadorMs+=delta;
        switch (estado){
            case estable:
                if(contadorMs>=msFrase){
                    iCaracter=0;
                    contadorMs=0;
                    estado=estado.esperando;
                    setTimeEspera();

                }


                break;
            case creciendo:

                if(contadorMs>=msporCaracter){
                    if(actual.getString().length()>iCaracter){
                        if(actual.getString().charAt(iCaracter)!=' ')
                            sound.play(1.0f,0.7f);
                        contadorMs-=msporCaracter;
                        iCaracter++;

                    }
                    else{
                        estado=estado.estable;
                        contadorMs=0;
                    }


                }
                break;
            case esperando:
                if(contadorMs>=msEspera){
                    setNuevaFrase();
                    contadorMs=0;
                    estado=estado.creciendo;

                }
                break;
        }


    }
    private void setNuevaFrase(){
        int i = ThreadLocalRandom.current().nextInt(0,listanormal.size()+listahp1.size());
        if(i<listanormal.size()){//frase de las normales
            actual=listanormal.get(i);
        }else{      //frase de vida
            if(pj.getVida()/pj.getVidamax()>0.75){
                actual=listahp4.get(ThreadLocalRandom.current().nextInt(0,listahp4.size()));
            }else if(pj.getVida()/pj.getVidamax()>0.5){
                actual=listahp3.get(ThreadLocalRandom.current().nextInt(0,listahp3.size()));
            }else if(pj.getVida()/pj.getVidamax()>0.25){
                actual=listahp2.get(ThreadLocalRandom.current().nextInt(0,listahp2.size()));
            }else{
                actual=listahp1.get(ThreadLocalRandom.current().nextInt(0,listahp1.size()));
            }
        }
        msFrase=ThreadLocalRandom.current().nextInt(2000,4000);
    }
    private void setTimeEspera(){
        msEspera=ThreadLocalRandom.current().nextInt(4000,8000);
    }
    public void render(Graphics g, Mapa mapa){
        if(estado!=estado.esperando){
            g.setFont(font);
            int tam = font.getWidth(actual.getString().substring(0,iCaracter));
            g.drawString(actual.getString().substring(0,iCaracter),actual.getPj().getX()+actual.getPj().TAMX/2-tam/2+mapa.getOffX(),actual.getPj().getY()+mapa.getOffY()+actual.getPj().TAMY+20);

        }

    }
}
