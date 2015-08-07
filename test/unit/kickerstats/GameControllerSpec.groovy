package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.math.RandomUtils
import spock.lang.Specification

@TestFor(GameController)
@Mock([Team, Player, Game, Challenge])
@SuppressWarnings("GroovyAssignabilityCheck")
class GameControllerSpec extends Specification {

    def "test createGame"() {
        given:
        new Challenge(current: true).save()

        when:
        controller.createGame()
        then:
        400 == response.status
        !response.json.success
        4 == response.json.errors.size()
        !response.json.game
        0 == Game.list().size()

        when:
        params.losersScore = 0
        params.winnersScore = Game.MAX_SCORE
        params.losers = [id: team.id]
        params.winners = [id: team.id]
        response.reset()
        controller.createGame()
        then:
        200 == response.status
        response.json.success
        !response.json.errors
        0 == response.json.game.losersScore
        1 == Game.list().size()
    }

    def "test deleteGame"() {
        given:
        new Challenge(current: true).save()
        def game = new Game(
                losersScore: 0,
                winnersScore: Game.MAX_SCORE,
                losers: team,
                winners: team
        ).save()

        when:
        controller.deleteGame()
        then:
        404 == response.status
        !response.json.success
        ['game not found'] == response.json.errors
        1 == Game.list().size()

        when:
        params.id = game.id
        response.reset()
        controller.deleteGame()
        then:
        200 == response.status
        response.json.success
    }

    def getTeam() {
        new Team(offence: player, defence: player, title: 'Title').save()
    }

    def getPlayer() {
        def player = new Player()
        player.firstname = "${RandomStringUtils.randomAlphabetic(5)}"
        player.lastname = "${RandomStringUtils.randomAlphabetic(5)}"
        player.id = RandomUtils.nextInt()
        return player.save()
    }
}
