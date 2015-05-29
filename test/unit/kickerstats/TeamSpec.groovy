package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(Team)
@Mock([Team, Player])
class TeamSpec extends Specification {

    def "test Team constraints"() {
        given:
        Player existingOffencePlayer = new Player()
        Player existingDefencePlayer = new Player()
        Team existingTeam = new Team(offence: existingOffencePlayer, defence: existingDefencePlayer)
        mockForConstraintsTests(Team, [existingTeam])
        Team team = new Team(defence: new Player(), offence: new Player())

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

        when: "team has title less than or exactly 20 characters"
        team.setTitle("12345678901234567890")
        team.clearErrors()
        then: "team not valid"
        team.validate()
        !team.hasErrors()

        when: "team has no defence"
        team.setDefence(null)
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("team.defence.nullable")

        when: "team has defence"
        team.setDefence(new Player())
        team.clearErrors()
        then: "team is valid"
        team.validate()
        !team.hasErrors()

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
        team.setOffence(existingOffencePlayer)
        team.setDefence(existingDefencePlayer)
        team.clearErrors()
        then: "team is not valid"
        !team.validate()
        team.hasErrors()
        1 == team.errors.errorCount
        team.errors.allErrors.first().codes.contains("unique.kickerstats.Team.offence")

        when: "team players are different"
        team.setDefence(new Player())
        team.clearErrors()
        then: "team is valid"
        team.validate()
        !team.hasErrors()
    }
}
