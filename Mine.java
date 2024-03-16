/**
 *
 * @author louise
 */
public class Mine extends Case{

    boolean mineJouee;
    
    public Mine(){  
    };

    public boolean isMineJouee(){
        return mineJouee;
    }
    public void setMineJouee(boolean b){
        mineJouee = b;
    }
@Override    
    public boolean isVide(){
        return false;
    }
  
@Override
    public String afficherCaseVisible(){
        //c'est une mine
        String s="";
        if(isMineJouee())
            s="[ * ]"; //la mine a été jouée
        else
            s="[ m ]";//on affiche m
        return s;
    }
}
