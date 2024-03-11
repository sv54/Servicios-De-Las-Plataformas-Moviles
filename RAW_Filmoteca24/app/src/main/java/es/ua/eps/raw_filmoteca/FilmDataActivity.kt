package es.ua.eps.raw_filmoteca

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import es.ua.eps.raw_filmoteca.data.FilmDataSource
import es.ua.eps.raw_filmoteca.databinding.ActivityFilmDataBinding
import es.ua.eps.raw_filmoteca.tools.OnTaskCompleted
import es.ua.eps.raw_filmoteca.tools.TaskRunner

class FilmDataActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_FILM_ID = "EXTRA_FILM_ID"
    }
    private lateinit var botonMaps: Button
    private lateinit var geocercadoButton: Button
    private lateinit var bindings : ActivityFilmDataBinding
    private var index = -1
    private lateinit var geofencingClient: GeofencingClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()

        geofencingClient = LocationServices.getGeofencingClient(this)

        botonMaps = findViewById(R.id.mapaButton)

        botonMaps.setOnClickListener {
            intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("INDEXPELICULA",index)
            startActivity(intent)
        }

        index = intent.getIntExtra(EXTRA_FILM_ID, -1)
        loadMovieData(index)



        geocercadoButton = findViewById(R.id.geocercado)
        if (FilmDataSource.films[index].geocercado!!){
            geocercadoButton.text = "Desactivar Geocercado"
        }
        else if (!FilmDataSource.films[index].geocercado!!){
            geocercadoButton.text = "Activar Geocercado"
        }

        geocercadoButton.setOnClickListener {
            if (!FilmDataSource.films[index].geocercado!!){
                geocercadoButton.text = "Desactivar Geocercado"
                FilmDataSource.films[index].geocercado = true
                addGeofences()
            }
            else if (FilmDataSource.films[index].geocercado!!){
                geocercadoButton.text = "Activar Geocercado"
                FilmDataSource.films[index].geocercado = false
                removeGeofences(index.toString())
            }
        }




    }
    fun removeGeofences(geofenceRequestId: String) {
        val geofenceRequestIds = mutableListOf<String>()
        geofenceRequestIds.add(geofenceRequestId)

        geofencingClient.removeGeofences(geofenceRequestIds)
            .addOnSuccessListener {
                // Geofence(s) removidos exitosamente
                Log.i("tagg", "Geofences quitado exitosamente")

            }
            .addOnFailureListener {
                // Error al remover el Geofence
                Log.i("tagg", "Error al quitar los Geofences")

            }
    }

    fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    private fun addGeofences() {
        // Verificar permisos
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Manejar permisos no concedidos
            return
        }
        try {
            val geofenceList = mutableListOf<Geofence>()

            val geofence = Geofence.Builder()
                .setRequestId(index.toString()) // Identificador único para el Geofence
                .setCircularRegion(FilmDataSource.films[index].lat!!,FilmDataSource.films[index].lon!!, 1500.0F) // Coordenadas del centro y radio en metros
                .setExpirationDuration(Geofence.NEVER_EXPIRE) // Duración de la permanencia del Geofence
                .setTransitionTypes(        Geofence.GEOFENCE_TRANSITION_ENTER or
                        Geofence.GEOFENCE_TRANSITION_DWELL or
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .setLoiteringDelay(5)// Tipos de transición a ser monitoreados
                .build()

            geofenceList.add(geofence)

            val geofencingRequest = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER) // Estado inicial del Geofence
                .addGeofences(geofenceList)
                .build()
            val pendingIntent = getGeofencePendingIntent()
//            val pendingIntent: PendingIntent = // PendingIntent para manejar las transiciones de Geofence
//                PendingIntent.getBroadcast(this, 0, Intent(this, GeofenceBroadcastReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

            geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener {
                    Log.i("tagg", "Geofences añadidos exitosamente")
                    // Geofences añadidos exitosamente
                }
                .addOnFailureListener {
                    Log.i("tagg", "Error al añadir los Geofences")
                    // Error al añadir los Geofences
                }
        }catch (e: Exception){
            Log.e("tagg", e.toString())
        }


    }

    private fun initUI() {
        bindings = ActivityFilmDataBinding.inflate(layoutInflater)
        with(bindings) {
            setContentView(root)
        }
    }

    private fun loadMovieData(index: Int) {
        if (index != -1) {
            val film = FilmDataSource.films[index]

            when {
                film.image != null -> {
                    bindings.imgCover.setImageBitmap(film.image)
                }

                film.imageResId != 0 -> {
                    bindings.imgCover.setImageResource(film.imageResId)
                }

                film.imageUrl != null -> {
                    TaskRunner.doInBackground(bindings.imgCover, film.imageUrl, object : OnTaskCompleted {
                        override fun onTaskCompleted(result: Bitmap?) {
                            film.image = result
                        }
                    })
                }
            }

            bindings.movieTitle.text = film.title
            bindings.tvDirector.text = film.director
            bindings.tvYear.text     = film.year.toString()
            //bindings.tvFormat.text   = "${resources.getStringArray(R.array.formats)[film.format]}, ${resources.getStringArray(R.array.genres)[film.genre]}"
            bindings.tvComments.text = film.comments
        }
    }
}