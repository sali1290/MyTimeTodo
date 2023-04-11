package com.example.mytimetodo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.mytimetodo.R
import com.example.mytimetodo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.apply {
            background = null
            selectedItemId = R.id.menu_item_daily_work
        }

        binding.apply {
            fab.setOnClickListener {
                bottomAppBar.visibility = View.GONE
                fab.visibility = View.GONE
                val navHostFragment =
                    navHostFragment.getFragment<NavHostFragment>()
                navHostFragment.navController.navigate(R.id.addWorkFragment)
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.menu_item_other_work -> {
                    val navHostFragment =
                        binding.navHostFragment.getFragment<NavHostFragment>()
                    navHostFragment.navController.navigate(R.id.otherWorksFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_item_daily_work -> {
                    val navHostFragment =
                        binding.navHostFragment.getFragment<NavHostFragment>()
                    navHostFragment.navController.navigate(R.id.dailyRoutineFragment)
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }

            }
        }


    }

}