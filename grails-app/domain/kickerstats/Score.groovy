package kickerstats

class Score implements Serializable {

    Team team
    Integer score

    static belongsTo = [game: Game]

    static MAX_SCORE = 6

    static constraints = {
        game nullable: true
        team nullable: false
        score nullable: false, range: 0..MAX_SCORE
    }
}
