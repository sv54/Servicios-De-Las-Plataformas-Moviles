package es.ua.eps.raw_filmoteca

import android.R
import android.app.Application
import android.content.Context
import android.view.Menu


//-------------------------------------
class Filmoteca : Application() {

    //---------------------------------
    companion object {
        private var mContext: Context? = null
        val context: Context?
            get() = mContext
    }

    //---------------------------------
    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    //---------------------------------
    override fun onTerminate() {
        super.onTerminate()
        mContext = null
    }



}