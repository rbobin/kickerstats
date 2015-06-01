package kickerstats

import grails.transaction.Transactional
import org.hibernate.ObjectNotFoundException
import org.springframework.validation.ObjectError

@Transactional
class ChallengeService {

    final def FINISHED_NOT_UNIQUE_CODE = "challenge.finished.notunique"

    def getChallenge(long id) {
        Challenge challenge = Challenge.get id
        if (!challenge) {
            throw new ObjectNotFoundException(id, "Challenge")
        }
        challenge
    }

    def createChallenge() {
        if (!Challenge.findAllByFinished(false).empty) {
            def codes = new String[1]
            codes[0] = FINISHED_NOT_UNIQUE_CODE
            def error = new ObjectError("Challenge", codes, null, null)
            throw new ServiceException(errors: [error])
        }
        new Challenge(finished: false).save(flush: true)
    }

    def finishChallenge(long id) {
        Challenge challenge = Challenge.get id
        if (!challenge) {
            throw new ObjectNotFoundException(id, "Challenge")
        }
        challenge.setFinished(true)
        if (!challenge.validate()) {
            throw new ServiceException(errors : challenge.errors.allErrors)
        }
        challenge.save(flush: true)
    }
}
