package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Game)
@Mock([Challenge, Game, Score, Team, Player])
class GameSpec extends Specification {

    def "test Game constraints"() {
        given:
        mockForConstraintsTests(Game)
        Game game = new Game(challenge: new Challenge())

        when: "game has no associated scores"
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.nullable")

        when: "game has less than two scores"
        game.addToScores([score: 1])
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.notcomplete")

        when: "game has more than two scores"
        game.addToScores([score: 3])
        game.addToScores([score: 2])
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.exceed")

        when: "game has same team associated with both scores"
        game.scores.clear()
        game.addToScores(new Score())
        game.addToScores(new Score())
        game.scores*.setTeam(new Team())
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.team.sameteam")

        when: "game scores are both maximum"
        game.scores.first().setTeam(new Team())
        game.scores*.setScore(Score.MAX_SCORE)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.score.twomaxscores")

        when: "game has no maximum score"
        game.scores*.setScore(0)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.score.nomaxscore")

        when: "game has the same player performing in both teams"
        game.scores.first().setScore(Score.MAX_SCORE)
        game.scores*.team*.each { team -> team.setDefence(new Player()) }
        game.scores*.team*.setOffence(new Player())
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.scores.team.player.playerintersection")

        when: "game has no players performing in both teams"
        game.scores*.team*.each { team -> team.setOffence(new Player()) }
        game.clearErrors()
        then: "game is valid"
        game.validate()
        !game.hasErrors()
    }
}
