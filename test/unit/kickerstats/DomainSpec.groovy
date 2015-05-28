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
        game.scores = [getScore(3), getScore(6)] as Set
        return game
    }

    def getChallenge() {
        new Challenge(games: [game])
    }
}
