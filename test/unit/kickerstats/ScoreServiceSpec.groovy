package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.hibernate.ObjectNotFoundException
import spock.lang.Specification

@TestFor(ScoreService)
@Mock([Team, Score])
class ScoreServiceSpec extends Specification {

    def "test getScore"() {
        given:
        Score score = new Score(team: new Team(), score: 0).save()

        when:
        def id = service.getScore(score.id).id
        then:
        id == score.id

        when:
        service.getScore(null)
        then:
        def e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Score" == e.getEntityName()
    }

    def "test createScore"() {
        when:
        Score score = service.createScore(team: new Team(), score: 0)
        then:
        score.id > 0

        when:
        service.createScore(team: new Team())
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "score.score.nullable.error"
    }

    def "test deleteScore"() {
        given:
        Score score = new Score(team: new Team(), score: 0).save()

        when:
        service.deleteScore(id: score.id)
        then:
        !Score.get(score.id)

        when:
        service.deleteScore(id: score.id)
        then:
        def e = thrown ObjectNotFoundException
        score.id == e.getIdentifier()
        "Score" == e.getEntityName()

        when:
        service.deleteScore()
        then:
        e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Score" == e.getEntityName()
    }
}
