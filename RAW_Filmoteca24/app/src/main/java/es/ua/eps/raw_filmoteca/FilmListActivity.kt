package es.ua.eps.raw_filmoteca

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import es.ua.eps.raw_filmoteca.data.Film
import es.ua.eps.raw_filmoteca.data.FilmDataSource
import es.ua.eps.raw_filmoteca.data.FilmsArrayAdapter
import es.ua.eps.raw_filmoteca.databinding.ActivityFilmListBinding


//-------------------------------------
class FilmListActivity : BaseActivity()
    , AdapterView.OnItemClickListener {

    private lateinit var bindings : ActivityFilmListBinding
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var account: GoogleSignInAccount;
    //---------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
        checkPermission(Manifest.permission.INTERNET, {
            filmAdapter.notifyDataSetChanged()
        })
        account = GoogleSignIn.getLastSignedInAccount(this)!!
        mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this) {
            val intent = Intent(this, SingInGoogleActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun disconnect() {
        mGoogleSignInClient?.revokeAccess()?.addOnCompleteListener(this) {
            val intent = Intent(this, SingInGoogleActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        menuInflater.inflate(R.menu.mymenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.about) {
            intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.close) {
            signOut()
            finish()
        }
        if (id == R.id.disconnect) {
            disconnect()
            finish()
        }
        if (id == R.id.add) {
            var f = Film()

            f.title = "Kung Fu Panda 4"
            f.director = "Mike Mitchell, Stepahine Stine"
            //f.imageUrl = "http://www.imdb.com/title/tt0088763"//"https://pics.filmaffinity.com/the_princess_bride-741508250-large.jpg"
            f.imageUrl = "https://dx35vtwkllhj9.cloudfront.net/universalstudios/kung-fu-panda-4/images/regions/us/onesheet.jpg"
            f.comments = ""
            f.format = Film.Format.Digital
            f.genre = Film.Genre.Comedy
            f.imdbUrl = "https://www.imdb.com/title/tt21692408/"
            f.year = 2024
            FilmDataSource.films.add(f)

            filmAdapter.notifyDataSetChanged()
        }
        if (id == R.id.delete){
            FilmDataSource.delete()
            filmAdapter.notifyDataSetChanged()

        }
        return super.onOptionsItemSelected(item)
    }
    //---------------------------------
    override fun onRestart() {
        super.onRestart()
        filmAdapter.notifyDataSetChanged()
    }

    //---------------------------------
    private fun initUI() {
        bindings = ActivityFilmListBinding.inflate(layoutInflater)
        with(bindings) {
            setContentView(root)
            filmAdapter = FilmsArrayAdapter(this@FilmListActivity, android.R.layout.simple_list_item_1, FilmDataSource.films)
            list.onItemClickListener = this@FilmListActivity
            list.adapter = filmAdapter
        }
    }

    //---------------------------------
    // AdapterView.OnItemClickListener (ListView)
    //---------------------------------
    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, index: Int, l: Long) {
        val intent = Intent(this, FilmDataActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        intent.putExtra(FilmDataActivity.EXTRA_FILM_ID, index)
        startActivity(intent)
    }

    companion object{
        lateinit var filmAdapter: FilmsArrayAdapter
    }

}
