package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.hibernate.ObjectNotFoundException
import spock.lang.Specification

@TestFor(GameService)
@Mock([Score, Game, Challenge])
class GameServiceSpec extends Specification {

    def "test getGame"() {
        given:
        Game game = getGame().save()

        when:
        def id = service.getGame(game.id).id
        then:
        id == game.id

        when:
        service.getGame(null)
        then:
        def e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Game" == e.getEntityName()
    }

    def "test createGame"() {
        when:
        Game game = service.createGame(getGame().getProperties())
        then:
        game.id > 0

        when:
        service.createGame()
        then:
        thrown ServiceException
    }

    def "test deleteGame"() {
        given:
        Game game = getGame().save()

        when:
        service.deleteGame(id: game.id)
        then:
        !Game.get(game.id)

        when:
        service.deleteGame(id: game.id)
        then:
        def e = thrown ObjectNotFoundException
        game.id == e.getIdentifier()
        "Game" == e.getEntityName()

        when:
        service.deleteGame()
        then:
        e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Game" == e.getEntityName()
    }

    def "test findByChallenge" () {
        given:
        Challenge challenge = new Challenge().save()
        challenge.hasErrors()
        getGame(challenge).save()
        getGame(challenge).save()

        when:
        List<Game> games = service.findByChallenge(challenge.id)
        then:
        2 == games.size()

        when:
        service.findByChallenge(null)
        then:
        def e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Challenge" == e.getEntityName()
    }

    private static Game getGame(Challenge challenge = new Challenge()) {
        Game game = new Game(challenge: challenge)
        game.setLoserScore(new Score(team: new Team(offence: new Player(), defence: new Player()), score: 0))
        game.setWinnerScore(new Score(team: new Team(offence: new Player(), defence: new Player()), score: Score.MAX_SCORE))
        game
    }
}
