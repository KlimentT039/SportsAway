package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.feature.events.view.model.TicketFilter
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import kotlin.random.Random

@Parcelize
data class Ticket(
  @StringRes val title: Int = 0,
  val remainingTickets: Int = 0,
  val price: Int = 0,
  val section: String = "101",
  val row: Int? = null,
  val ticketType: TicketFilter = TicketFilter.GENERAL,
  val matchId: Int = -1
) : Parcelable


fun initRandomGeneralTickets(matchId: Int, isItShortSide: Boolean) = Ticket(
  title = if (isItShortSide) R.string.shortside_ticket else R.string.longside_ticket,
  remainingTickets = Random.nextInt(from = 10, until = 20),
  section = Random.nextInt(100, 120).toString(),
  row = Random.nextInt(0, 19),
  price = Random.nextInt(30, 60),
  ticketType = TicketFilter.GENERAL,
  matchId = matchId
)


fun initRandomVipTickets(matchId: Int) = Ticket(
  title = R.string.vip_ticket,
  remainingTickets = Random.nextInt(from = 5, until = 15),
  price = Random.nextInt(80, 100),
  section = "VIP",
  row = null,
  ticketType = TicketFilter.VIP,
  matchId = matchId
)
