package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(Game)
@Mock([Challenge, Game, Score, Team, Player])
class GameSpec extends DomainSpec {

    def "test score constraint"() {
        given:
        Challenge challenge = challenge.save()
        Game game = challenge.games.first()

        when: "a game has no associated scores"
        game.scores = [] as Set
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.empty")

        when: "game has less than two scores"
        game.addToScores(getScore(6))
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.notcomplete")

        when: "game has more than two scores"
        game.addToScores(getScore(4))
        game.addToScores(getScore(2))
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.exceed")

        when: "game has exactly two scores"
        game.scores.clear()
        game.addToScores(getScore(6))
        game.addToScores(getScore(0))
        game.clearErrors()
        then: "game is valid"
        game.validate()
        0 == game.errors.errorCount

        when: "game has same team associated with both scores"
        game.scores*.setTeam(team)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.team.sameteam")

        when: "game has different teams associated with both scores"
        game.scores.first().setTeam(team)
        game.clearErrors()
        then: "game is valid"
        game.validate()
        0 == game.errors.errorCount

        when: "game scores are both maximum"
        game.scores*.setScore(Score.MAX_SCORE)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.score.twomaxscores")

        when: "game has no maximum score"
        game.scores*.setScore(0)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.score.nomaxscore")

        when: "game scores are different and at least one is maximum score"
        game.scores.first().setScore(6)
        game.clearErrors()
        then: "game is valid"
        game.validate()
        0 == game.errors.errorCount

        when: "game has the same player performing in both teams"
        game.scores*.team*.setOffence(player)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.team.player.playerintersection")

        when: "game has no players performing in both teams"
        game.scores*.team*.each { team -> team.setOffence(player)}
        game.clearErrors()
        then: "game is valid"
        game.validate()
        0 == game.errors.errorCount
    }
}
