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

        when: "game has no winner score"
        game.setLoserScore(new Score(score: 1, team: new Team()))
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.winnerScore.nullable.error")

        when: "game has invalid winner score"
        game.setWinnerScore(new Score(score: 1))
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.winnerScore.nomaxscore")

        when: "game has no loser score"
        game.getWinnerScore().setScore(Score.MAX_SCORE)
        game.setLoserScore(null)
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.loserScore.nullable.error")

        when: "game has invalid loser score"
        game.setLoserScore(new Score(score: Score.MAX_SCORE, team: new Team()))
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.loserScore.maxscore")

        when: "game has same team associated with both scores"
        game.setLoserScore(new Score(score: Score.MAX_SCORE - 1))
        Team team = new Team()
        game.winnerScore.setTeam team
        game.loserScore.setTeam team
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.sameteam")

        when: "game has the same player performing in both teams"
        game.winnerScore.setTeam new Team()
        Player player = new Player()
        game.winnerScore.team.setDefence player
        game.loserScore.team.setOffence player
        game.clearErrors()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains("game.playerintersection")

        when: "game has no players performing in both teams"
        game.winnerScore.team.setDefence new Player()
        game.winnerScore.team.setOffence new Player()
        game.clearErrors()
        then: "game is valid"
        game.validate()
        !game.hasErrors()
    }
}
