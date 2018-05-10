package EstadosJuego.CoreGame.drylands;

public class Combo {
    private int combo;
    private final int msTimeout = 4000;
    private int msContador = 0;

    public Combo() {
        combo = 0;
    }

    public void moreCombo() {
        combo++;
        msContador=0;
    }

    public int getCombo() {
        return combo;
    }

    public void contarMs(int delta) {
        if (combo != 0) {
            msContador += delta;
            if (msContador >= msTimeout)
                resetCombo();
        }

    }

    public void resetCombo() {
        combo = 0;
        msContador=0;
    }
}
