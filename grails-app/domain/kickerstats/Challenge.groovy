package kickerstats

class Challenge implements Serializable {

    Date dateCreated
    Boolean finished = false // TODO this is so lame.. do something about it!

    static hasMany = [games: Game]

    static constraints = {
        finished nullable: false, validator: { finished, object, errors ->
            if (finished && !object.games)
                errors.rejectValue(
                        "finished",
                        "challenge.finished.emptychallenge",
                        "Empty challenge can't be finished")
            if (!finished && Challenge.findAllByFinished(false).size() > 0) {
                errors.reject("challenge.existingunfinished", "")
            }
        }
    }
}
