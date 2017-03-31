import java.util.Scanner;

public class TestOrdi{
	public static void main(String[] args){
		Plateau p = new Plateau();
		Scanner sc = new Scanner(System.in);
		while(true){
			if(!p.estPlein()){
				if(p.getJoueurCourant() == 1){
					/* Partie joueur */
					System.out.println("A vous de jouer ");
					System.out.println("Saisissez le numero de la colonne dans la quelle vous souhaitez jouer (vous avez le choix entre 0 et 6) : ");
		    			int i = sc.nextInt();
					while((i<0) || (i>6)){
						System.out.println("Erreur " + i + " n'est pas une colonne valide! Choisissez une colonne entre 0 et 6");
						System.out.println();
						System.out.println("A vous de jouer ");
						System.out.println("Saisissez le numero de la colonne dans la quelle vous souhaitez jouer (vous avez le choix entre 0 et 6) : ");
						i = sc.nextInt();
					}
					try{
						p.joue(i);
					}catch(ColonnePleineException e){
						System.out.println(e.getMessage());
						System.out.println("recommencez : ");
					}
					p.setPret(true);
					try{
						p.imprime();
					}catch (InterruptedException e) {
						e.printStackTrace();
	    				}
					int c = p.gagne();
					if(c != -1){
						System.out.println("Vous avez gagné ! ");
						p.afficheVecteurGagnant();
						System.out.println("Souhaitez_vous continuer ? (Oui/Non)");
						sc.nextLine();
						String str = sc.nextLine();
						System.out.println("Votre choix est  : " + str);
						if((str.equals("Oui")) || (str.equals("OUI")) || (str.equals("O")) || (str.equals("oui")) || (str.equals("o"))){
							System.out.println("Nouvelle partie : ");
							p.initialise();
						}else{
							if((str.equals("Non")) || (str.equals("NON")) || (str.equals("N")) || (str.equals("non")) || (str.equals("n"))){
								System.out.println("Fin de la partie! Au revoir!");
								return;
							}
						}
					}
				}else{
					if(p.getJoueurCourant() == 2){
						/* Partie Ordinateur */
						System.out.println("c'est à l'ordi de jouer ");
						int v = 0;
						v = (int)( 10 * Math.random());			//renvoie un entier entre 0 et 1
						while((v<0) || (v>6)){
							v = (int)( 10 * Math.random());
						}
						try{
							p.joue(v);
						}catch(ColonnePleineException e){
							System.out.println(e.getMessage());
							System.out.println("l'ordi a choisi une colonne déjà pleine! il recommence ");
						}
						p.setPret(true);
						try{
							p.imprime();
						}catch (InterruptedException e) {
							e.printStackTrace();
		    				}
						int c = p.gagne();
						if(c != -1){
							System.out.println("L'ordinateur a gagné ! ");
							p.afficheVecteurGagnant();
							System.out.println("Souhaitez_vous continuer ? (Oui/Non)");
							sc.nextLine();
							String str = sc.nextLine();
							System.out.println("Votre choix est  : " + str);
							if((str.equals("Oui")) || (str.equals("OUI")) || (str.equals("O")) || (str.equals("oui")) || (str.equals("o"))){
								System.out.println("Nouvelle partie : ");
								p.initialise();
							}else{
								if((str.equals("Non")) || (str.equals("NON")) || (str.equals("N")) || (str.equals("non")) || (str.equals("n"))){
									System.out.println("Fin de la partie! Au revoir!");
									return;
								}
							}
						}	
					}
				}
			}else{
				System.out.println("Fin de la partie! Le plateau est plein!");
				System.out.println("Souhaitez_vous entammer une nouvelle partie ? (Oui/Non)");
				String str = sc.nextLine();
				System.out.println("Votre choix est  : " + str);
				if((str.equals("Oui")) || (str.equals("OUI")) || (str.equals("O")) || (str.equals("oui")) || (str.equals("o"))){
					System.out.println("Nouvelle partie : ");
					p.initialise();;
				}else{
					if((str.equals("Non")) || (str.equals("NON")) || (str.equals("N")) || (str.equals("non")) || (str.equals("n"))){
						System.out.println("Fin de la partie! Au revoir!");
						return;
					}

				}
			}
		}
	}


}
