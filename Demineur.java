/**
 *
 * @author louise
 */
public class Demineur {
    
    public static void main(String[] args) {
        Jeu jeu = new Jeu();//on crée un nouveau jeu
        jeu.options();//on appelle les options
        jeu.initPlateau();//on initialise le plateau
        jeu.joue();//On commence à jouer
    }
}
