/**
 *
 * @author louise
 */
public class Vide extends Case {
    
    public Vide(){
    };
    
@Override   
    public boolean isVide(){
        return true;
    }
@Override   
    public String afficherCaseVisible(){
        String s="";
        int nbm = getNbVMines();
                if(nbm==0)//si le nombre de mines voisines est égal à 0
                    s="[   ]";//on affiche une case vide
                else
                    s= "[ "+ nbm+ " ]";  //sinon on affiche le nombre de mines autour 
        return s;
    }
}
