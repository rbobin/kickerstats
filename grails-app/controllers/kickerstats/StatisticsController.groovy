package kickerstats

class StatisticsController {

    def statisticsService

    def topTeamWin() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamTotalWins(params.max)
        }
    }

    def topTeamAvg() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamAverageScore(params.max)
        }
    }

    def topTeamRate() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamWinRate(params.max)
        }
    }

    def topTeamCrawlRate() {
        render(contentType: 'application/json') {
            statisticsService.getTopTeamCrawlRate(params.max)
        }
    }

    def lastTeamCrawlers() {
        render(contentType: 'application/json') {
            statisticsService.getRecentCrawledTeams(params.max)
        }
    }

    def topDefenseWin() {
        render(contentType: 'application/json') {
            statisticsService.getTopPlayerDefenceWins(params.max)
        }
    }

    def topOffenceWin() {
        render(contentType: 'application/json') {
            statisticsService.getTopPlayerOffenceWins(params.max)
        }
    }

    def topTotalWins() {
        render(contentType: 'application/json') {
            statisticsService.getTopPlayerTotalWins(params.max)
        }
    }

    def topDefenceRate() {
        render(contentType: 'application/json') {
            statisticsService.getTopPlayerDefenceWinRate(params.max)
        }
    }

    def topOffenceRate() {
        render(contentType: 'application/json') {
            statisticsService.getTopPlayerOffenceWinRate(params.max)
        }
    }
 }
