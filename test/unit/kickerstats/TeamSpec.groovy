package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor


@TestFor(Team)
@Mock([Challenge, Game, Score, Team, Player])
class TeamSpec extends DomainSpec {

    def "test title constraint"() {
        given:
        Challenge challenge = challenge.save()
        Team team = challenge.games.first().scores.first().team

        when: "team has no title"
        team.setTitle(null)
        then: "team is valid"
        team.validate()
        !team.hasErrors()

        when: "team has blank title"
        team.setTitle("")
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.title.blank.error")

        when: "team has title longer than 20 characters"
        team.setTitle("123456789012345678901")
        team.clearErrors()
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.title.maxSize.error")

        when: "team has title less or exactly 20 characters"
        team.setTitle("12345678901234567890")
        team.clearErrors()
        then: "team not valid"
        team.validate()
        !team.hasErrors()
    }

    def "test defence constraint"() {
        given:
        Challenge challenge = challenge.save()
        Team team = challenge.games.first().scores.first().team

        when: "team has no defence"
        team.setDefence(null)
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.defence.nullable")

        when: "team has defence"
        team.setDefence(player)
        team.clearErrors()
        then: "team is valid"
        team.validate()
        !team.hasErrors()
    }

    def "test offence constraint"() {
        given:
        Challenge challenge = challenge.save()
        Team team = challenge.games.first().scores.first().team

        when: "team has no offence"
        team.setOffence(null)
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.offence.nullable")

        when: "team has the same offence and defence"
        team.setOffence(team.getDefence())
        team.clearErrors()
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.offence.sameplayer")

        when: "team has offence-defence pair is not unique"
        Player firstPlayer = player
        Player secondPlayer = player
        Team anotherTeam = getTeam()
        anotherTeam.setOffence(firstPlayer)
        anotherTeam.setDefence(secondPlayer)
        mockForConstraintsTests(Team, [anotherTeam])
        team.setOffence(firstPlayer)
        team.setDefence(secondPlayer)
        team.clearErrors()
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("unique.kickerstats.Team.offence")

        when: "team has the same player performing both roles"
        team.setOffence(firstPlayer)
        team.setDefence(firstPlayer)
        team.clearErrors()
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.offence.sameplayer")

        when: "team players are different"
        team.setDefence(player)
        team.clearErrors()
        then: "team is valid"
        team.validate()
        !team.hasErrors()
    }
}
