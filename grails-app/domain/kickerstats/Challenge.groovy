package kickerstats

class Challenge implements Serializable {

    Date dateCreated
    Boolean finished = false

    static hasMany = [games: Game]

    static constraints = {
        finished nullable: false, validator: { finished, object, errors ->
            if (finished && !object.games)
                errors.rejectValue(
                        "finished",
                        "challenge.finished.emptychallenge",
                        "Empty challenge can't be finished")
        }
    }
}
