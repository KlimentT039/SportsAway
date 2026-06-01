package com.diplomska.sportsaway.feature.favourite.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.feature.favourite.domain.FetchLatestTeamInfoUseCase
import com.diplomska.sportsaway.feature.favourite.model.LatestTeamInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface LatestNewsViewState {
  data object Loading : LatestNewsViewState
  data object Error : LatestNewsViewState
  data class Content(val teamInfo: LatestTeamInfo) : LatestNewsViewState
}

class LatestNewsViewModel(private val fetchLatestTeamInfoUseCase: FetchLatestTeamInfoUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<LatestNewsViewState>(LatestNewsViewState.Loading)
  val viewState = _viewState.asStateFlow()

  fun initData(id: Int)  = viewModelScope.launch{
    fetchLatestTeamInfoUseCase(id).fold(
      onFailure = { _viewState.update { LatestNewsViewState.Error } },
      onSuccess = { latestTeamInfo ->
        _viewState.update { LatestNewsViewState.Content(teamInfo = latestTeamInfo) }
      }
    )
  }
}