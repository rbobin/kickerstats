package kickerstats

class Game implements Serializable {

    Challenge challenge

    static belongsTo = [challenge: Challenge]
    static hasMany = [scores: Score]

    static constraints = {
        challenge(nullable: false, validator: { challenge, object, errors ->
            if (challenge.finished)
                errors.reject("game.challenge.finished")
        })
        scores(nullable: false, validator: {scores, object, errors ->
            List<Score> scoresList = scores.toList()
            if (!scores)
                errors.reject("game.scores.empty")
            else if (scores.size() != 2)
                errors.reject("game.scores.notcomplete")
            else if (scoresList[0].team == scoresList[1].team)
                errors.reject("game.scores.team.sameteam")
            else if (scoresList[0].score.intValue() == Score.MAX_SCORE && scoresList[1].score.intValue() == Score.MAX_SCORE)
                errors.reject("game.scores.score.twomaxscores")
            else if (scoresList[0].score.intValue() != Score.MAX_SCORE && scoresList[1].score.intValue() != Score.MAX_SCORE)
                errors.reject("game.scores.score.nowinner")
            else if ([scoresList[0].team.defence, scoresList[0].team.offence].intersect([scoresList[1].team.defence, scoresList[1].team.offence]))
                errors.reject("game.scores.team.player.playerintersection")
        })
    }
}