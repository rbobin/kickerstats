package kickerstats

class Challenge implements Serializable {

    Date dateCreated
    Boolean current = true

    static hasMany = [games: Game]

    static constraints = {
        current nullable: true, unique: true, validator: { current, object, errors ->
            if (!current && !object.games)
                errors.rejectValue(
                        "current",
                        "challenge.finished.emptychallenge",
                        "Empty challenge can't be finished")
        }
    }

    static Challenge getCurrentChallenge() {
        findByCurrent(true)
    }
}
