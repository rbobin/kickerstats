package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.hibernate.ObjectNotFoundException
import spock.lang.Specification

@TestFor(ChallengeService)
@Mock([Challenge, Game])
class ChallengeServiceSpec extends Specification {

    def "test getChallenge"() {
        given:
        Challenge challenge = new Challenge().save()

        when:
        def id = service.getChallenge(challenge.id).id
        then:
        id == challenge.id

        when:
        service.getChallenge(-1L)
        then:
        def e = thrown ObjectNotFoundException
        -1L == e.getIdentifier()
        "Challenge" == e.getEntityName()
    }

    def "test createChallenge"() {
        when:
        Challenge challenge = service.createChallenge()
        then:
        challenge.id > 0

        when:
        service.createChallenge()
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "challenge.finished.notunique"
    }

    def "test finishChallenge"() {
        given:
        Challenge challenge = new Challenge().save()

        when:
        service.finishChallenge challenge.id
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "challenge.finished.emptychallenge"

        when:
        mockForConstraintsTests(Challenge)
        challenge.addToGames new Game()
        challenge.save()
        service.finishChallenge challenge.id
        then:
        challenge.finished

        when:
        service.finishChallenge(-1L)
        then:
        e = thrown ObjectNotFoundException
        -1L == e.getIdentifier()
        "Challenge" == e.getEntityName()
    }
}
