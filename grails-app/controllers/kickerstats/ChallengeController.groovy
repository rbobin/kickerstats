package kickerstats

import grails.converters.JSON

class ChallengeController extends BaseController {

    def createChallenge() {
        if (currentChallenge || !(new Challenge().save(flush: true))) {
            renderConflict('Unable to start new challenge: finish current challenge first')
            return
        }

        render([success: true] as JSON)
    }

    def findCurrentChallenge() {
        def responseMap = [
                success  : true,
                challenge: currentChallenge
        ]
        render(responseMap as JSON)
    }

    private getCurrentChallenge() {
        Challenge.findByFinished(false)
    }
}
