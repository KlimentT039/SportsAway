package com.diplomska.sportsaway.common.shared.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.diplomska.sportsaway.R
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import kotlin.random.Random

@Parcelize
data class Ticket(
  @StringRes val title: Int = 0,
  val remainingTickets: Int = 0,
  val price: Int = 0,
  val ticketType: TicketType = TicketType.GENERAL,
  val matchId: Int
) : Parcelable


fun initRandomGeneralTickets(matchId: Int, isItShortSide: Boolean) = Ticket(
  title = if (isItShortSide) R.string.shortside_ticket else R.string.longside_ticket,
  remainingTickets = Random.nextInt(from = 10, until = 20),
  price = Random.nextInt(30, 60),
  ticketType = TicketType.GENERAL,
  matchId = matchId
)


fun initRandomVipTickets(matchId: Int) = Ticket(
  title = R.string.vip_ticket,
  remainingTickets = Random.nextInt(from = 5, until = 15),
  price = Random.nextInt(80, 100),
  ticketType = TicketType.VIP,
  matchId = matchId
)

enum class TicketType {
  VIP, GENERAL
}