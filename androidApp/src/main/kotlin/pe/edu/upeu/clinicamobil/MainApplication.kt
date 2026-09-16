package pe.edu.upeu.clinicamobil

import android.app.Application
import pe.edu.upeu.clinicamobil.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
