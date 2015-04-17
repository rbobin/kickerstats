package kickerstats

class StatisticsController {

    def statisticsService

    def topWins() {
        render(contentType: 'application/json') {
            statisticsService.getTopWins(params.max)
        }
    }
}
