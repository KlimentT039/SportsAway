package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.diplomska.sportsaway.R
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Ticket(
  @StringRes val title: Int = 0,
  val remainingTickets: Int = 0,
  val price: Int = 0
): Parcelable


fun Ticket.initRandomGeneralTickets() = Ticket(
  title = R.string.general_ticket,
  remainingTickets = Random.nextInt(from = 10, until = 20),
  price = Random.nextInt(from = 30, until = 40)
)


fun Ticket.initRandomVipTickets() = Ticket(
  title = R.string.general_ticket,
  remainingTickets = Random.nextInt(from = 5, until = 15),
  price = Random.nextInt(from = 120, until = 150)
)