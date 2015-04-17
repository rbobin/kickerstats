import grails.test.spock.IntegrationSpec


class StatisticsServiceSpec extends IntegrationSpec {

    def statisticsService

    def 'testMe'() {
        when:
        def teams = statisticsService.getTopTeams()
        then:
        println teams
    }

}