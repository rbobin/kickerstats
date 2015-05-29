package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(Score)
@Mock([Challenge, Game, Score, Team, Player])
class ScoreSpec extends DomainSpec {

    def "test game constraint"() {
        given:
        Challenge challenge = challenge.save()
        Score score = challenge.games.first().scores.first()

        when: "score is not associated with any game"
        score.setGame(null)
        then: "score is valid"
        score.validate()
        !score.hasErrors()
    }

    def "test team constraint"() {
        given:
        Challenge challenge = challenge.save()
        Score score = challenge.games.first().scores.first()

        when: "score has no associated team"
        score.setTeam(null)
        then: "score is not valid"
        !score.validate()
        score.hasErrors()
        1 == score.errors.errorCount
        score.errors.allErrors.first().codes.contains("score.team.nullable")

        when: "score has associated team"
        score.setTeam(team)
        score.clearErrors()
        then: "score is valid"
        score.validate()
        !score.hasErrors()
    }

    def "test score constraint"() {
        given:
        Challenge challenge = challenge.save()
        Score score = challenge.games.first().scores.first()

        when: "score has no score"
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
        score.errors.allErrors.first().codes.contains("score.score.min.error")

        when: "score has score > MAX_SCORE"
        score.setScore(Score.MAX_SCORE + 1)
        score.clearErrors()
        then: "score is not valid"
        !score.validate()
        score.hasErrors()
        1 == score.errors.errorCount
        score.errors.allErrors.first().codes.contains("score.score.max.error")

        when: "score has 0 <= score <= MAX_SCORE"
        score.setScore(Score.MAX_SCORE - 1)
        score.clearErrors()
        then: "score is valid"
        score.validate()
        !score.hasErrors()
    }
}
