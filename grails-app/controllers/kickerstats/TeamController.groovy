package kickerstats

class TeamController extends BaseController {

    def findTeam(Team team) {
        if (team) {
            renderSuccess team: team
        } else {
            renderNotFound "team"
        }
    }

    def createTeam() {
        save new Team(params)
    }

    def updateTeam(Team team) {
        if (team) {
            team.properties = params
            save team
        } else {
            renderNotFound "team"
        }
    }

    private void save(Team team){
        if (team.save()) {
            renderSuccess team: team
        } else {
            renderValidationErrors team
        }
    }
}