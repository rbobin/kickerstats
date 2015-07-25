package kickerstats

class PlayerController extends BaseController {

    def findPlayer(Player player) {
        if (!player) {
            renderNotFound("player")
        } else {
            renderSuccess(player: player)
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
        if (!player) {
            renderNotFound("player")
        } else {
            player.properties = params
            if (player.save()) {
                renderSuccess(player: player)
            } else {
                renderValidationErrors(player)
            }
        }
    }
}