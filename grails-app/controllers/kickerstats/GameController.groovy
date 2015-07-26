package kickerstats

class GameController extends BaseController {

    def createGame(GameCommand gameCommand) {
        Game game = gameCommand.game
        if (game.save()) {
            renderSuccess(game: game)
        } else {
            renderValidationErrors(game)
        }
    }

    def deleteGame(Game game) {
        if (game) {
            game.delete()
            renderSuccess()
        } else {
            renderNotFound("game")
        }
    }
}

class GameCommand {

    Team winners
    Team losers
    Integer winnersScore
    Integer losersScore

    def getGame() {
        new Game(
                winners: winners,
                losers: losers,
                winnersScore: winnersScore,
                losersScore: losersScore
        )
    }
}
