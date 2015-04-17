package kickerstats

import grails.transaction.Transactional

@Transactional
class StatisticsService {

    final def DEFAULT_MAX_RESULTS = 10

    def getTopTeams(int maxResults = DEFAULT_MAX_RESULTS) {
        def teams = Game.executeQuery("Select g.winner, count(g.winner) from Game g group by g.winner order by count(g.winner) desc ", [max: maxResults])
        return teams
    }
}
