package kickerstats

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification

import java.math.MathContext

@Transactional
class StatisticsService {

    final def DEFAULT_MAX_RESULTS = "10"

    def getTopWins(maxResults) {
        def result = Game.createCriteria().list(max: maxResults ?: DEFAULT_MAX_RESULTS) {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("winner", "team")
                groupProperty "winner"
                rowCount "wins"
                order("wins", "desc")
            }
        }
        return result
    }

    def getTopAverageScore(maxResults) {
        def result = []
        def winners = Game.createCriteria().list {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("winner", "team")
                groupProperty "winner"
                sum("winnerScore", "score")
                rowCount "wins"
            }
        }

        def losers = Game.createCriteria().list {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("loser", "team")
                groupProperty "loser"
                sum("loserScore", "score")
                rowCount "losses"
            }
        }

        def games = [:]
        winners.each {
            games[it.team] = [games: it.wins, score: it.score]
        }



        losers.each {
            def a = games[it.team]
            if(!a) {
                games[it.team] = [games: it.losses, score: it.score]
            } else {
                games[it.team] = [games: a.games + it.losses, score: a.score + it.score]
            }
        }

        games.each {team, stats ->
            def avg = stats.score / stats.games
            avg = avg.round(new MathContext(3))
            result << [team: team, avg: avg]
        }

        result.sort {a, b ->
            - (a.avg <=> b.avg)
        }
        return result
    }

    def getTopRate(maxResults) {
        def result = []
        def winners = Game.createCriteria().list {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("winner", "team")
                groupProperty "winner"
                rowCount "wins"
            }
        }

        def losers = Game.createCriteria().list {
            resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
            projections {
                property("winner", "team")
                groupProperty "winner"
                rowCount "wins"
            }
        }

        def winnersMap = [:]
        winners.each { team ->
            winnersMap[team.first().id] = team
        }

        def losersMap = [:]
        losers.each { team ->
            losersMap[team.first().id] = team
        }


    }
}
