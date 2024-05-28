package com.diplomska.sportsaway.dashboard.home.components.tiles

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.diplomska.sportsaway.dashboard.home.components.tiles.model.SportsTile
import com.diplomska.sportsaway.style.compose.components.TileWithIconAndText

@Composable
fun SportTilesContent(list: List<SportsTile>) {
  LazyRow{
    itemsIndexed(list){index, item ->  
      TileWithIconAndText(iconDrawable = item.icon, text = stringResource(id = item.title))
    }
  }
}