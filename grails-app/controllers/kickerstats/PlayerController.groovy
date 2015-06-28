package kickerstats

import grails.converters.JSON

class PlayerController extends BaseController {


    def findPlayer() {
        def responseMap = [success: false]

        if (!params.id) {
            renderMissingParameter('id')
            return
        }

        def id = params.long('id')
        if (!id) {
            renderWrongType('id', 'long')
            return
        }

        def player = Player.get(id)
        if (!player) {
            renderNotFound('player')
            return
        }

        responseMap.success = true
        responseMap.player = player
        render(responseMap as JSON)
    }

    def createPlayer() {
        def responseMap = [success: false]

        def player = new Player(params)
        if (!player.validate()) {
            renderValidationErrors(player)
            return
        }

        player.save(flush: true)

        response.setStatus CREATED
        responseMap.success = true
        responseMap.player = player
        render(responseMap as JSON)
    }

    def updatePlayer() {
        def responseMap = [success: false]

        if (!params.id) {
            renderMissingParameter('id')
            return
        }

        def id = params.long('id')
        if (!id) {
            renderWrongType('id', 'long')
            return
        }

        def player = Player.get(id)
        if (!player) {
            renderNotFound('player')
            return
        }


        player.setProperties(params)
        if (!player.validate()) {
            renderValidationErrors(player)
            return
        }

        player.save(flush: true)

        response.setStatus SUCCESS
        responseMap.success = true
        responseMap.player = player
        render(responseMap as JSON)
    }


}
