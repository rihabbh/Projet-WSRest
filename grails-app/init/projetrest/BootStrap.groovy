package projetrest
class BootStrap {

    def init = { servletContext ->

       def ln= new Library(name :"Louis Nucera", address: " 2 Place Yves Klein, 06300 Nice", yearCreated: 2002)
                .addToBooks(new Book(name:"Le rouge et le noir",author: "Stendhal",isbn: "76GFRY75",releaseDate: new Date(1,1,1830)))
                .addToBooks(new Book(name:"les MisÃ©rables",author: "Victor Hugo",isbn: "r657G78G",releaseDate: new Date(1,1,1862))).save(flush:true, failOnError: true)

        def lib = new Library(name :"jean jaures", address: " 2 rue jean jaures, 06300 Nice", yearCreated: 2012)
                .addToBooks(new Book(name:"test1",author: "louise",isbn: "fdfdss5",releaseDate: new Date(1,1,1830)))
                .addToBooks(new Book(name:"test2",author: "anne",isbn: "rdfdf78G",releaseDate: new Date(1,1,1862))).save(flush:true, failOnError: true)

    }

    def destroy = {
    }
}
