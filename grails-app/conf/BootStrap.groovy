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

        def scores = [new Score(team: teams[0], score: 6),
                      new Score(team: teams[1], score: 0),
                      new Score(team: teams[2], score: 6),
                      new Score(team: teams[0], score: 3),
                      new Score(team: teams[4], score: 6),
                      new Score(team: teams[2], score: 5),
                      new Score(team: teams[1], score: 6),
                      new Score(team: teams[2], score: 5),
                      new Score(team: teams[2], score: 6),
                      new Score(team: teams[1], score: 0),
                      new Score(team: teams[1], score: 6),
                      new Score(team: teams[0], score: 5),
                      new Score(team: teams[0], score: 6),
                      new Score(team: teams[1], score: 4),
                      new Score(team: teams[1], score: 6),
                      new Score(team: teams[0], score: 1),
                      new Score(team: teams[2], score: 6),
                      new Score(team: teams[1], score: 2),
                      new Score(team: teams[2], score: 6),
                      new Score(team: teams[4], score: 0)]

        def games = [new Game(challenge: challenges[0], winnerScore: scores[0], loserScore: scores[1]),
                     new Game(challenge: challenges[0], winnerScore: scores[2], loserScore: scores[3]),
                     new Game(challenge: challenges[0], winnerScore: scores[4], loserScore: scores[5]),
                     new Game(challenge: challenges[1], winnerScore: scores[6], loserScore: scores[7]),
                     new Game(challenge: challenges[1], winnerScore: scores[8], loserScore: scores[9]),
                     new Game(challenge: challenges[2], winnerScore: scores[10], loserScore: scores[11]),
                     new Game(challenge: challenges[2], winnerScore: scores[12], loserScore: scores[13]),
                     new Game(challenge: challenges[2], winnerScore: scores[14], loserScore: scores[15]),
                     new Game(challenge: challenges[2], winnerScore: scores[16], loserScore: scores[17]),
                     new Game(challenge: challenges[2], winnerScore: scores[18], loserScore: scores[19])]

        entities.addAll(players)
        entities.addAll(teams)
        entities.addAll(challenges)
        entities.addAll(scores)
        entities.addAll(games)

        entities.each { entity ->
            entity.save(failOnError: true, validate: true)
        }
    }

    def destroy = {
    }
}
