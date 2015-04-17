package kickerstats

class Game implements Serializable {

    Challenge challenge
    Team winner, looser
    Integer winnerScore = MAX_SCORE, loserScore

    static final MAX_SCORE = 6

    static belongsTo = [challenge: Challenge]

    static constraints = {
        challenge(nullable: false, validator: { val, obj ->
            !val.finished
        })
        winner(nullable: false)
        looser(nullable: false, validator: { val, obj ->
            val != obj.winner
        })
        winnerScore(nullable: false, validator: { val, obj ->
            val == MAX_SCORE
        })
        loserScore(nullable: false, validator: { val, obj ->
            val < MAX_SCORE && val >= 0
        })
    }
}