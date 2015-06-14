package kickerstats

class Game implements Serializable {

    Score winnerScore
    Score loserScore

    static belongsTo = [challenge: Challenge]

    static constraints = {
        winnerScore nullable: false, validator: { score, object, errors ->
            if (score.score != Score.MAX_SCORE)
                errors.rejectValue(
                        "winnerScore",
                        "game.winnerScore.nomaxscore",
                        "Winner score must be equal to maximum score")
        }
        loserScore nullable: false, validator: { score, object, errors ->
            if (score.score == Score.MAX_SCORE)
                errors.rejectValue(
                        "loserScore",
                        "game.loserScore.maxscore",
                        "Loser score must be between 0 and maximum score")
            if (score.team == object.winnerScore?.team)
                errors.reject("game.sameteam")
            else if (score.team.getPlayers().intersect(object.winnerScore?.team?.getPlayers() ?: []))
                errors.reject("game.playerintersection")
        }
    }
}