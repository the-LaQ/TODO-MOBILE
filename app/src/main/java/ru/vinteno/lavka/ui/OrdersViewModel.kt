package ru.vinteno.lavka.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.vinteno.lavka.R
import ru.vinteno.lavka.data.NetworkModule
import ru.vinteno.lavka.data.Order
import ru.vinteno.lavka.data.OrdersRepository

sealed interface OrdersUiState {
    data object Loading : OrdersUiState
    data class Data(val orders: List<Order>) : OrdersUiState
    data object Empty : OrdersUiState
}

class OrdersViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = OrdersRepository(NetworkModule.ordersApi)

    private val _state = MutableStateFlow<OrdersUiState>(OrdersUiState.Loading)
    val state: StateFlow<OrdersUiState> = _state.asStateFlow()

    private val _events = Channel<String>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        refresh()
    }

    fun refresh() {
        _state.value = OrdersUiState.Loading
        viewModelScope.launch {
            val context = getApplication<Application>()
            val orders = try {
                repository.loadNewOrders(context)
            } catch (t: Throwable) {
                // As a safety net, even though repository handles fallback, catch any errors
                _events.trySend(context.getString(R.string.toast_offline))
                emptyList()
            }

            if (orders.isEmpty()) {
                _state.value = OrdersUiState.Empty
                _events.trySend(context.getString(R.string.toast_offline))
            } else {
                // If network failed, repository still provided data. Heuristic: always inform when coming from fallback
                _state.value = OrdersUiState.Data(orders)
            }
        }
    }
}


