package sg.edu.nus.iss.day16workshop.restcontroller;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.service.GameService;

@RestController
@RequestMapping(path = "/api/games")
public class GameRestController {
    
    @Autowired
    GameService gameService;

    //Create (PostMapping)
    @PostMapping("")
    public ResponseEntity<String> postGame(@RequestBody String requestPayload) throws ParseException{
        JsonReader jReader = Json.createReader(new StringReader(requestPayload));
        JsonObject jObject = jReader.readObject();
        Game game = new Game();

        game.setGameId(jObject.getString("gameId"));
        game.setHomeTeam(jObject.getString("homeTeam"));
        game.setOppTeam(jObject.getString("oppTeam"));
        game.setVenue(jObject.getString("venue"));

        String gameDate = jObject.getString("gameDate");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date gDate = sdf.parse(gameDate);
        game.setGameDate(gDate);

        System.out.println("Post Game: " + game);
        gameService.createGame(game);
        
        //to modify it to fit day 16 workshop task 1
        return ResponseEntity.ok("Game created successfully");
    }

    //Read - Get all (GetMapping)
    @GetMapping("")
    public ResponseEntity<List<Game>> getAllGame(){
        Map<String, Game> games = gameService.getGameList();
        List <Game> gamelist = new ArrayList<>();
        for (Map.Entry<String, Game> game : games.entrySet()){
            gamelist.add(game.getValue());
        }

        return ResponseEntity.ok(gamelist);
    }

    //Read - Get by id (GetMapping)

    //Update (PutMapping)

    //Delete (DeleteMapping)


}
