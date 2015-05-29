package kickerstats

import spock.lang.Specification

class DomainSpec extends Specification {

    def getPlayer() {
        new Player(firstname: "First name ${System.currentTimeMillis()}",
                lastname: "Last name ${System.currentTimeMillis()}",
                nickname: "Nickname")
    }

    def getTeam() {
        new Team(title: "Title ${System.currentTimeMillis()}",
                offence: player, defence: player)
    }

    def getScore(int score) {
        new Score(score: score, team: team)
    }

    def getGame() {
        Game game = new Game()
        game.addToScores(getScore(3))
        game.addToScores(getScore(6))
        return game
    }

    def getChallenge() {
        Challenge challenge = new Challenge()
        challenge.addToGames(game)
        return challenge
    }
}
