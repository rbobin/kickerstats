package kickerstats

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.hibernate.ObjectNotFoundException
import spock.lang.Specification

@TestFor(PlayerService)
@Mock(Player)
class PlayerServiceSpec extends Specification {

    def "test getPlayer"() {
        given:
        Player player = new Player(firstname: "First name", lastname: "Last name").save()

        when:
        def id = service.getPlayer(player.id).id
        then:
        id == player.id

        when:
        service.getPlayer(-1L)
        then:
        def e = thrown ObjectNotFoundException
        -1L == e.getIdentifier()
        "Player" == e.getEntityName()
    }

    def "test createPlayer"() {
        when:
        Player player = service.createPlayer([firstname     : "First Name",
                                              lastname      : "Last Name",
                                              redundantParam: "123"])
        then:
        player.id > 0

        when:
        service.createPlayer([firstname: "First Name"])
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "player.lastname.nullable.error"
    }

    def "test editPlayer"() {
        given:
        Player player = new Player(firstname: "First Name", lastname: "Last Name").save()

        when:
        Player updatedPlayer = service.editPlayer(id: player.id, firstname: "First Name1")
        then:
        "First Name1" == updatedPlayer.firstname

        when:
        service.editPlayer(id: player.id, firstname: null)
        then:
        def e = thrown ServiceException
        1 == e.errors.size()
        e.errors.first().codes.contains "player.firstname.nullable.error"

        when:
        service.editPlayer id: -1L
        then:
        e = thrown ObjectNotFoundException
        -1L == e.getIdentifier()
        "Player" == e.getEntityName()

        when:
        service.editPlayer nickname: "Nickname"
        then:
        e = thrown ObjectNotFoundException
        null == e.getIdentifier()
        "Player" == e.getEntityName()
    }
}
