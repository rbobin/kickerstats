package kickerstats

import grails.converters.JSON

class Converters {

    void init() {

        JSON.registerObjectMarshaller(Player) { Player it ->
            return [
                    firstname: it.firstname,
                    lastname : it.lastname,
                    nickname : it.nickname ?: "",
            ]
        }

        JSON.registerObjectMarshaller(Team) { Team it ->
            return [
                    title  : it.title ?: "",
                    defence: it.defence,
                    offence: it.offence,
            ]
        }
    }
}
