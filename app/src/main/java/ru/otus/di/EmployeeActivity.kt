package ru.otus.di

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import ru.otus.di.databinding.ActivityEmployeeBinding
import ru.otus.di.domain.data.Employee

class EmployeeActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_EID = "extra_eid"

        fun launch(context: Activity, employeeId: Int) {
            val intent = Intent(context, EmployeeActivity::class.java).apply {
                putExtra(EXTRA_EID, employeeId)
            }
            context.startActivity(intent)
        }

        val Intent.employeeId: Int get() = getIntExtra(EXTRA_EID, -1)
    }

    private lateinit var binding: ActivityEmployeeBinding
    private val model: EmployeeViewModel by viewModels {
        EmployeeViewModel.Factory(application, intent.employeeId)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.setNavigationOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.data.collect { data ->
                    when (data) {
                        null -> hide()
                        else -> content(data)
                    }
                }
            }
        }
    }

    private fun hide() = with(binding) {
        name.isVisible = false
        salary.isVisible = false
        age.isVisible = false
    }

    private fun content(data: Employee) = with(binding) {
        name.isVisible = true
        salary.isVisible = true
        age.isVisible = true
        title.title = data.name
        name.text = getString(R.string.details_name, data.name)
        salary.text = getString(R.string.details_salary, data.salary)
        age.text = getString(R.string.details_age, data.age)
    }
}