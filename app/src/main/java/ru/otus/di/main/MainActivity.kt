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
import ru.otus.di.component
import ru.otus.di.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainActivityComponent

    private lateinit var binding: ActivityMainBinding

    private val model: MainViewModel by viewModels { component.vmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = DaggerMainActivityComponent.factory().build(application.component, this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.apply {
            adapter = ConcatAdapter(HeaderAdapter(), component.adapter())
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
                    component.adapter().submitList(data?.items.orEmpty())
                    binding.status.text = data?.syncedAt?.let { getString(R.string.last_synced, it) } ?: getString(R.string.not_synced)
                }
            }
        }
    }
}