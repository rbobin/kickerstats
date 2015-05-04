import grails.test.spock.IntegrationSpec
import spock.lang.Ignore

class StatisticsServiceSpec extends IntegrationSpec {

    def statisticsService

    @Ignore
    def 'test getTopWins'() {
        when:
        def teams = statisticsService.getTeamTopWins(10)
        then:
        teams[0].team.title == "title1"
        teams[1].team.title == "title2"
        teams[0].wins == 2
        teams[1].wins == 1

    }

    def 'test getTopAvg'() {
        when:
        def teams = statisticsService.getTopTeamAverageScore(10)
        then:
        teams
    }

    def 'test getTopRate'() {
        when:
        def teams = statisticsService.getTopTeamWinRate(10)
        then:
        teams
    }
}