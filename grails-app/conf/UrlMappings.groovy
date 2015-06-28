class UrlMappings {

    static mappings = {

        "/$action"(controller: "statistics", parseRequest: true)

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/players/$id"(controller: "player") { action = [PUT: "edit"] }
        "/"(view: "/index")
    }
}
