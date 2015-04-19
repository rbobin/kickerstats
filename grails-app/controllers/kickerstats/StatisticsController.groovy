package kickerstats

class StatisticsController {

    def statisticsService

    def topWins() {
        render(contentType: 'application/json') {
            statisticsService.getTopWins(params.max)
        }
    }

    def topAvg() {
        render(contentType: 'application/json') {
            statisticsService.getTopAverageScore(params.max)
        }
    }
}
