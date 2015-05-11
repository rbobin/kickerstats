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
            statisticsService.getTopPlayerDefenceTotalWins(params.max)
        }
    }

    def topOffenceWin() {
        render()
    }
 }
