package sg.edu.nus.iss.day16workshop.restcontroller;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<String> postGame(@RequestBody Game requestPayload) throws ParseException{
        //use if the @requestbody is string
        // JsonReader jReader = Json.createReader(new StringReader(requestPayload));
        // JsonObject jObject = jReader.readObject();
        // Game game = new Game();

        // game.setGameId(jObject.getString("gameId"));
        // game.setHomeTeam(jObject.getString("homeTeam"));
        // game.setOppTeam(jObject.getString("oppTeam"));
        // game.setVenue(jObject.getString("venue"));

        // String gameDate = jObject.getString("gameDate");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date gDate = sdf.parse(gameDate);
        // game.setGameDate(gDate);

        // System.out.println("Post Game: " + game);
        gameService.createGame(requestPayload);
        
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
    @GetMapping("/{game-id}")
    public ResponseEntity<Game> getGgameById(@PathVariable("game-id")String gameId){
        Game game = gameService.getGameById(gameId);
        return new ResponseEntity<Game>(game,HttpStatus.OK);
    }
    //Update (PutMapping)
    @PutMapping("")
    public ResponseEntity<Boolean> updateGame(@RequestBody Game payload){
        gameService.updateGame(payload);
        /* sample payload
            {
                "gameId": "12348",
                "homeTeam": "The Rock",
                "oppTeam": "Undertaker",
                "venue": "WWF",
                "gameDate": "2009-02-21"  //in yyyy-MM-dd
            } 
         */

        return ResponseEntity.ok(true);
    }

    //Delete (DeleteMapping)
    @DeleteMapping("/{game-id}")
    public ResponseEntity<Boolean> deleteGame (@PathVariable("game-id")String gameId){
        
        gameService.deleteGame(gameId);

        return ResponseEntity.ok(true);
    }

}
