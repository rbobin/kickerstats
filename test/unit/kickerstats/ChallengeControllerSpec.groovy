package kickerstats

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ChallengeController)
class ChallengeControllerSpec extends Specification {

    def "test createChallenge"() {
        given:
        null == Challenge.findByFinished(false)
        when:
        controller.createChallenge()
        then:
        200 == response.status
        response.json.success
        1 == Challenge.findAllByFinished(false).size()

        when:
        controller.createChallenge()
        then:
        409 == response.status
        !response.json.success
        ['Unable to start new challenge: finish current challenge first'] == response.json.errors
        1 == Challenge.findAllByFinished(false).size()
    }
}
