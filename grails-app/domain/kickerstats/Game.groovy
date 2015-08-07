package kickerstats

class Game implements Serializable {

    Team winners
    Team losers
    Integer winnersScore
    Integer losersScore

    final static Integer MAX_SCORE = 6;

    static belongsTo = [challenge: Challenge]

    static constraints = {
        winners nullable: false
        losers nullable: false, validator: { losers, object, errors ->
            if (losers && object.winners) {
                if (losers.id && object.winners.id && losers.id == object.winners.id) {
                    errors.reject("game.sameteam",
                            "Same team playing detected")
                } else if (losers.players*.id.intersect(object.winners.players*.id)) {
                    errors.reject("game.playerintersection",
                            "Player performing in both teams detected")
                }
            }
        }
        winnersScore nullable: false, validator: { winnersScore, object, errors ->
            if (winnersScore != MAX_SCORE) {
                errors.rejectValue('winnersScore',
                        "game.winnersScore.notmax",
                        "Winners score must be $MAX_SCORE")
            }
        }
        losersScore nullable: false, range: 0..(MAX_SCORE - 1)
        challenge validator: { challenge, object, errors ->
            if (!object.id && !challenge.current) {
                errors.reject(
                        "game.challenge.current",
                        "Unable add a game to not current challenge")
            }
        }
    }

    def beforeValidate() {
        this.challenge = Challenge.currentChallenge
    }
}