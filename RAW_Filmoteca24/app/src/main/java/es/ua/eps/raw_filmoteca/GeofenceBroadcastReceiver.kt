package es.ua.eps.raw_filmoteca

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            // Manejar errores de Geofencing
            Log.i("tagg", "Geofence error")

            return
        }
        Log.i("tagg", "Dentro de onReceive de Geofence")

        // Manejar transiciones de Geofence
        val geofenceTransition = geofencingEvent.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.i("tagg", "El usuario ha entrado en el Geofence")
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            Log.i("tagg", "El usuario está dentro del Geofence")
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Log.i("tagg", "El usuario ha salido del Geofence")
        }
    }
}
