import kickerstats.Challenge
import kickerstats.Game
import kickerstats.Player
import kickerstats.Score
import kickerstats.Team

class BootStrap {

    def converterBean

    def init = { servletContext ->

        converterBean.init()
        def entities = []

        def players = [new Player(firstname: "First Name 1", lastname: "Last Name 1", nickname: "Nickname 1"),
                       new Player(firstname: "First Name 2", lastname: "Last Name 2", nickname: "Nickname 2"),
                       new Player(firstname: "First Name 3", lastname: "Last Name 3", nickname: "Nickname 3"),
                       new Player(firstname: "First Name 4", lastname: "Last Name 4", nickname: "Nickname 4"),
                       new Player(firstname: "First Name 5", lastname: "Last Name 5", nickname: "Nickname 5"),
                       new Player(firstname: "First Name 6", lastname: "Last Name 6", nickname: "Nickname 6"),
                       new Player(firstname: "First Name 7", lastname: "Last Name 7", nickname: "Nickname 7"),
                       new Player(firstname: "First Name 8", lastname: "Last Name 8", nickname: "Nickname 8")]


        def teams = [new Team(offence: players[0], defence: players[1], title: "Title 1"),
                     new Team(offence: players[2], defence: players[3], title: "Title 2"),
                     new Team(offence: players[4], defence: players[5], title: "Title 3"),
                     new Team(offence: players[6], defence: players[7], title: "Title 4"),
                     new Team(offence: players[0], defence: players[2], title: "Title 5")]

        def challenges = [new Challenge(),
                          new Challenge(),
                          new Challenge()]


        def games = [new Game(challenge: challenges[0]),
                     new Game(challenge: challenges[0]),
                     new Game(challenge: challenges[0]),
                     new Game(challenge: challenges[1]),
                     new Game(challenge: challenges[1]),
                     new Game(challenge: challenges[2]),
                     new Game(challenge: challenges[2]),
                     new Game(challenge: challenges[2]),
                     new Game(challenge: challenges[2]),
                     new Game(challenge: challenges[2])]

        def scores = [new Score(game: games[0], team: teams[0], score: 6),
                      new Score(game: games[0], team: teams[1], score: 0),
                      new Score(game: games[1], team: teams[0], score: 3),
                      new Score(game: games[1], team: teams[2], score: 6),
                      new Score(game: games[2], team: teams[4], score: 6),
                      new Score(game: games[2], team: teams[2], score: 5),
                      new Score(game: games[3], team: teams[1], score: 6),
                      new Score(game: games[3], team: teams[2], score: 5),
                      new Score(game: games[4], team: teams[2], score: 6),
                      new Score(game: games[4], team: teams[1], score: 0),
                      new Score(game: games[5], team: teams[0], score: 5),
                      new Score(game: games[5], team: teams[1], score: 6),
                      new Score(game: games[6], team: teams[0], score: 6),
                      new Score(game: games[6], team: teams[1], score: 4),
                      new Score(game: games[7], team: teams[0], score: 1),
                      new Score(game: games[7], team: teams[1], score: 6),
                      new Score(game: games[8], team: teams[1], score: 2),
                      new Score(game: games[8], team: teams[2], score: 6),
                      new Score(game: games[9], team: teams[2], score: 6),
                      new Score(game: games[9], team: teams[4], score: 0)]

        entities.addAll(players)
        entities.addAll(teams)
        entities.addAll(challenges)
        entities.addAll(games)
        entities.addAll(scores)

        entities.each { entity ->
            entity.save(failOnError: true, validate: true)
        }
    }

    def destroy = {
    }
}
