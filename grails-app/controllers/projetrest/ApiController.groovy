package projetrest

import grails.converters.JSON

class ApiController {

 //   static allowedMethods = [book: "POST", book: "PUT", book: "DELETE", books: "GET",book: "GET"]


    def book() {
        switch (request.getMethod()) {
            case "POST":
                if (!Library.get(params.library.id)) {
                    render(status: 404, text: "Library inexistante (${params.library.id})")
                }
                def bookInstance = new Book(params)
                if (bookInstance.save(flush: true)) {
                    render bookInstance as  JSON
                    response.status = 200
                } else {
                    response.status = 400
                }
                break

            default: response.status = 405

                break

            case "GET":
                if (params.id) {
                    def bookIns = Book.findById(params.id)
                    if(bookIns==null){
                        render text : "Ce livre n'existe pas"
                        response.status = 404
                    } else {
                    render Book.findById(params.id) as JSON
                    response.status = 200 }
                } else {
                    render Book.getAll() as JSON
                    response.status = 200

                }
                response.status = 201
                break
            case "DELETE":
                if (params.id) {
                    def book = Book.findById(params.id)
                    if (book) {
                        book.delete(flush: true)
                        response.status = 200 //Not Found
                        render "Le livre ${params.id} a été Supprimé."
                    } else {
                        response.status = 404 //Not Found
                        render "Le livre ${params.id}  est inexistant."
                    }
                }
                break
            case "PUT":
                def bookIns = Book.findById(params.id)
                if (params.id) {
                    bookIns.properties = params
                    if(bookIns.save(flush : true)){
                        response.status = 200
                        render bookIns as JSON
                    } else {
                        response.status = 400
                        render "Vérifiez que le format des paramètres est correctes"
                    }
                } else {
                    response.status = 404
                    render "Le livre n'existe pas"
                }
                break


                break
        }
    }


    def library() {
        switch (request.getMethod()) {
            case "POST":
                def libInstance = new Library(params)
                libInstance.setBooks(null)
                if (libInstance.save(flush: true)) {
                    render libInstance as JSON
                    response.status = 201
                } else {
                    response.status = 400
                }
                break

            default: response.status = 404

                break
            case "GET":
                if (params.id) {
                    def libIns = Library.findById(params.id)
                    if(libIns==null){
                        render text : "Cette librarie n'existe pas"
                        response.status = 404
                    } else {
                    render Library.findById(params.id) as JSON
                        response.status = 200
                    }
                } else {
                    render Library.getAll() as JSON
                    response.status = 200
                }
                response.status = 201
                break
            case "DELETE":
                if (params.id) {
                    def lib = Library.findById(params.id)
                    if (lib) {
                        lib.delete(flush: true)
                        response.status = 200 //Not Found
                        render "La librarie ${params.id} a été supprimé"
                    } else {
                        response.status = 404 //Not Found
                        render "La librarie  ${params.id} est introuvable."
                    }
                }
                break

            case "PUT":
                def libIns = Library.findById(params.id)
                if (params.id) {
                    libIns.properties = params
                    if(libIns.save(flush : true)){
                        response.status = 200 // OK
                        render libIns as JSON
                    }
                    } else {
                        response.status = 404
                        render "La librairie est introuvable"
                    }
                break

                    break
                }
        }

    def linked(Library library){
        switch (request.getMethod()) {
            case "POST":
                def bookInstance = new Book(params)
                bookInstance.setLibrary(library)
                if (bookInstance.save(flush: true)) {

                    response.status = 201
                } else {
                    response.status = 400
                }
                break

            default: response.status = 404

                break

            case "GET":
                if (library==null){
                    response.status = 404
                    render text:"La bibliotheque n'existe pas"
                } else if(params.book.id) {
                    def bookI =  Book.findById(params.book.id)
                    if (bookI in library.getBooks()){
                    render bookI as JSON
                    response.status = 200
                    } else {
                        render text:"Ce livre n'est pas dans cette Bibliothéque"
                        response.status = 404
                    }
                }
        else {
                        render library.getBooks() as JSON
                    response.status = 200
                }
                break
            case "DELETE":
                if (params.id) {
                    if (params.book.id) {
                        def book = Book.findById(params.book.id)
                        if(book in library.books) {
                            library.removeFromBooks(book)
                            book.delete(flush: true)
                            library.save(flush: true)
                            response.status = 200
                            render "Le livre ${params.book.id} a été supprimer de la librarie ${params.id}"
                        }
                     else {
                            response.status = 404
                            render "Le livre ${params.book.id} n'existe pas dans la librarie ${params.id}"
                            }
                    }
                }
                break
            case "PUT":
                if (params.id) {
                    def bookIns = Book.findById(params.book.id)
                    if (bookIns in library.books) {
                        bookIns.properties = params
                        if (bookIns.save(flush: true)) {
                            response.status = 200
                            render bookIns as JSON
                        }
                    } else {
                        response.status = 404
                        render "Le livre ${params.book.id} n'existe pas dans la librarie ${params.id}"
                    }
                } else {
                    response.status = 404//Internal Server Error
                    render text:"La bibliotheque n'existe pas"
                }
                break


                break
        }


    }

    def libraries() {
        switch (request.getMethod()) {
            case "GET":
                render Library.getAll() as JSON
                response.status = 201
                break
                break
        }
    }


        def books() {
            switch (request.getMethod()) {
                case "GET":
                    render Book.getAll() as JSON
                    response.status = 201
                    break
                    break
            }
        }
    }


