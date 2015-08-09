package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import kickerstats.utils.InstanceGenerator
import spock.lang.Specification

@TestFor(Challenge)
@Mock([Challenge, Game, Team, Player])
class ChallengeSpec extends Specification {

    def "test Challenge constraints"() {
        given: "empty not current challenge"
        mockForConstraintsTests Challenge
        Challenge challenge = new Challenge(current: null)

        expect: "challenge is not valid"
        !challenge.validate()
        challenge.hasErrors()
        1 == challenge.errors.errorCount
        challenge.errors.allErrors.first().codes.contains "challenge.finished.emptychallenge"

        when: "challenge is not current and has at least one game associated"
        challenge.addToGames InstanceGenerator.generateGame()
        challenge.clearErrors()
        then: "challenge is valid"
        challenge.validate()
        !challenge.hasErrors()

        when: "challenge is current and has no games associated"
        challenge.current = true
        challenge.games.clear()
        challenge.clearErrors()
        then: "challenge is valid"
        challenge.validate()
        !challenge.hasErrors()
    }
}