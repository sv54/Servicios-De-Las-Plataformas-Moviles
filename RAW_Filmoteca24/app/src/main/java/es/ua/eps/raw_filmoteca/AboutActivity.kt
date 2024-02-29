package es.ua.eps.raw_filmoteca

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class AboutActivity : AppCompatActivity() {

    lateinit var nombre: TextView
    lateinit var id: TextView
    lateinit var email: TextView
    lateinit var fotoPerfil: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true);


        val account = GoogleSignIn.getLastSignedInAccount(this)
        val photoURL = account?.photoUrl

        id = findViewById(R.id.idusuario)
        email = findViewById(R.id.email)
        nombre = findViewById(R.id.nombreCompleto)
        fotoPerfil = findViewById(R.id.imagenPerfil)

        id.text = account?.id ?: "null"
        email.text = account?.email ?: "null"
        nombre.text = account?.displayName ?: "null"
        if (photoURL != null) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val url = URL(photoURL.toString())
                    val connection = url.openConnection() as HttpURLConnection
                    connection.connect()

                    val inputStream = BufferedInputStream(connection.inputStream)
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    runOnUiThread {
                        fotoPerfil.setImageBitmap(bitmap)
                    }

                    inputStream.close()
                    connection.disconnect()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}