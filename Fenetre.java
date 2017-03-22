import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

/**
 * Classe decrivant la fenetre gaphique
 */
public class Fenetre extends JFrame {

	/**Constructeur de la fenetre
     * @param jeu le plateau de jeu*/
    public Fenetre(Plateau jeu){
        super();
        this.jeu = jeu;

        setTitle("Puissance 4");
        setSize(largeurP,hauteurP + 2*hauteurM);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contenu = getContentPane();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
 		  
        top = new JPanel();
        top.setPreferredSize( new Dimension(largeurP,hauteurM ) );

        top.setLayout(new FlowLayout(FlowLayout.LEADING,40,10));
        choix = myComboBox();
        top.add(choix);
        texte = new JLabel();
        top.add(texte);

        middle = new Panneau(this.jeu,largeurP,hauteurP,texte);
 		  
        bottom = new JPanel();
        bottom.setPreferredSize( new Dimension(largeurP, hauteurM ) );
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        profondeur = mySpinner();
        bottom.add(profondeur);

        slider = mySlider();
        bottom.add(slider);
        newgame = new JButton("Nouvelle Partie");
		bottom.add(newgame);
		quit = new JButton("Quitter");
        bottom.add(quit);


        contenu.add(top);
        contenu.add(middle);
        contenu.add(bottom);
 		  
        pack();
        setResizable(false);
        setVisible(true);

		newgame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				middle.effacer();
			}
		});

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jeu.setAttente(((JSlider)e.getSource()).getValue());
            }
        });

        choix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                middle.setMode(((JComboBox)e.getSource()).getSelectedIndex());
            }
        });

        profondeur.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jeu.setProfondeur((Integer)((JSpinner)e.getSource()).getValue());
            }
        });

    }
    
    
    private Container contenu;
    private JPanel top;
    private Panneau middle;
    private JPanel bottom;
    private Plateau jeu;
    
    private int pixels = 90;
	private int largeurP = 7*pixels;// or Math.min(jeu.getLargeur()*pixels,Toolkit.getDefaultToolkit().getScreenSize().width);;
    private int hauteurP = 6*pixels;
	private int hauteurM = 50;

    private JButton newgame;
    private JButton quit;
    private JLabel texte;
    private JSlider slider;
    private JComboBox choix;
    private JSpinner profondeur;

	private JSlider mySlider(){
		JSlider s = new JSlider(JSlider.HORIZONTAL,Plateau.attenteMin,Plateau.attenteMax
                ,Plateau.attenteMin);
		//Create the label table
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( Plateau.attenteMin), new JLabel("Rapide") );
		labelTable.put( new Integer( Plateau.attenteMax), new JLabel("Lent") );
		s.setLabelTable( labelTable );
		s.setPaintLabels(true);
		return s;
	}

    private JComboBox myComboBox(){
        JComboBox c = new JComboBox();
        c.addItem("Deux Joueurs");
        c.addItem("Un Joueur - Facile");
        c.addItem("Un Joueur - Moyen");
        c.addItem("Un Joueur - Difficile");
        return c;
    }

    private JSpinner mySpinner(){
        JSpinner s = new JSpinner(new SpinnerNumberModel(1,1,Plateau.profondeurMax,1));
        JLabel l = new JLabel("Profondeur :");
        l.setLabelFor(s);bottom.add(l);
        return s;
    }

}
