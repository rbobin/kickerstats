package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.math.RandomUtils
import spock.lang.Specification

@TestFor(TeamController)
@Mock([Team, Player])
@SuppressWarnings("GroovyAssignabilityCheck")
class TeamControllerSpec extends Specification {

    def "test findTeam"() {
        given:
        def existingTeam = team

        when:
        controller.findTeam()
        then:
        404 == response.status
        !response.json.success
        ['team not found'] == response.json.errors

        when:
        params.id = 'abc'
        response.reset()
        controller.findTeam()
        then:
        404 == response.status
        !response.json.success
        ['team not found'] == response.json.errors

        when:
        params.id = '-1'
        response.reset()
        controller.findTeam()
        then:
        404 == response.status
        !response.json.success
        ['team not found'] == response.json.errors

        when:
        params.id = existingTeam.id
        response.reset()
        controller.findTeam()
        then:
        200 == response.status
        response.json.success
        'Title' == response.json.team.title
    }

    def "test createTeam"() {
        when:
        controller.createTeam()
        then:
        400 == response.status
        !response.json.success
        2 == response.json.errors.size()
        !response.json.team
        0 == Player.list().size()

        when:
        params.offence = [id: player.id]
        params.defence = [id: player.id]
        params.title = 'Title'
        response.reset()
        controller.createTeam()
        then:
        200 == response.status
        response.json.success
        !response.json.errors
        'Title' == response.json.team.title
        1 == Team.list().size()
    }

    def "test updateTeam"() {
        given:
        def existingTeam = team

        when:
        controller.updateTeam()
        then:
        404 == response.status
        !response.json.success
        ['team not found'] == response.json.errors

        when:
        params.id = 'abc'
        response.reset()
        controller.updateTeam()
        then:
        404 == response.status
        !response.json.success
        ['team not found'] == response.json.errors

        when:
        params.id = '-1'
        response.reset()
        controller.updateTeam()
        then:
        404 == response.status
        !response.json.success
        ['team not found'] == response.json.errors

        when:
        params.id = existingTeam.id
        params.offence = null
        response.reset()
        controller.updateTeam()
        then:
        400 == response.status
        !response.json.success
        1 == response.json.errors.size()
        !response.json.team

        when:
        params.offence = player
        response.reset()
        controller.updateTeam()
        then:
        200 == response.status
        response.json.success
        !response.json.errors
        params.offence.id == response.json.team.offence.id
    }

    def getTeam() {
        new Team(offence: player, defence: player, title: 'Title').save()
    }

    def getPlayer() {
        def player = new Player()
        player.firstname = "${RandomStringUtils.randomAlphabetic(5)}"
        player.lastname= "${RandomStringUtils.randomAlphabetic(5)}"
        player.id = RandomUtils.nextInt()
        return player.save()
    }
}
