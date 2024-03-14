package sg.edu.nus.iss.day16workshop.repo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day16workshop.model.Game;
import sg.edu.nus.iss.day16workshop.util.Util;

@Repository
public class GameRepo {

    @Autowired 
    @Qualifier(Util.REDIS_TWO)
    //HashOperations <String, String, Game> hashOps;
    RedisTemplate <String, Game> template;

    public void test(){
       //HashOperations hashOps = template.opsForHash();
       
    }

    //CREATE (in Redis Map)
    public void saveGame(Game game){
        HashOperations <String, String, Game> hashOps = template.opsForHash();
        hashOps.putIfAbsent(Util.KEY_GAME, game.getGameId(), game);
    }

    //READ (from Redis Map)
    public Map <String, Game> getAllGame(){
        HashOperations <String, String, Game> hashOps = template.opsForHash();
        return hashOps.entries(Util.KEY_GAME);
    }

    //Read one specific record (from Redis Map)
    public Game getGameById(String gameId){
        HashOperations <String, String, Game> hashOps = template.opsForHash();
        return hashOps.get(Util.KEY_GAME, gameId);
    }

    //Update a specific record
    public void updateGame(Game game){
        HashOperations <String, String, Game> hashOps = template.opsForHash();
        hashOps.put(Util.KEY_GAME, game.getGameId(), game);
    }

    //Delete a specific record
    public void deleteGame(String gameId){
        HashOperations <String, String, Game> hashOps = template.opsForHash();
        hashOps.delete(Util.KEY_GAME, gameId);
    }
}
