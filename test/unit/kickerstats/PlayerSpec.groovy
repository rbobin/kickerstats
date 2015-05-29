package kickerstats

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Player)
class PlayerSpec extends Specification {

    void "test Player constraints"() {
        given:
        Player existingPlayer = new Player(nickname: "Nickname")
        mockForConstraintsTests(Player, [existingPlayer])
        Player player = new Player(firstname: "First Name", lastname: "Last Name")

        when: "player has not unique nickname"
        player.setNickname("Nickname")
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("unique.nickname")

        when: "player has no nickname"
        player.setNickname(null)
        player.clearErrors()
        then: "player is valid"
        player.validate()
        !player.hasErrors()

        when: "player has blank nickname"
        player.setNickname("")
        player.clearErrors()
        then: "player is valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.nickname.blank.error")

        when: "player has nickname longer than 20 characters"
        player.setNickname("123456789012345678901")
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.nickname.maxSize.error")

        when: "player has nickname less than or exactly 20 characters"
        player.setNickname("12345678901234567890")
        player.clearErrors()
        then: "player is valid"
        player.validate()
        !player.hasErrors()

        when: "player has no first name"
        player.setFirstname(null)
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.firstname.nullable")

        when: "player has blank first name"
        player.setFirstname("")
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.firstname.blank.error")


        when: "player has first name longer than 20 characters"
        player.setFirstname("123456789012345678901")
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.firstname.maxSize.error")

        when: "player has first name less than or exactly 20 characters"
        player.setFirstname("12345678901234567890")
        then: "player is valid"
        player.validate()
        !player.hasErrors()

        when: "player has no last name"
        player.setLastname(null)
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.lastname.nullable")

        when: "player has blank last name"
        player.setLastname("")
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.lastname.blank.error")

        when: "player has last name longer than 20 characters"
        player.setLastname("123456789012345678901")
        then: "player is not valid"
        !player.validate()
        player.hasErrors()
        1 == player.errors.errorCount
        player.errors.allErrors.first().codes.contains("player.lastname.maxSize.error")

        when: "player has last name less than or exactly 20 characters"
        player.setLastname("12345678901234567890")
        then: "player is valid"
        player.validate()
        !player.hasErrors()
    }
}
