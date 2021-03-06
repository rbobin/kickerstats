package kickerstats

import grails.transaction.Transactional
import static org.hibernate.criterion.CriteriaSpecification.*

@Transactional
class StatisticsService {

    final def DEFAULT_MAX_RESULTS = "10"

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopTeamTotalWins(maxResults) {
        Score.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(ALIAS_TO_ENTITY_MAP)
            eq("score", Score.MAX_SCORE)
            projections {
                property("team", "team")
                groupProperty "team"
                rowCount "wins"
                order("wins", "desc")
                order("team", "asc")
            }
        }
    }

    def getTopTeamAverageScore(maxResults) {
        def result = Score.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(ALIAS_TO_ENTITY_MAP)
            projections {
                property("team", "team")
                groupProperty "team"
                avg("score", "avg")
                order("avg", "desc")
                order("team", "asc")
            }
        }
        result.collect()
        result.each {
            it.avg = it.avg.round(2)
        }
        return result
    }

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopTeamWinRate(maxResults) {
        Score.executeQuery("""select new map (s.team as team,
                                  (count(case s.score when :maxScore then 1 else null end) * 100) / count(*) as rate)
                              from Score s
                              group by s.team
                              order by rate desc, team asc""", [maxScore: Score.MAX_SCORE], [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    def getTopTeamCrawlRate(maxResults) {
        Score.executeQuery("""select new map (s.team as team,
                                  (count(case s.score when 0 then 1 else null end) * 100) / count(*) as crawlRate)
                              from Score s
                              group by s.team
                              order by crawlRate desc, team asc""", [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    def getRecentCrawledTeams(maxResults) {
        Challenge.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(ALIAS_TO_ENTITY_MAP)
            createAlias("games", "g")
            createAlias("g.loserScore", "s")
            projections {
                property "s.id"
                property "s.score"
                property("s.team", "team")
            }
            eq("s.score", 0)
            order("s.id", "desc")
            order("team", "asc")
        }
    }

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopPlayerDefenceWins(maxResults) {
        Score.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(ALIAS_TO_ENTITY_MAP)
            eq("score", Score.MAX_SCORE)
            createAlias("team", "t")
            projections {
                property("t.defence", "player")
                groupProperty "t.defence"
                rowCount "wins"
                order("wins", "desc")
                order("player", "asc")
            }
        }
    }

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopPlayerOffenceWins(maxResults) {
        Score.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(ALIAS_TO_ENTITY_MAP)
            eq("score", Score.MAX_SCORE)
            createAlias("team", "t")
            projections {
                property("t.offence", "player")
                groupProperty "t.offence"
                rowCount "wins"
                order("wins", "desc")
                order("player", "asc")
            }
        }
    }

    def getTopPlayerTotalWins(maxResults) {
        Score.executeQuery("""select new map (p as player,
                                  count(p) as wins)
                              from Score s, Player p
                              where (s.team.defence = p or s.team.offence = p)
                                  and s.score = 6
                              group by p
                              order by wins desc, player asc""", [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopPlayerDefenceWinRate(maxResults) {
        Score.executeQuery("""select new map (p as player,
                                  (count(case s.score when :maxScore then 1 else null end) * 100) / count(*) as rate)
                              from Score s, Player p
                              where (s.team.defence = p)
                              group by p
                              order by rate desc, player asc""", [maxScore: Score.MAX_SCORE], [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopPlayerOffenceWinRate(maxResults) {
        Score.executeQuery("""select new map (p as player,
                                  (count(case s.score when :maxScore then 1 else null end) * 100) / count(*) as rate)
                              from Score s, Player p
                              where (s.team.offence = p)
                              group by p
                              order by rate desc, player asc""", [maxScore: Score.MAX_SCORE], [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    // TODO Update to count on winnerScore instead of MAX_SCORE
    def getTopPlayerTotalWinRate(maxResults) {
        Score.executeQuery("""select new map (p as player,
                                  (count(case s.score when :maxScore then 1 else null end) * 100) / count(*) as rate)
                              from Score s, Player p
                              where (s.team.defence = p or s.team.offence = p)
                               group by p
                              order by rate desc, player asc""", [maxScore: Score.MAX_SCORE], [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }
}