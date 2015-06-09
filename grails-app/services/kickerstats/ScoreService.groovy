package kickerstats

import grails.transaction.Transactional
import org.hibernate.ObjectNotFoundException

@Transactional
class ScoreService {

    @Transactional(readOnly = true)
    def getScore(Long id) {
        Score score = Score.get id
        if (!score) {
            throw new ObjectNotFoundException(id, "Score")
        }
        score
    }

    def createScore(Map params) {
        Score score = new Score(params)
        if (!score.validate()) {
            throw new ServiceException(errors: score.errors.allErrors)
        }
        score.save flush: true
    }

    def deleteScore(Map params = [:]) {
        def id = params.id as Long
        Score score = Score.get id
        if (!score) {
            throw new ObjectNotFoundException(id, "Score")
        }
        score.delete()
    }
}
