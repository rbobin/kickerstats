package kickerstats

import grails.transaction.Transactional
import org.hibernate.ObjectNotFoundException

@Transactional
class PlayerService {

    @Transactional(readOnly = true)
    def getPlayer(id) {
        id = id as Long
        if (!id) {
            throw new MissingPropertyException("id")
        }
        Player player = Player.get id
        if (!player) {
            throw new ObjectNotFoundException(id, "Player")
        }
        player
    }

    def createPlayer(Map params) {
        Player player = new Player(params)
        if (!player.validate()) {
            throw new ServiceException(errors: player.errors.allErrors)
        }
        player.save flush: true
    }

    def editPlayer(Map params = [:]) {
        def id = Long.getLong(params.id?.toString())
        if (!id) {
            throw new MissingPropertyException("id")
        }
        Player player = Player.get id
        if (!player) {
            throw new ObjectNotFoundException(id, "Player")
        }
        player.properties = params
        if (!player.validate()) {
            throw new ServiceException(errors: player.errors.allErrors)
        }
        player.save flush: true
    }
}
