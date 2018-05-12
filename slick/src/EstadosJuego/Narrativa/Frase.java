package EstadosJuego.Narrativa;

import EstadosJuego.CoreGame.drylands.Personaje;

public class Frase {
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Personaje getPj() {
        return pj;
    }

    public void setPj(Personaje pj) {
        this.pj = pj;
    }

    public Frase(String string, Personaje pj) {
        this.string = string;
        this.pj = pj;
    }

    String string;
    Personaje pj;
}
