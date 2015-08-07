package kickerstats

class ChallengeController extends BaseController {

    def createChallenge() {
        if (Challenge.currentChallenge || !(new Challenge().save(flush: true))) {
            renderConflict 'Unable to start new challenge: finish current challenge first'
        } else {
            renderSuccess challenge: Challenge.currentChallenge
        }
    }

    def findCurrentChallenge() {
        renderSuccess challenge: Challenge.currentChallenge
    }
}