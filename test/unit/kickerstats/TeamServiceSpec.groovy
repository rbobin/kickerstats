package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.hibernate.ObjectNotFoundException
import spock.lang.Specification

@TestFor(TeamService)
@Mock([Team, Player])
class TeamServiceSpec extends Specification {

    def "test getTeam"() {
        given:
        Team team = new Team(offence: new Player(), defence: new Player()).save()

        when:
        def id = service.getTeam(team.id).id
        then:
        id == team.id

        when:
        service.getTeam(null)
        then:
        def e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Team" == e.getEntityName()
    }

    def "test createTeam"() {
        when:
        Team team = service.createTeam(offence: new Player(), defence: new Player())
        then:
        team.id > 0

        when:
        service.createTeam(offence: new Player())
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "team.defence.nullable.error"
    }

    def "test editTeam"() {
        given:
        Team team = new Team(offence: new Player(), defence: new Player()).save()

        when:
        Team updatedTeam = service.editTeam(id: team.id, title: "Title")
        then:
        "Title" == updatedTeam.title

        when:
        service.editTeam(id: team.id, offence: null)
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "team.offence.nullable.error"

        when:
        service.editTeam id: -1L
        then:
        e = thrown ObjectNotFoundException
        -1L == e.getIdentifier()
        "Team" == e.getEntityName()

        when:
        service.editTeam notExistingParam: null
        then:
        e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Team" == e.getEntityName()
    }
}
