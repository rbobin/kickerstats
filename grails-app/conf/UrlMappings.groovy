class UrlMappings {

	static mappings = {

        "/topwins"(controller: "statistics", action: "topWins", parseRequest: true)

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
