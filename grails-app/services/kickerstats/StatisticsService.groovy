package kickerstats

import grails.transaction.Transactional

@Transactional
class StatisticsService {

    final def DEFAULT_MAX_RESULTS = "10"

    def getTopWins(maxResults) {
        def result = []
        def teams = Game.executeQuery("Select g.winner as team, count(g.winner) as wins from Game g group by g.winner order by count(g.winner) desc ", [max: maxResults ?: DEFAULT_MAX_RESULTS])
        teams.each {
            result << [team: it[0], wins: it[1]]
        }
        return result
    }

    def getTopRate(maxResults) {
        def result = []
        def winners = Game.executeQuery("Select g.winner as team, count(g.winner), sum(g.winnerScore) as wins from Game g group by g.winner order by count(g.winner) desc ", [max: maxResults ?: DEFAULT_MAX_RESULTS])
        def loser = Game.executeQuery("Select g.loser as team, count(g.loser), sum(g.loserScore) as wins from Game g group by g.winner order by count(g.winner) desc ", [max: maxResults ?: DEFAULT_MAX_RESULTS])
        return winners
    }
}
