package kickerstats

class Score implements Serializable {

    Game game
    Team team
    Integer score

    static MAX_SCORE = 6

    static constraints = {
        game nullable: false
        team nullable: false
        score(nullable: false, validator: {val, obj ->
            val <=MAX_SCORE && val >= 0
        })
    }
}
