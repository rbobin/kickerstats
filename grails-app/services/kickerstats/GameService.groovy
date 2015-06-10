package kickerstats

import grails.transaction.Transactional
import org.hibernate.ObjectNotFoundException

@Transactional
class GameService {

    @Transactional(readOnly = true)
    def getGame(Long id) {
        Game game = Game.get id
        if (!game) {
            throw new ObjectNotFoundException(id, "Game")
        }
        game
    }

    def createGame(Map params) {
        Game game = new Game(params)
        if (!game.validate()) {
            throw new ServiceException(errors: game.errors.allErrors)
        }
        game.save flush: true
    }

    def deleteGame(Map params = [:]) {
        def id = params.id as Long
        Game game = Game.get id
        if (!game) {
            throw new ObjectNotFoundException(id, "Game")
        }
        game.delete()
    }

    def findByChallenge(Long id) {
        Challenge challenge = Challenge.get id
        if (!challenge) {
            throw new ObjectNotFoundException(id, "Challenge")
        }
        Game.findAllByChallenge(challenge)
    }
}
