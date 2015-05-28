package kickerstats

import grails.test.mixin.TestFor

@TestFor(Challenge)
class ChallengeSpec extends DomainSpec {

    def "test saving not finished empty challenge"() {
        given:
        Challenge challenge = new Challenge(finished: false)
        expect:
        challenge.validate()
        !challenge.hasErrors()
        0 == challenge.errors.errorCount
    }

    def "test saving finished empty challenge"() {
        given:
        Challenge challenge = new Challenge(finished: true)
        expect:
        !challenge.validate()
        challenge.hasErrors()
        1 == challenge.errors.errorCount
        challenge.errors.allErrors.first().codes.contains("challenge.finished.emptychallenge")
    }

    def "test saving not finished non-empty challenge"() {
        given:
        Challenge challenge = challenge
        challenge.setFinished(false)
        expect:
        challenge.validate()
        !challenge.hasErrors()
        0 == challenge.errors.errorCount
    }

    def "test saving finished non-empty challenge"() {
        given:
        Challenge challenge = challenge
        challenge.setFinished(true)
        expect:
        challenge.validate()
        !challenge.hasErrors()
        0 == challenge.errors.errorCount
    }
}