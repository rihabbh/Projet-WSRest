package projetrest

import grails.converters.JSON

class ApiController {

    
    def book() {
        switch (request.getMethod()) {
            case "POST":
                if (!Library.get(params.library.id)) {
                    render(status: 404, text: "Bibliothèque inexistante (${params.library.id})")
                    response.status = 404
                }
                def bookInstance = new Book(params)
                if (bookInstance.save(flush: true)) {
                    render bookInstance as  JSON
                    response.status = 200
                } else {
                    render text : "Le livre n'a pas pu etre crée, vérifiez que votre requete est correcte"
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
                        render "Vérifiez que la requete est correcte et que chaque paramétre est au bon format"
                    }
                } else {
                    response.status = 404
                    render "Le livre ${params.id} n'existe pas"
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
                    render text : "La bibliothèque n'a pas pu etre crée, vérifiez que votre requete est correcte"
                    response.status = 400
                }
                break

            default: response.status = 404

                break
            case "GET":
                if (params.id) {
                    def libIns = Library.findById(params.id)
                    if(libIns==null){
                        render text : "Cette bibliotheque n'existe pas"
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
                        render "La bibliothèque ${params.id} a été supprimé"
                    } else {
                        response.status = 404 //Not Found
                        render "La  bibliothèque ${params.id} est introuvable."
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
                        render "La  bibliothèque est introuvable"
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
                    render bookInstance as JSON
                    response.status = 201
                } else {
                    response.status = 400
                    render text : "Le livre n'a pas pu etre crée, vérifiez que votre requete est correcte"
                }
                break

            default: response.status = 404
                render text : "La bibliotheque ${params.id} n'existe pas"
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
                            render "Le livre ${params.book.id} a été supprimer de la bibliothèque ${params.id}"
                        }
                     else {
                            response.status = 404
                            render "Le livre ${params.book.id} n'existe pas dans la bibliothèque ${params.id}"
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
                        render "Le livre ${params.book.id} n'existe pas dans la bibliothèque${params.id}"
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


