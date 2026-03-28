package com.tech.cursor.presentation.Home

import com.tech.cursor.presentation.common.models.ConnectFilter
import com.tech.cursor.presentation.common.models.Connection
import com.tech.cursor.presentation.common.models.OnlineStatus
import com.tech.cursor.presentation.common.models.TradeStatus
import com.tech.cursor.presentation.common.sampleConnections

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ConnectUiState(
    val allConnections: List<Connection> = sampleConnections,
    val activeFilter: ConnectFilter = ConnectFilter.ALL,
    val searchQuery: String = "",
    val toastMessage: String? = null
) {
    val filtered: List<Connection>
        get() {
            val q = searchQuery.lowercase()
            return allConnections.filter { c ->
                val matchesFilter = when (activeFilter) {
                    ConnectFilter.ALL         -> true
                    ConnectFilter.ONLINE      -> c.status == OnlineStatus.ONLINE
                    ConnectFilter.NEW_MATCH   -> c.isNewMatch
                    ConnectFilter.ACTIVE_TRADE -> c.tradeStatus == TradeStatus.ACTIVE
                    ConnectFilter.COMPLETED   -> c.tradeStatus == TradeStatus.COMPLETED
                }
                val matchesSearch = q.isEmpty() ||
                        c.name.lowercase().contains(q) ||
                        c.title.lowercase().contains(q) ||
                        c.offersSkill.lowercase().contains(q) ||
                        c.wantsSkill.lowercase().contains(q) ||
                        c.district.lowercase().contains(q)
                matchesFilter && matchesSearch
            }
        }

    val totalConnections: Int  get() = allConnections.size
    val totalTrades: Int       get() = allConnections.sumOf { it.trades }
    val avgRating: Float       get() = allConnections.map { it.rating }.average().toFloat()
    val onlineCount: Int       get() = allConnections.count { it.status == OnlineStatus.ONLINE }
}

class ConnectViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ConnectUiState())
    val uiState: StateFlow<ConnectUiState> = _uiState.asStateFlow()

    fun onFilterChange(filter: ConnectFilter) {
        _uiState.update { it.copy(activeFilter = filter) }
    }

    fun onSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onMessageClick(connection: Connection) {
        _uiState.update { it.copy(toastMessage = "Opening chat with ${connection.name}...") }
    }

    fun onTradeClick(connection: Connection) {
        _uiState.update { it.copy(toastMessage = "Trade request sent to ${connection.name}!") }
    }

    fun onProfileClick(connection: Connection) {
        _uiState.update { it.copy(toastMessage = "Viewing ${connection.name}'s profile...") }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}