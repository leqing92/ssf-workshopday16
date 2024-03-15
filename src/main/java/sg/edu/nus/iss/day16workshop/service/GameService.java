package sg.edu.nus.iss.day16workshop.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.repo.GameRepo;

@Service
public class GameService {
    
    @Autowired
    GameRepo gameRepo;

    public void createGame (Game game){
        gameRepo.saveGame(game);
    }

    public Map <String, Game> getGameList(){
        return gameRepo.getAllGame();
    }

    public Game getGameById(String gameId){
        return gameRepo.getGameById(gameId);
    }

    public void updateGame(Game game){
        gameRepo.updateGame(game);
    }

    public void deleteGame(String gameId){
        gameRepo.deleteGame(gameId);
    }
    
    public void sortGames(String order, Comparator<Game> comparator, Model model) {
        Map<String, Game> games = getGameList();
        List<Game> sortedGameList = games.values()
                .stream()
                .sorted(order.equals("desc") ? comparator.reversed() : comparator)
                .collect(Collectors.toList());

        model.addAttribute("games", sortedGameList);
        model.addAttribute("order", order.equals("asc") ? "desc" : "asc");
    }
}
