package kickerstats

class Player implements Serializable {

    String firstname
    String lastname
    String nickname

    static constraints = {
        firstname nullable: false, blank: false, size: 2..20
        lastname nullable: false, blank: false, size: 2..20, unique: ['firstname']
        nickname nullable: true, blank: false, size: 2..20, unique: true
    }
}
