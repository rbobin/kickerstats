package kickerstats

class Score implements Serializable {

    Game game
    Team team
    Integer score

    static belongsTo = [game: Game]

    static MAX_SCORE = 6

    static constraints = {
        game nullable: true
        team nullable: false
        score(nullable: false, min: 0, max: MAX_SCORE)
    }
}
