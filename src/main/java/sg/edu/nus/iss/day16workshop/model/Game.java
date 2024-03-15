package sg.edu.nus.iss.day16workshop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class Game implements Serializable /* , Comparable <Game> */{
    
    private String gameId;

    @NotEmpty (message ="Please Home Team")
    private String homeTeam;

    @NotEmpty (message ="Please Opp Team")
    private String oppTeam;

    @NotEmpty (message ="Please venue of the game")
    private String venue;

    @NotNull (message ="Please enter date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past (message ="The date shall be at past")
    private Date gameDate;      

    public Game() {
        this.gameId = UUID.randomUUID().toString();
    }

    public Game(String homeTeam, String oppTeam, String venue, Date gameDate) {
        this.gameId = UUID.randomUUID().toString();
        this.homeTeam = homeTeam;
        this.oppTeam = oppTeam;
        this.venue = venue;
        this.gameDate = gameDate;
    }

    // public Game(String gameId, String homeTeam, String oppTeam, String venue, Date gameDate) {
    //     this.gameId = gameId;
    //     this.homeTeam = homeTeam;
    //     this.oppTeam = oppTeam;
    //     this.venue = venue;
    //     this.gameDate = gameDate;
    // }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getOppTeam() {
        return oppTeam;
    }

    public void setOppTeam(String oppTeam) {
        this.oppTeam = oppTeam;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }
    

    


}
