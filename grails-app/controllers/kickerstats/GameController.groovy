package kickerstats

class GameController extends BaseController {

    def createGame() {
        Game game = new Game(params)
        if (game.save()) {
            renderSuccess game: game
        } else {
            renderValidationErrors game
        }
    }

    def deleteGame(Game game) {
        if (game) {
            game.delete()
            renderSuccess()
        } else {
            renderNotFound "game"
        }
    }
}