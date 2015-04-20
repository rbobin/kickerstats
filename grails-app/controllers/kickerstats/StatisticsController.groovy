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
            statisticsService.getTopAverage(params.max)
        }
    }

    def topRate() {
        render(contentType: 'application/json') {
            statisticsService.getTopRate(params.max)
        }
    }
}
