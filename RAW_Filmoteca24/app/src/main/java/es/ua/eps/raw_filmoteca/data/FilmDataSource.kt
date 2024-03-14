package es.ua.eps.raw_filmoteca.data

import es.ua.eps.raw_filmoteca.R

//-------------------------------------
object FilmDataSource {
    val films: MutableList<Film> = mutableListOf<Film>()

    //---------------------------------
    init {
        var f = Film()
        f.title      = "Regreso al futuro"
        f.director   = "Robert Zemeckis"
        f.imageResId = R.drawable.regresoalfuturo
        f.comments   = ""
        f.format     = Film.Format.Digital
        f.genre      = Film.Genre.SciFi
        f.imdbUrl    = "http://www.imdb.com/title/tt0088763"
        f.year       = 1985
        f.lat = 33.76346227559892
        f.lon = -118.23701106456677
        f.geocercado = false
        films.add(f)

        f = Film()
        f.title = "Los Cazafantasmas"
        f.director = "Ivan Reitman"
        f.imageResId = R.drawable.loscazafantasmas
        f.comments = ""
        f.format = Film.Format.BlueRay
        f.genre = Film.Genre.Comedy
        f.imdbUrl = "https://www.imdb.com/title/tt0087332"
        f.year = 1984
        f.lat = 40.71550940318287
        f.lon = -74.00574012415866
        f.geocercado = false
        films.add(f)

        f = Film()
        f.title = "La princesa prometida"
        f.director = "Rob Reiner"
        //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
        f.imageUrl = "https://es.web.img2.acsta.net/pictures/19/07/03/16/08/2300654.jpg"
        f.comments = ""
        f.format = Film.Format.Digital
        f.genre = Film.Genre.Fantasy
        f.imdbUrl = "https://www.filmaffinity.com/es/film579602.html"
        f.year = 1987
        f.lat = 51.542289389305985
        f.lon = -0.6473200399613228
        f.geocercado = false
        films.add(f)

        f = Film()
        f.title = "La princesa desprometida"
        f.director = "Serhii"
        //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
        f.imageUrl = "https://es.web.img2.acsta.net/pictures/19/07/03/16/08/2300654.jpg"
        f.comments = ""
        f.format = Film.Format.Digital
        f.genre = Film.Genre.Fantasy
        f.imdbUrl = "https://www.filmaffinity.com/es/film579602.html"
        f.year = 1987
        f.lat = 38.5460487
        f.lon = -0.1277011
        f.geocercado = false
        films.add(f)
        // Añade tantas películas como quieras!
    }

    //---------------------------------
    fun add() {
        films.add(Film())
    }

    //---------------------------------
    fun add(f: Film) {
        films.add(f)
    }

    fun delete(){
        films.removeLast()
    }

}