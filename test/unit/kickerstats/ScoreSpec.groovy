package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Score)
@Mock([Game, Score, Team])
class ScoreSpec extends Specification {

    def "test Score constraints"() {
        given:
        mockForConstraintsTests(Score)
        Score score = new Score(team: new Team(), score: 0)

        when: "score is not associated with any game"
        score.setGame(null)
        then: "score is valid"

        when: "score has no team"
        score.setTeam(null)
        then: "score is not valid"
        !score.validate()
        score.hasErrors()
        1 == score.errors.errorCount
        score.errors.allErrors.first().codes.contains("score.team.nullable")

        when: "score has no score"
        score.setTeam(new Team())
        score.setScore(null)
        then: "score is not valid"
        !score.validate()
        score.hasErrors()
        1 == score.errors.errorCount
        score.errors.allErrors.first().codes.contains("score.score.nullable")

        when: "score has score < 0"
        score.setScore(-1)
        score.clearErrors()
        then: "score is not valid"
        !score.validate()
        score.hasErrors()
        1 == score.errors.errorCount
        score.errors.allErrors.first().codes.contains("score.score.range.error")

        when: "score has score > MAX_SCORE"
        score.setScore(Score.MAX_SCORE + 1)
        score.clearErrors()
        then: "score is not valid"
        !score.validate()
        score.hasErrors()
        1 == score.errors.errorCount
        score.errors.allErrors.first().codes.contains("score.score.range.error")

        when: "score has 0 <= score <= MAX_SCORE"
        score.setScore(Score.MAX_SCORE - 1)
        score.clearErrors()
        then: "score is valid"
        score.validate()
        !score.hasErrors()
    }
}
