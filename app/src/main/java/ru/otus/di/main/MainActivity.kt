package ru.otus.di.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch
import ru.otus.di.R
import ru.otus.di.databinding.ActivityMainBinding
import ru.otus.di.details.EmployeeActivity

class MainActivity : AppCompatActivity() {
    private val employeeAdapter = EmployeeAdapter {
        EmployeeActivity.launch(this, it)
    }
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels {
        MainViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.apply {
            adapter = ConcatAdapter(HeaderAdapter(), employeeAdapter)
            addItemDecoration(
                MaterialDividerItemDecoration(
                    context,
                    MaterialDividerItemDecoration.VERTICAL
                )
            )
        }
        binding.load.setOnClickListener {
            model.load()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.data.collect { data ->
                    employeeAdapter.submitList(data?.items.orEmpty())
                    binding.status.text = data?.syncedAt?.let { getString(R.string.last_synced, it) } ?: getString(R.string.not_synced)
                }
            }
        }
    }
}