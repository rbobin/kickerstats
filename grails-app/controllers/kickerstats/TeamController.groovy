package kickerstats

class TeamController extends BaseController {

    def findTeam(Team team) {
        if (team) {
            renderSuccess(team: team)
        } else {
            renderNotFound("team")
        }
    }

    def createTeam(TeamCommand teamCommand) {
        Team team = teamCommand.team
        if (team.save()) {
            renderSuccess(team: team)
        } else {
            renderValidationErrors(team)
        }
    }

    def updateTeam(Team team) {
        if (team) {
            team.properties = params
            if (team.save()) {
                renderSuccess(team: team)
            } else {
                renderValidationErrors(team)
            }
        } else {
            renderNotFound("team")
        }
    }
}

class TeamCommand {

    Player defence
    Player offence
    String title

    def getTeam() {
        new Team(defence: defence, offence: offence, title: title)
    }
}