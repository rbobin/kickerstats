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
        score(nullable: false, validator: { score, object, errors ->
            if (score > MAX_SCORE)
                errors.rejectValue("score", "score.score.highlimit")
            else if (score.intValue() < 0)
                errors.rejectValue("score", "score.score.lowlimit")
        })
    }
}
