import grails.test.spock.IntegrationSpec

class StatisticsServiceSpec extends IntegrationSpec {

    def statisticsService

    def 'test getTopWins'() {
        when:
        def teams = statisticsService.getTopWins()
        then:
        teams[0].team.title == "title1"
        teams[1].team.title == "title2"
        teams[0].wins == 2
        teams[1].wins == 1

    }

}