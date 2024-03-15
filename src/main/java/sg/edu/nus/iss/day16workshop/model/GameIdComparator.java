package sg.edu.nus.iss.day16workshop.model;

import java.util.Comparator;

public class GameIdComparator implements Comparator <Game>{

    @Override
    public int compare(Game o1, Game o2) {
       return o1.getGameId().compareTo(o2.getGameId());
    }
    
}
