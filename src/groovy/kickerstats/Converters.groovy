package kickerstats

import grails.converters.JSON

class Converters {

    static void init() {

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
                    defence: it.defence.id,
                    offence: it.offence.id,
            ]
        }

        JSON.registerObjectMarshaller(Challenge) { Challenge it ->
            return [
                    games: it.games
            ]
        }

        JSON.registerObjectMarshaller(Game) { Game it ->
            return [
                    winnerScore: it.winnerScore.score,
                    winnerTeam : it.winnerScore.team.id,
                    loserScore : it.loserScore.score,
                    loserTeam  : it.loserScore.team.id
            ]
        }
    }
}
