package ru.vinteno.lavka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import ru.vinteno.lavka.ui.OrdersUiState
import ru.vinteno.lavka.ui.OrdersViewModel
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.vinteno.lavka.ui.OrderItemDecoration

class MainActivity : AppCompatActivity() {
    private val viewModel: OrdersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Инициализация объектов
        val recycler = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvOrders)
        val progress = findViewById<android.view.View>(R.id.progress)
        val stateError = findViewById<android.view.View>(R.id.stateError)
        val stateEmpty = findViewById<android.view.View>(R.id.stateEmpty)
        val btnRetry = findViewById<android.view.View>(R.id.btnRetry)
        val btnRefresh = findViewById<android.view.View>(R.id.btnRefresh)
        val swipe =
            findViewById<androidx.swiperefreshlayout.widget.SwipeRefreshLayout>(R.id.swipeRefresh)
        swipe.setColorSchemeResources(R.color.primary)

        btnRefresh.setOnClickListener { viewModel.refresh() }

        // TODO: Задание 1 - Создайте адаптер OrdersAdapter и подключите его к RecyclerView

        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_sm)
        recycler.addItemDecoration(OrderItemDecoration(spacing))

        // Слушатель на действия
        btnRetry.setOnClickListener { viewModel.refresh() }
        swipe.setOnRefreshListener { viewModel.refresh() }

        lifecycleScope.launchWhenStarted {

            // Обработка viewModel
            viewModel.state.collect { state ->
                when (state) {
                    is OrdersUiState.Loading -> {
                        progress.isVisible = true
                        recycler.isVisible = false
                        stateError.isVisible = false
                        stateEmpty.isVisible = false
                        swipe.isRefreshing = false
                    }

                    is OrdersUiState.Data -> {
                        progress.isVisible = false
                        recycler.isVisible = true
                        stateError.isVisible = false
                        stateEmpty.isVisible = false
                        swipe.isRefreshing = false
                    }

                    is OrdersUiState.Empty -> {
                        progress.isVisible = false
                        recycler.isVisible = false
                        stateError.isVisible = false
                        stateEmpty.isVisible = true
                        swipe.isRefreshing = false
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.events.collect { message ->
                if (!message.isNullOrBlank()) {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}