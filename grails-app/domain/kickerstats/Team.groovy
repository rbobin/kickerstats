package kickerstats

class Team implements Serializable {

    Player defence, offence
    String title

    static constraints = {
        defence(nullable: false, validator: { val, obj ->
            val != obj.offence
        })
        offence(nullable: false, unique: ['defence'])
        title(nullable: true, blank: false)
    }
}
