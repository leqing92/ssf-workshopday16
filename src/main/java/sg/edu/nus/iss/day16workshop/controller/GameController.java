package sg.edu.nus.iss.day16workshop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.model.GameDateComparator;
import sg.edu.nus.iss.day16workshop.model.GameIdComparator;
import sg.edu.nus.iss.day16workshop.service.GameService;

@Controller
@RequestMapping(path = "/games")
public class GameController {
    
    @Autowired
    GameService gameService;

    @GetMapping("/list")
    public String getList(Model model){
        Map<String, Game> games = gameService.getGameList();
        List <Game> gameList = new ArrayList<>();
        for(Map.Entry<String, Game> game : games.entrySet()){
            gameList.add(game.getValue());
        }
        model.addAttribute("games", gameList);
        return "gamelist";
    }

    @PostMapping("/add")
    public String addList(HttpServletResponse response, Model model, @ModelAttribute("game") @Valid Game game, BindingResult bindings) throws ParseException{
        
        if(bindings.hasErrors()){            
            return "add";
        }else{
            // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // Date gDate = sdf.parse(sdf.format(game.getGameDate()));
            //Game newGame = new Game(game.getHomeTeam(),game.getOppTeam(),game.getVenue(),game.getGameDate());
            gameService.createGame(game);
        }
        model.addAttribute("game",game);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "success";
    }

    @GetMapping("/delete/{gameId}")
    public String deleteGame (@PathVariable("gameId") String gameId){
        gameService.deleteGame(gameId);

        return "redirect:/games/list";
    }

//for update game 
    //get the detail of game by game ID
    @GetMapping("/edit/{gameId}")
    public String gameToBeEdit (@PathVariable("gameId") String gameId, Model model){
        Game game = gameService.getGameById(gameId);
        model.addAttribute("game", game);
        return "/edit";
    }
    //save the updated detail
    @PostMapping("/edit")
    public String editGame (HttpServletResponse response, Model model, @ModelAttribute("game") @Valid Game game, BindingResult bindings) throws ParseException{
        
        if(bindings.hasErrors()){            
            return "edit";
        }else{            
            gameService.updateGame(game);
        }
        model.addAttribute("game",game);
        return "success";
    }
//different way yo do sorting
    //sort by map + comparator
    @GetMapping("/sort/id")
    public String sortGamebyId (Model model){   
        Map <String, Game> games = gameService.getGameList();
        Map <String, Game> sortedGames = games.entrySet()
                                                .stream()
                                                .sorted(Map.Entry.comparingByValue(new GameIdComparator()))
                                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List <Game> sortedGameList = new ArrayList<>();
        for(Map.Entry<String, Game> game : sortedGames.entrySet()){
            sortedGameList.add(game.getValue());
        }
        model.addAttribute("games", sortedGameList);
        return "gamelist";
    }

    //sort by list + comparator + asc/desc
    @GetMapping("/sort/date")
    public String sortGamebyDate (@RequestParam(name = "order", defaultValue = "asc") String order, Model model){   
        Map <String, Game> games = gameService.getGameList();
        // Map <String, Game> sortedGames = games.entrySet()
        //                                         .stream()
        //                                         .sorted(Map.Entry.comparingByValue(new GameDateComparator()))
        //                                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        // for(Map.Entry<String, Game> game : sortedGames.entrySet()){
        //     sortedGameList.add(game.getValue());
        // }
        List <Game> sortedGameList = games.values()
                                        .stream()
                                        .sorted((game1, game2) -> 
                                        {
                                            if ("desc".equals(order)) {
                                                return game2.getGameDate().compareTo(game1.getGameDate());
                                            } else {
                                                return game1.getGameDate().compareTo(game2.getGameDate());
                                            }
                                        })
                                        .collect(Collectors.toList());        

        model.addAttribute("games", sortedGameList);
        model.addAttribute("order", order.equals("asc") ? "desc" : "asc");

        return "gamelist";
    }

    @GetMapping("/sort/hometeam")
    public String sortGamebyHome(@RequestParam(name = "order", defaultValue = "asc") String order, Model model) {
        gameService.sortGames(order, Comparator.comparing(Game::getHomeTeam), model);
        return "gamelist";
    }

    @GetMapping("/sort/oppteam")
    public String sortGamebyOpp(@RequestParam(name = "order", defaultValue = "asc") String order, Model model) {
        gameService.sortGames(order, Comparator.comparing(Game::getOppTeam), model);
        return "gamelist";
    }

    @GetMapping("/sort/venue")
    public String sortGamebyVenue(@RequestParam(name = "order", defaultValue = "asc") String order, Model model) {
        gameService.sortGames(order, Comparator.comparing(Game::getVenue), model);
        return "gamelist";
    }
    
}
