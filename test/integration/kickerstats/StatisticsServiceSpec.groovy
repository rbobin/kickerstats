package kickerstats

import grails.test.spock.IntegrationSpec

@SuppressWarnings(["GroovyAssignabilityCheck", "GrUnresolvedAccess"])
class StatisticsServiceSpec extends IntegrationSpec {

    def statisticsService

    def "test getTopTeamTotalWins"() {
        when:
        def result = statisticsService.getTopTeamTotalWins(10)
        then:
        4 == result.size()
        4 == result[0].wins
        3 == result[1].wins
        2 == result[2].wins
        1 == result[3].wins
        "Title 3" == result[0].team.title
        "Title 2" == result[1].team.title
        "Title 1" == result[2].team.title
        "Title 5" == result[3].team.title
    }

    def "test getTopTeamAverageScore"() {
        when:
        def result = statisticsService.getTopTeamAverageScore(10)
        then:
        4 == result.size()
        5.67 == result[0].avg
        4.2  == result[1].avg
        3.43 == result[2].avg
        3    == result[3].avg
        "Title 3" == result[0].team.title
        "Title 1" == result[1].team.title
        "Title 2" == result[2].team.title
        "Title 5" == result[3].team.title
    }

    def "test getTopTeamWinRate"() {
        when:
        def result = statisticsService.getTopTeamWinRate(10)
        then:
        4 == result.size()
        66 == result[0].rate
        50 == result[1].rate
        42 == result[2].rate
        40 == result[3].rate
        "Title 3" == result[0].team.title
        "Title 5" == result[1].team.title
        "Title 2" == result[2].team.title
        "Title 1" == result[3].team.title
    }

    def "test getTopTeamCrawlRate"() {
        when:
        def result = statisticsService.getTopTeamCrawlRate(10)
        then:
        4 == result.size()
        50 == result[0].crawlRate
        28 == result[1].crawlRate
        0  == result[2].crawlRate
        0  == result[3].crawlRate
        "Title 5" == result[0].team.title
        "Title 2" == result[1].team.title
        "Title 1" == result[2].team.title
        "Title 3" == result[3].team.title
    }

    def "test getRecentCrawledTeams"() {
        when:
        def result = statisticsService.getRecentCrawledTeams(10)
        then:
        3 == result.size()
        "Title 5" == result[0].team.title
        "Title 2" == result[1].team.title
        "Title 2" == result[2].team.title
    }

    def "test getTopPlayerDefenceWins"() {
        when:
        def result = statisticsService.getTopPlayerDefenceWins(10)
        then:
        4 == result.size()
        4 == result[0].wins
        3 == result[1].wins
        2 == result[2].wins
        1 == result[3].wins
        "First Name 6" == result[0].player.firstname
        "First Name 4" == result[1].player.firstname
        "First Name 2" == result[2].player.firstname
        "First Name 3" == result[3].player.firstname
    }

    def "test getTopPlayerOffenceWins"() {
        when:
        def result = statisticsService.getTopPlayerOffenceWins(10)
        then:
        3 == result.size()
        4 == result[0].wins
        3 == result[1].wins
        3 == result[2].wins
        "First Name 5" == result[0].player.firstname
        "First Name 1" == result[1].player.firstname
        "First Name 3" == result[2].player.firstname
    }


    def "test getTopPlayerTotalWins"() {
        when:
        def result = statisticsService.getTopPlayerTotalWins(10)
        then:
        6 == result.size()
        4 == result[0].wins
        4 == result[1].wins
        4 == result[2].wins
        3 == result[3].wins
        3 == result[4].wins
        2 == result[5].wins
        "First Name 3" == result[0].player.firstname
        "First Name 5" == result[1].player.firstname
        "First Name 6" == result[2].player.firstname
        "First Name 1" == result[3].player.firstname
        "First Name 4" == result[4].player.firstname
        "First Name 2" == result[5].player.firstname
    }

    def "test getTopPlayerDefenceWinRate"() {
        when:
        def result = statisticsService.getTopPlayerDefenceWinRate(10)
        then:
        4 == result.size()
        66 == result[0].rate
        50 == result[1].rate
        42 == result[2].rate
        40 == result[3].rate
        "First Name 6" == result[0].player.firstname
        "First Name 3" == result[1].player.firstname
        "First Name 4" == result[2].player.firstname
        "First Name 2" == result[3].player.firstname
    }

    def "test getTopPlayerOffenceWinRate"() {
        when:
        def result = statisticsService.getTopPlayerOffenceWinRate(10)
        then:
        3 == result.size()
        66 == result[0].rate
        42 == result[1].rate
        42 == result[2].rate
        "First Name 5" == result[0].player.firstname
        "First Name 1" == result[1].player.firstname
        "First Name 3" == result[2].player.firstname
    }

    def "test getTopPlayerTotalWinRate"() {
        when:
        def result = statisticsService.getTopPlayerTotalWinRate(10)
        then:
        6 == result.size()
        66  == result[0].rate
        66  == result[1].rate
        44  == result[2].rate
        42  == result[3].rate
        42  == result[4].rate
        40  == result[5].rate
        "First Name 5" == result[0].player.firstname
        "First Name 6" == result[1].player.firstname
        "First Name 3" == result[2].player.firstname
        "First Name 1" == result[3].player.firstname
        "First Name 4" == result[4].player.firstname
        "First Name 2" == result[5].player.firstname
    }
}