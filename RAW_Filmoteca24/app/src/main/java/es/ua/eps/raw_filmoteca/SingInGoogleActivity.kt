package es.ua.eps.raw_filmoteca

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import es.ua.eps.raw_filmoteca.data.Film
import es.ua.eps.raw_filmoteca.data.FilmDataSource


class SingInGoogleActivity : AppCompatActivity() {

    private lateinit var signInButton: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in_google)

        signInButton = findViewById(R.id.sign_in_button)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener {
            signIn()
        }

        var f = Film()
        if(intent.extras?.getString("NotifTitulo") == "New"){

            f.title = intent.extras?.getString("Titulo")
            f.director = intent.extras?.getString("Director")
            //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
            f.imageUrl = intent.extras?.getString("Imagen")
            f.comments = ""
            f.format = Film.Format.valueOf(intent.extras?.getString("Formato").toString())
            f.genre = Film.Genre.valueOf(intent.extras?.getString("Genero").toString())
            f.imdbUrl = intent.extras?.getString("Imdb")
            f.year = intent.extras?.getString("Año")!!.toInt()
            FilmDataSource.films.add(f)
//            Handler(Looper.getMainLooper()).post{
//                FilmListActivity.filmAdapter.notifyDataSetChanged()
//            }
        }

        if(intent.extras?.getString("NotifTitulo") == "Delete"){

            val title = intent.extras?.getString("Titulo")

            val find = FilmDataSource.films.find{ it.title == title}
            if(find != null){
                FilmDataSource.films.remove(find)
//                Handler(Looper.getMainLooper()).post{
//                    FilmListActivity.filmAdapter.notifyDataSetChanged()
//                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.result
                if (account != null) {
                    val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("accountName", account.displayName);
                    editor.apply();
                    Log.i("tagg", account.email.toString())
                    startActivity(Intent(this, FilmListActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Google Sign In failed", Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e:Exception){
            Log.e("tagg", e.toString())
        }

    }


}