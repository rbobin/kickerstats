package kickerstats

class StatisticsController {

    def statisticsService

    def topwins() {
        render(contentType: 'application/json') {
            statisticsService.getTopWins(params.max)
        }
    }

    def topavg() {
        render(contentType: 'application/json') {
            statisticsService.getTopAverage(params.max)
        }
    }

    def toprate() {
        render(contentType: 'application/json') {
            statisticsService.getTopRate(params.max)
        }
    }

    def topcrawlrate() {
        render(contentType: 'application/json') {
            statisticsService.getTopCrawlRate(params.max)
        }
    }

    def lastcrawlers() {
        render(contentType: 'application/json') {
            statisticsService.getLastCrawlers(params.max)
        }
    }
}
