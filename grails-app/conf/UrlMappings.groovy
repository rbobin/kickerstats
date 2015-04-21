class UrlMappings {

	static mappings = {

        "/$action"(controller: "statistics", parseRequest: true)

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
