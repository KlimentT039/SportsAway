package com.diplomska.sportsaway.feature.favourite.view.addteams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplomska.sportsaway.common.shared.errorhandling.fold
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.feature.favourite.domain.TeamsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class AddFavouriteTeamsViewModel(private val teamsUseCase: TeamsUseCase) : ViewModel() {

  private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
  val viewState = _viewState.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery = _searchQuery.asStateFlow()

  private val _event = MutableSharedFlow<FavouriteEvent>()
  val event = _event.asSharedFlow()

  private var initialList: List<Team> = emptyList()

  init {
    getTeams()
  }

  fun onSearchValueChanged(searchQuery: String) {
    runWithViewStateData { viewState ->
      _searchQuery.update { searchQuery }
      if (searchQuery.isEmpty()) {
        _viewState.update { viewState.copy(teams = initialList) }
        return
      }
      val updateList = viewState.teams.filter { it.name.contains(searchQuery, ignoreCase = true) }
      _viewState.update { viewState.copy(teams = updateList) }
    }
  }

  fun onDoneClicked() = viewModelScope.launch {
    runWithViewStateData {
      _viewState.update { ViewState.Loading }

      teamsUseCase.updateListOfFavourites(it.selectedTeams).fold(
        onFailure = { _viewState.update { ViewState.Error } },
        onSuccess = {
          _event.emit(FavouriteEvent.Done)
        }
      )
    }
  }

  fun onFavouriteClicked(team: Team) {
    runWithViewStateData { viewState ->
      val updatedTeam = team.copy(isFavourite = !team.isFavourite)

      val updatedSelectedTeams = if (updatedTeam.isFavourite) {
        viewState.selectedTeams + updatedTeam.id
      } else {
        viewState.selectedTeams - updatedTeam.id
      }

      val updatedTeams = viewState.teams.map { existingTeam ->
        if (existingTeam.id == team.id) updatedTeam else existingTeam
      }

      _viewState.update {
        viewState.copy(
          teams = updatedTeams,
          selectedTeams = updatedSelectedTeams
        )
      }
    }
  }

  private fun getTeams() = viewModelScope.launch {
    teamsUseCase().fold(
      onFailure = { _viewState.update { ViewState.Error } },
      onSuccess = { teams ->
        initialList = teams
        _viewState.update {
          ViewState.TeamsData(teams = teams, selectedTeams = updateSelectedTeams(teams))
        }
      }
    )
  }

  private fun updateSelectedTeams(teams: List<Team>): List<Int> {
    return teams.filter { it.isFavourite }.map { it.id }
  }

  private inline fun runWithViewStateData(block: (ViewState.TeamsData) -> Unit) {
    val viewStateData = _viewState.value as? ViewState.TeamsData ?: return
    block(viewStateData)
  }

}

sealed interface ViewState {

  data object Loading : ViewState

  data class TeamsData(
    val teams: List<Team> = emptyList(),
    val selectedTeams: List<Int> = emptyList()
  ) : ViewState

  data object Error : ViewState
}

sealed interface FavouriteEvent {
  data object Done : FavouriteEvent
}