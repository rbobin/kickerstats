package kickerstats

import grails.transaction.Transactional
import org.hibernate.ObjectNotFoundException

@Transactional
class TeamService {

    @Transactional(readOnly = true)
    def getTeam(Long id) {
        Team team = Team.get id
        if (!team) {
            throw new ObjectNotFoundException(id, "Team")
        }
        team
    }


    def createTeam(Map params) {
        Team team = new Team(params)
        if (!team.validate()) {
            throw new ServiceException(errors: team.errors.allErrors)
        }
        team.save flush: true
    }

    def editTeam(Map params = [:]) {
        def id = params.id as Long
        Team team = Team.get id
        if (!team) {
            throw new ObjectNotFoundException(id, "Team")
        }
        team.properties = params
        if (!team.validate()) {
            throw new ServiceException(errors: team.errors.allErrors)
        }
        team.save flush: true
    }
}
