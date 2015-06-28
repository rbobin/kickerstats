package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(PlayerController)
@Mock(Player)
class PlayerControllerSpec extends Specification {

    def "test findPlayer"() {
        given:
        def player = new Player(firstname: 'Firstname', lastname: 'Lastname').save()

        when:
        controller.findPlayer()
        then:
        400 == response.status
        !response.json.success
        ['Missing id parameter'] == response.json.errors

        when:
        params.id = 'abc'
        response.reset()
        controller.findPlayer()
        then:
        400 == response.status
        !response.json.success
        ['id parameter must be of type long'] == response.json.errors

        when:
        params.id = '-1'
        response.reset()
        controller.findPlayer()
        then:
        404 == response.status
        !response.json.success
        ['player not found'] == response.json.errors

        when:
        params.id = player.id
        response.reset()
        controller.findPlayer()
        then:
        200 == response.status
        response.json.success
        'Firstname' == response.json.player.firstname
    }

    def "test createPlayer"() {
        given:
        params.firstname = ''
        params.lastname = 'Lastname'

        when:
        controller.createPlayer()
        then:
        400 == response.status
        !response.json.success
        1 == response.json.errors.size()
        !response.json.player
        0 == Player.list().size()

        when:
        params.firstname = 'Firstname'
        response.reset()
        controller.createPlayer()
        then:
        201 == response.status
        response.json.success
        !response.json.errors
        'Firstname' == response.json.player.firstname
        1 == Player.list().size()
    }

    def "test updatePlayer"() {
        given:
        def player = new Player(firstname: 'Firstname', lastname: 'Lastname').save()

        when:
        controller.updatePlayer()
        then:
        400 == response.status
        !response.json.success
        ['Missing id parameter'] == response.json.errors

        when:
        params.id = 'abc'
        response.reset()
        controller.updatePlayer()
        then:
        400 == response.status
        !response.json.success
        ['id parameter must be of type long'] == response.json.errors

        when:
        params.id = '-1'
        response.reset()
        controller.updatePlayer()
        then:
        404 == response.status
        !response.json.success
        ['player not found'] == response.json.errors

        when:
        params.id = player.id
        params.firstname = ''
        response.reset()
        controller.updatePlayer()
        then:
        400 == response.status
        !response.json.success
        1 == response.json.errors.size()
        !response.json.player

        when:
        params.id = player.id
        params.firstname = 'FirstnameUpdated'
        response.reset()
        controller.updatePlayer()
        then:
        200 == response.status
        response.json.success
        !response.json.errors
        'FirstnameUpdated' == response.json.player.firstname
    }
}
