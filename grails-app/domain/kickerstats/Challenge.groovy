package kickerstats

class Challenge implements Serializable{

    Date dateCreated
    Boolean finished = false

    static hasMany = [games: Game]

    static constraints = {
        finished(validator: {val, obj ->
            if (val)
                games.size() > 0
        })
    }
}
