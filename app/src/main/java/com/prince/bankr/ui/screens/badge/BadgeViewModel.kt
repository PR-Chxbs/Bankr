package com.prince.bankr.ui.screens.badges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.bankr.data.local.entities.Badge
import com.prince.bankr.data.repository.BadgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BadgeViewModel @Inject constructor(
    private val badgeRepo: BadgeRepository
) : ViewModel() {

    private val userId = 1 // Replace with actual session logic

    private val _badges = MutableStateFlow<List<Badge>>(emptyList())
    val badges: StateFlow<List<Badge>> = _badges.asStateFlow()

    init {
        loadBadges()
    }

    private fun loadBadges() {
        viewModelScope.launch {
            badgeRepo.getAllBadgesForUser(userId).collect {
                _badges.value = it
            }
        }
    }
}
