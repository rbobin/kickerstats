class UrlMappings {

	static mappings = {

        "/topwins"(controller: "statistics", action: "topWins", parseRequest: true)
        "/topavg"(controller: "statistics", action: "topAvg", parseRequest: true)

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
