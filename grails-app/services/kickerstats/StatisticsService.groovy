package kickerstats

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class StatisticsService {

    final def DEFAULT_MAX_RESULTS = "10"

    def getTopWins(maxResults) {
        Score.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            gt("wins", 3)
            eq("score", Score.MAX_SCORE)
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("team", "team")
                groupProperty "team"
                rowCount "wins"
                order("wins", "desc")
            }
        }
    }

    def getTopAverage(maxResults) {
        Score.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("team", "team")
                groupProperty "team"
                avg("score", "avg")
                order("avg", "desc")
            }
        }
    }

    def getTopRate(maxResults) {
         Score.executeQuery("""select new map (s.team as team,
                                               (count(case s.score when :maxScore then 1 else null end) * 100) / count(*) as rate)
                               from Score s
                               group by s.team
                               order by rate desc""", [maxScore: Score.MAX_SCORE], [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    def getTopCrawlRate(maxResults) {
        Score.executeQuery("""select new map (s.team as team,
                                              (count(case s.score when 0 then 1 else null end) * 1000) / count(*) as crawlRate)
                              from Score s
                              group by s.team
                              order by crawlRate desc""", [max: maxResults ?: DEFAULT_MAX_RESULTS])
    }

    def getLastCrawlers(maxResults) {
        Challenge.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            createAlias("games", "g")
            createAlias("g.scores", "s")
            projections {
                property "s.id"
                property "s.score"
                property("s.team", "team")
            }
            eq("s.score", 0)
            order("s.id", "desc")
        }
    }
}
