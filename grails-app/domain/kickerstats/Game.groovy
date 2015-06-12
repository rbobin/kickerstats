package kickerstats

class Game implements Serializable {

    Challenge challenge
    Score winnerScore
    Score loserScore

    static belongsTo = [challenge: Challenge]

    static constraints = {
        winnerScore(nullable: false, validator: { score, object, errors ->
            if (score.score != Score.MAX_SCORE)
                errors.reject("game.winnerScore.nomaxscore")
        })
        loserScore(nullable: false, validator: { score, object, errors ->
            if (score.score == Score.MAX_SCORE)
                errors.reject("game.scores.score.twomaxscores")
            else if (score.team == object.winnerScore?.team)
                errors.reject("game.scores.team.sameteam")
            else if (score.team.getPlayers().intersect(object.winnerScore?.team?.getPlayers() ?: []))
                errors.reject("game.scores.team.player.playerintersection")
        })
    }
}