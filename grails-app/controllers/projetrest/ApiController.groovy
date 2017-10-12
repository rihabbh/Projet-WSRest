package projetrest

import grails.converters.JSON

class ApiController {

    def book() {

        //  render "ok"

        switch (request.getMethod()) {
            case "POST":
                if (!Library.get(params.library.id)) {
                    render(status: 400, text: "Library inexistante (${params.library.id})")
                }
                def bookInstance = new Book(params)
                if (bookInstance.save(flush: true)) {

                    response.status = 201
                } else {
                    response.status = 400
                }
                break

                default: response.status = 405

                break

            case "GET" :
                render Book.getAll() as JSON
        }
    }

}
