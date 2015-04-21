package kickerstats

class Game implements Serializable {

    Challenge challenge

    static belongsTo = [challenge: Challenge]
    static hasMany = [scores: Score]

    static constraints = {
        challenge(nullable: false, validator: { val, obj ->
            !val.finished
        })
    }
}