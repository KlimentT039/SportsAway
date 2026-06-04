package com.diplomska.sportsaway.feature.favourite.view.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.data.events_data.model.News

@Composable
fun NewsItem(news: News) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    AsyncImage(
      model = news.link,
      contentDescription = stringResource(R.string.generic_news_image),
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(72.dp)
        .clip(RoundedCornerShape(8.dp))
    )

    Spacer(modifier = Modifier.width(12.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = news.title,
        style = typography.mLarge,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = news.date,
        style = typography.mRegular,
        color = Color.Gray
      )
    }
  }
}