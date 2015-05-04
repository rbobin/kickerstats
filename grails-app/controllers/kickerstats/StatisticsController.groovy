package kickerstats

class StatisticsController {

    def statisticsService

    def topwins() {
        render(contentType: 'application/json') {
            statisticsService.getTeamTopWins(params.max)
        }
    }

    def topavg() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamAverageScore(params.max)
        }
    }

    def toprate() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamWinRate(params.max)
        }
    }

    def topcrawlrate() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamCrawlRate(params.max)
        }
    }

    def lastcrawlers() {
        render(contentType: 'application/json') {
            statisticsService.getRecentCrawledTeams(params.max)
        }
    }
}
