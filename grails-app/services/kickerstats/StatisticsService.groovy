package kickerstats

import grails.transaction.Transactional

import java.math.MathContext

@Transactional
class StatisticsService {

    final def DEFAULT_MAX_RESULTS = "10"

    def getTopWins(maxResults) {
        def result = []
        def teams = Game.executeQuery("Select g.winner, count(g.winner) from Game g group by g.winner order by count(g.winner) desc ", [max: maxResults ?: DEFAULT_MAX_RESULTS])
        teams.each {
            result << [team: it[0], wins: it[1]]
        }
        return result
    }

    def getTopAvg(maxResults) {
        def result = []
        def winners = Game.executeQuery("Select g.winner, count(g.winner), sum(g.winnerScore) from Game g group by g.winner order by count(g.winnerScore) desc ")
        def losers = Game.executeQuery("Select g.loser, count(g.loser), sum(g.loserScore) from Game g group by g.loser order by count(g.loserScore) desc ")

        def winnersMap = [:]
        winners.each { team ->
            winnersMap[team.first().id] = team
        }

        def losersMap = [:]
        losers.each { team ->
            losersMap[team.first().id] = team
        }

        winnersMap.each { key, value ->
            if (!losersMap.containsKey(key))
                return
            def avg = (value[2] + losersMap[key][2]) / (value[1] + losersMap[key][1])
            avg = avg.round(new MathContext(3))

            result << [team: value[0], rate: avg]

        }
        result.sort {a, b ->
            - (a.rate <=> b.rate)
        }
        return result
    }

    def getWinRate(maxResults) {
        def result = []
        def teams = Game.executeQuery("Select g.winner, count(g.winner) from Game g group by g.winner order by count(g.winner) desc ", [max: maxResults ?: DEFAULT_MAX_RESULTS])

    }
}
