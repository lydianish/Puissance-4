/**
 * Created by Lydia on 11-Mar-17.
 */
public class ExceptionColonneInvalide extends Exception {
    private int indiceMax;

    public ExceptionColonneInvalide(int nbcol){
        super();
        this.indiceMax = nbcol-1;
    }

    public int getIndiceMax(){return indiceMax;}
}
