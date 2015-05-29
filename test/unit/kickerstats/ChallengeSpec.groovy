package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(Challenge)
@Mock([Challenge, Game, Score, Team, Player])
class ChallengeSpec extends DomainSpec {

    def "test finished constraint"() {
        given:
        Challenge challenge = challenge.save()

        when: "challenge is finished and has no games associated"
        challenge.setFinished(true)
        challenge.games.clear()
        then: "challenge is not valid"
        !challenge.validate()
        challenge.hasErrors()
        1 == challenge.errors.errorCount
        challenge.errors.allErrors.first().codes.contains("challenge.finished.emptychallenge")

        when: "challenge is finished and has at least one game associated"
        challenge.addToGames(game)
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