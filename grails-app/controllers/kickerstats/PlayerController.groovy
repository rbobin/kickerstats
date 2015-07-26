package kickerstats

class PlayerController extends BaseController {

    def findPlayer(Player player) {
        if (player) {
            renderSuccess(player: player)
        } else {
            renderNotFound("player")
        }
    }

    def createPlayer() {
        Player player = new Player(params)
        if (player.save()) {
            renderSuccess(player: player)
        } else {
            renderValidationErrors(player)
        }
    }

    def updatePlayer(Player player) {
        if (player) {
            player.properties = params
            if (player.save()) {
                renderSuccess(player: player)
            } else {
                renderValidationErrors(player)
            }
        } else {
            renderNotFound("player")
        }
    }
}