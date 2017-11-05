package projetrest

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/books" (resources : 'book')

        "/libraries" (resource: 'library'){
                "/books" (resource: 'book')
        }
        "/api/biblio/$id?/livres"(controller: 'api', action: 'linked' )
    //            {
      //              "/$id?" (controller: 'api', action: 'book' )
    //            }//marche pas
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }


}
