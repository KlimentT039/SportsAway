import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.res.colorResource
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Team
import com.diplomska.sportsaway.common.shared.utils.GetImage

@Composable
fun FavouriteTeamCard(
  team: Team,
  modifier: Modifier = Modifier,
  onFavoriteClick: (Team) -> Unit
) {
  Card(
    modifier = modifier
      .fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(containerColor = colorResource(R.color.cardColor))
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {
      GetImage(team.crest, pictureSize = 48)
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(start = 8.dp)
      ) {
        Text(
          text = team.name,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          color = Color.Black
        )
        team.country?.let {
          Text(
            text = it,
            fontSize = 14.sp,
            color = Color.Gray
          )
        }
      }
      Icon(
        imageVector = if (team.isFavourite) Icons.Filled.Star else Icons.Outlined.Star,
        contentDescription = if (team.isFavourite) "Unmark as Favorite" else "Mark as Favorite",
        tint = if (team.isFavourite) Color(0xFFFFD700) else Color.Gray, // Gold color when selected
        modifier = Modifier
          .size(24.dp)
          .clickable {
            onFavoriteClick(team)
          }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FootballClubCardPreview() {
  FavouriteTeamCard(
    Team(name = "Barcelona", country = "Spain"),
    onFavoriteClick = {},
  )
}
