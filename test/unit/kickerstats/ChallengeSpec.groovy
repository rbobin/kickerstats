package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Challenge)
@Mock([Challenge, Game, Score, Team, Player])
class ChallengeSpec extends Specification {

    def "test Challenge constraints"() {
        given:
        mockForConstraintsTests(Challenge)
        Challenge challenge = new Challenge()

        when: "challenge is finished and has no games associated"
        challenge.setFinished(true)
        then: "challenge is not valid"
        !challenge.validate()
        challenge.hasErrors()
        1 == challenge.errors.errorCount
        challenge.errors.allErrors.first().codes.contains("challenge.finished.emptychallenge")

        when: "challenge is finished and has at least one game associated"
        challenge.addToGames(new Game())
        challenge.clearErrors()
        then: "challenge is valid"
        challenge.validate()
        !challenge.hasErrors()

        when: "challenge is not finished and has no games associated"
        challenge.setFinished(false)
        challenge.games.clear()
        challenge.clearErrors()
        then: "challenge is valid"
        challenge.validate()
        !challenge.hasErrors()
    }
}