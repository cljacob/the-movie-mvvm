package co.jacobcloldev.apps.themoviecl.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import co.jacobcloldev.apps.themoviecl.R
import co.jacobcloldev.apps.themoviecl.TheMovieCLApp
import co.jacobcloldev.apps.themoviecl.di.AppSubcomponents

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var subcomponents: AppSubcomponents

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TheMovieApp_NoActionBar)

        subcomponents = (applicationContext as TheMovieCLApp).appComponent.appSubcomponents().create()
        subcomponents.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}