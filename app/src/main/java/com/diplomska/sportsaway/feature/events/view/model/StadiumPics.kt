package com.diplomska.sportsaway.feature.events.view.model

import com.diplomska.sportsaway.data.events_data.model.Venue

data class StadiumPics(
  val imagePrimary: String?,
  val imageSecondary: String?,
  val imageTertiary: String?,
  val imageQuaternary: String?
)

fun Venue.toStadiumPics() = StadiumPics(
  imagePrimary = strFanart1,
  imageSecondary = strFanart2,
  imageTertiary = strFanart3,
  imageQuaternary = strFanart4
)

fun StadiumPics.getPic() =
  imageSecondary ?:
  imagePrimary ?:
  imageTertiary ?:
  imageQuaternary
