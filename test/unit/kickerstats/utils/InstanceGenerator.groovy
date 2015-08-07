package kickerstats.utils

import kickerstats.Challenge
import kickerstats.Game
import kickerstats.Player
import kickerstats.Team
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.math.RandomUtils

class InstanceGenerator {

    static def generateCurrentChallenge() {
        new Challenge(current: true).save(failOnError: true)
    }

    static def generateGame() {
        def challenge = Challenge.findByCurrent(true) ?: generateCurrentChallenge()
        new Game(challenge: challenge,
                losers: generateTeam(),
                losersScore: 0,
                winners: generateTeam(),
                winnersScore: Game.MAX_SCORE)
    }

    static def generateTeam(offence = generatePlayer(), defence = generatePlayer()) {
        new Team(offence: offence,
                defence: defence,
                title: 'Title').save(failOnError: true)
    }

    static def generatePlayer() {
        def player = new Player()
        player.firstname = "${RandomStringUtils.randomAlphabetic 5}"
        player.lastname = "${RandomStringUtils.randomAlphabetic 5}"
        player.id = RandomUtils.nextInt()
        return player.save(failOnError: true)
    }

}
