package es.ua.eps.raw_filmoteca

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.ua.eps.raw_filmoteca.data.Film
import es.ua.eps.raw_filmoteca.data.FilmDataSource
import es.ua.eps.raw_filmoteca.data.FilmsArrayAdapter
import es.ua.eps.raw_filmoteca.databinding.ActivityFilmListBinding

class FirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // Si quieres enviar mensajes a esta instancia de la aplicación o
        // gestionar las suscripciones de esta aplicación en el lado del servidor,
        // envía el token de registro FCM a tu servidor de aplicaciones.
        sendRegistrationToServer(token)
    }

    override fun onCreate() {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token
            Log.d(TAG, msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun sendRegistrationToServer(token: String) {
        // Aquí puedes implementar el código para enviar el token al servidor
        // por ejemplo, usando una llamada a una API REST
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (

            /* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob()
            } else {
                // Handle message within 10 seconds
                //handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {

//            Log.d(TAG, "Message Notification Body: ${it.body}")
//            val notificationText = it.body
//            val titleNotification = it.title
//            val imgNotification = it.imageUrl
//            Log.i(TAG, it.imageUrl.toString())
//
//            val tituloRegex = Regex("Titulo: (.+)")
//            val añoRegex = Regex("Año: (\\d{4})")
//
//            val tituloMatch = tituloRegex.find(notificationText!!)
//            val añoMatch = añoRegex.find(notificationText)
//
//            val titulo = tituloMatch?.groups?.get(1)?.value
//            val año = añoMatch?.groups?.get(1)?.value
//
//            Log.i(TAG, titulo.toString())
//            Log.i(TAG, año.toString())
//
//            var f = Film()
//
//            if(titleNotification == "Pelicula Nueva!"){
//
//                f.title = titulo
//                f.director = ""
//                //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
//                f.imageUrl = imgNotification.toString()
//                f.comments = ""
//                f.format = Film.Format.Digital
//                f.genre = Film.Genre.Comedy
//                f.imdbUrl = ""
//                f.year = año!!.toInt()
//                FilmDataSource.films.add(f)
//                Handler(Looper.getMainLooper()).post{
//                    FilmListActivity.filmAdapter.notifyDataSetChanged()
//                }
//            }


        }

        remoteMessage.data?.let { data ->
            // Itera sobre las claves y valores de los datos adicionales

            var f = Film()

            if(data.get("NotifTitulo") == "New"){

                f.title = data.get("Titulo")
                f.director = data.get("Director")
                //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
                f.imageUrl = data.get("Imagen")
                f.comments = ""
                f.format = Film.Format.valueOf(data.get("Formato").toString())
                f.genre = Film.Genre.valueOf(data.get("Genero").toString())
                f.imdbUrl = data.get("Imdb")
                f.year = data.get("Año")!!.toInt()
                f.lat = data.get("Lat")!!.toDouble()
                f.lon = data.get("Lon")!!.toDouble()
                f.geocercado = data.get("Geo")!!.toBoolean()
                FilmDataSource.films.add(f)
                Handler(Looper.getMainLooper()).post{
                    FilmListActivity.filmAdapter.notifyDataSetChanged()
                }
            }

            if(data.get("NotifTitulo") == "Delete"){

                val title = data.get("Titulo")

                val find = FilmDataSource.films.find{ it.title == title}
                if(find != null){
                    FilmDataSource.films.remove(find)
                    Handler(Looper.getMainLooper()).post{
                        FilmListActivity.filmAdapter.notifyDataSetChanged()
                    }
                }
            }


        }



        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
