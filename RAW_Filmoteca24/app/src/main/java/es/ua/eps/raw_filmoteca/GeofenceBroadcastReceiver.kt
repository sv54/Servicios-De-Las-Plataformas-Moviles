package es.ua.eps.raw_filmoteca

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            // Manejar errores de Geofencing
            return
        }

        // Manejar transiciones de Geofence
        val geofenceTransition = geofencingEvent.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            // Entrada al Geofence
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            // Salida del Geofence
        }
    }
}
