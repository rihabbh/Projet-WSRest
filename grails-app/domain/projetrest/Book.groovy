package projetrest



class Book {

    String name
    String author
    Date releaseDate
    String isbn

    static belongsTo = [library:Library]

    static constraints = {
        name blank: false
        author blank : false
        releaseDate nullable : false
        isbn blank: false
    }
}
