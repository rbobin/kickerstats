package kickerstats

class PlayerController extends BaseController {

    def findPlayer(Player player) {
        if (player) {
            renderSuccess player: player
        } else {
            renderNotFound "player"
        }
    }

    def createPlayer() {
        save new Player(params)
    }

    def updatePlayer(Player player) {
        if (player) {
            player.properties = params
            save player
        } else {
            renderNotFound "player"
        }
    }

    private void save(Player player) {
        if (player.save()) {
            renderSuccess player: player
        } else {
            renderValidationErrors player
        }
    }
}