package com.yg.drawer // Ensure this matches your package name

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem // Keep if you have other menu items
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment // Import NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
// Remove Firebase imports
// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.FirebaseUser
// import com.google.firebase.auth.ktx.auth
// import com.google.firebase.ktx.Firebase
import com.yg.drawer.databinding.ActivityMainBinding // Ensure this matches your package and binding class
import com.yg.drawer.R // Ensure this is imported for resource IDs
// Uncomment or add the import for onNavDestinationSelected
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    // Remove FirebaseAuth instance
    // private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController // Keep NavController for standard navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Remove Firebase Auth initialization
        // auth = Firebase.auth

        setSupportActionBar(binding.appBarMain.toolbar)

        // Original Snackbar click listener - keep or remove as needed
        binding.appBarMain.toolbar.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        // Get NavController from the NavHostFragment instance
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // IMPORTANT: Only include the IDs of your main content fragments here
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery // Your main content fragments
                // Remove auth fragment IDs:
                // R.id.nav_sign_in, R.id.nav_sign_up, R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Set listener for navigation item selection - handle standard navigation
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation items using NavController
            // This will navigate to the corresponding fragment based on the menu item ID
            // Use the imported function
            val handled = onNavDestinationSelected(menuItem, navController)
            if (handled) {
                // Close the drawer if navigation was handled
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            handled // Return whether the navigation was handled
        }

        // Remove initial UI update based on auth state
        // updateNavigationDrawerUI(auth.currentUser)
    }

    // Remove onStart() override if it was only used for auth check, otherwise keep and remove auth logic
    override fun onStart() {
        super.onStart()
        // Remove auth state check and navigation logic
        // val currentUser = auth.currentUser
        // updateNavigationDrawerUI(currentUser)
        // if (currentUser == null) { ... }
    }

    // Remove the updateNavigationDrawerUI function
    // fun updateNavigationDrawerUI(user: FirebaseUser?) { ... }

    // Remove the signOut function
    // private fun signOut() { ... }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu) // Replace R.menu.main with your actual options menu resource
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main) // Ensure this ID matches your NavHostFragment
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
