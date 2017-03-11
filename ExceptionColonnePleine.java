/**
 * Created by Lydia on 11-Mar-17.
 */
public class ExceptionColonnePleine extends Exception {

    private int col;

    public ExceptionColonnePleine(int col){
        super();
        this.col = col;
    }

    public int getCol(){return col;}

}
