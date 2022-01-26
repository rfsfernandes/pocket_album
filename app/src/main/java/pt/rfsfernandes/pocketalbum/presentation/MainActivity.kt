package pt.rfsfernandes.pocketalbum.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.mainNavContainer) as NavHostFragment
    navController = navHostFragment.navController
//    navController = findNavController(R.id.mainNavContainer)
  }
}