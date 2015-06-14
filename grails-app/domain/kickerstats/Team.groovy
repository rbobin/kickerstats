package kickerstats

class Team implements Serializable {

    Player defence
    Player offence
    String title

    static constraints = {
        title nullable: true, blank: false, size: 3..20
        defence nullable: false
        offence nullable: false, unique: "defence", validator: { offence, object, errors ->
            if (offence == object.defence)
                errors.reject("team.offence.sameplayer")
        }
    }

    Collection<Player> getPlayers() {
        [defence, offence]
    }
}
