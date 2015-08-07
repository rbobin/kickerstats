package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import kickerstats.utils.InstanceGenerator
import spock.lang.Specification

@TestFor(Game)
@Mock([Challenge, Game, Team, Player])
class GameSpec extends Specification {

    def "test Game constraints"() {
        given:
        mockForConstraintsTests Game
        Game game = InstanceGenerator.generateGame()

        when: "game has no winners"
        game.setWinners null
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.winners.nullable.error"

        when: "game has no winner score"
        game.clearErrors()
        game.setWinners InstanceGenerator.generateTeam()
        game.setWinnersScore null
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.winnersScore.nullable.error"

        when: "game has invalid winner score"
        game.clearErrors()
        game.setWinnersScore Score.MAX_SCORE - 1
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.winnersScore.notmax"

        when: "game has no loser score"
        game.clearErrors()
        game.setWinnersScore Score.MAX_SCORE
        game.setLosersScore null
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.losersScore.nullable.error"

        when: "game has invalid loser score"
        game.clearErrors()
        game.setLosersScore Score.MAX_SCORE
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.losersScore.range.error"

        when: "game has same team associated with both scores"
        game.clearErrors()
        game.setLosersScore Score.MAX_SCORE - 1
        game.setWinners game.getLosers()
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.sameteam"

        when: "game has the same player performing in both teams"
        game.clearErrors()
        game.setLosers InstanceGenerator.generateTeam(game.winners.defence)
        then: "game is not valid"
        !game.validate()
        game.hasErrors()
        1 == game.errors.errorCount
        game.errors.allErrors.first().codes.contains "game.playerintersection"

        when: "game has no players performing in both teams"
        game.clearErrors()
        game.setLosers InstanceGenerator.generateTeam()
        then: "game is valid"
        game.validate()
        !game.hasErrors()
    }
}
