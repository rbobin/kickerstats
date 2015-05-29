package kickerstats

class Player implements Serializable {

    String firstname, lastname, nickname

    static constraints = {
        firstname(nullable: false, blank: false, maxSize: 20)
        lastname(nullable: false, blank: false, maxSize: 20, unique: ['firstname'])
        nickname(nullable: true, blank: false, maxSize: 20, unique: true)
    }
}
