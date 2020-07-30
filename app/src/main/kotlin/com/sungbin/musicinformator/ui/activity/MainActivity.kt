package com.sungbin.musicinformator.ui.activity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commitNow
import com.sungbin.musicinformator.R
import com.sungbin.musicinformator.databinding.ActivityMainBinding
import com.sungbin.musicinformator.ui.fragment.main.MainFragment
import com.sungbin.musicinformator.ui.fragment.search.SearchFragment
import com.sungbin.musicinformator.utils.SongUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.songItem = SongUtils.getTestSongItem()
        binding.invalidateAll()

        supportFragmentManager.commitNow {
            add(R.id.fl_container, MainFragment.newInstance())
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1000
        )

        bnv_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main -> {
                    supportFragmentManager.commitNow {
                        replace(R.id.fl_container, MainFragment.newInstance())
                    }
                }
                R.id.menu_search -> {
                    supportFragmentManager.commitNow {
                        replace(R.id.fl_container, SearchFragment.newInstance())
                    }
                }
                R.id.menu_library -> {

                }
                else -> {

                }
            }
            true
        }
    }
}