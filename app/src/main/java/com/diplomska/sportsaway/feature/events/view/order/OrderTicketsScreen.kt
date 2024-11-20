import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.shared.model.Match
import com.diplomska.sportsaway.common.shared.model.Ticket
import com.diplomska.sportsaway.common.shared.model.getMatchDescription
import com.diplomska.sportsaway.common.shared.model.getMatchTitle
import com.diplomska.sportsaway.common.shared.model.initRandomVipTickets
import com.diplomska.sportsaway.common.style.compose.components.AppBar
import com.diplomska.sportsaway.common.style.compose.components.ListDivider
import com.diplomska.sportsaway.common.style.compose.components.Scaffold
import com.diplomska.sportsaway.common.style.compose.layouts.OverlayLoader
import com.diplomska.sportsaway.common.style.compose.theme.backgroundDefault
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import com.diplomska.sportsaway.common.style.compose.theme.topBarTextColor
import com.diplomska.sportsaway.common.style.compose.typography
import com.diplomska.sportsaway.feature.events.view.model.BillingAddress
import com.diplomska.sportsaway.feature.events.view.model.SavedCard
import com.diplomska.sportsaway.feature.events.view.order.OrderTicketsState
import com.diplomska.sportsaway.feature.events.view.order.OrderTicketsViewModel

@Composable
fun OrderTicketsScreen(viewModel: OrderTicketsViewModel, onBackClick: () -> Unit) {
  val uiState = viewModel.state.collectAsStateWithLifecycle()
  val modifier = Modifier
  Scaffold.WithBottomBarOnly(
    bottomBar = {
      SlideToPlaceOrderButton(modifier)
    },
    content = { padding ->
      when (uiState.value) {
        is OrderTicketsState.Loading -> OverlayLoader()
        is OrderTicketsState.OrderTicketsData -> OrderTicketsContent(
          data = uiState.value as OrderTicketsState.OrderTicketsData,
          modifier = modifier.padding(padding),
          onAddAddressClick = viewModel::onAddBillingAddressClicked,
          onAddCardClick = viewModel::onAddCardClicked,
          onEditNumTickets = viewModel::onEditNumOfTickets,
          onBackClick = onBackClick
        )
      }
    })
}

@Composable
private fun OrderTicketsContent(
  data: OrderTicketsState.OrderTicketsData,
  onBackClick: () -> Unit,
  modifier: Modifier,
  onAddCardClick: () -> Unit,
  onAddAddressClick: () -> Unit,
  onEditNumTickets: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    BannerContent(modifier = modifier, match = data.match, onBackClick = onBackClick)

    Spacer(modifier = Modifier.height(8.dp))

    OrderDetailsSection(
      modifier = modifier,
      orderTicketsData = data,
      onAddCardClick = onAddCardClick,
      onAddAddressClick = onAddAddressClick,
      onEditNumTickets = onEditNumTickets
    )
  }
}

@Composable
private fun OrderDetailsSection(
  modifier: Modifier,
  orderTicketsData: OrderTicketsState.OrderTicketsData,
  onAddCardClick: () -> Unit,
  onAddAddressClick: () -> Unit,
  onEditNumTickets: () -> Unit
) {
  val ticket = orderTicketsData.ticket
  Column(modifier = modifier.padding(16.dp)) {

    SeatingInfo(modifier = modifier, ticket = ticket)

    Spacer(modifier = Modifier.height(16.dp))

    PaymentInfo(
      modifier = modifier,
      cardData = orderTicketsData.cardData,
      billingAddress = orderTicketsData.billingAddress,
      onAddCardClick = onAddCardClick,
      onAddAddressClick = onAddAddressClick,
      onEditNumTickets = onEditNumTickets,
      numTickets = orderTicketsData.numberOfTickets
    )

    Spacer(modifier = modifier.height(8.dp))

    PriceInfo(
      modifier,
      price = ticket.price,
      numberOfTickets = orderTicketsData.numberOfTickets,
      total = orderTicketsData.total
    )
  }
}

@Composable
private fun BannerContent(modifier: Modifier, match: Match, onBackClick: () -> Unit) {
  val screenHeight = LocalConfiguration.current.screenHeightDp.dp
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(backgroundDefault)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight / 2)
        .clip(RoundedCornerShape(16.dp))
    ) {
      if (!match.venueImage.isNullOrEmpty()) {
        AsyncImage(
          model = match.venueImage,
          contentDescription = null,
          modifier = Modifier.matchParentSize(),
          contentScale = ContentScale.Crop
        )
      } else {
        Image(
          painter = painterResource(id = R.drawable.stadium_pic),
          contentDescription = "Event Banner",
          modifier = Modifier.matchParentSize(),
          contentScale = ContentScale.Crop
        )
      }

      Row(modifier = modifier.align(Alignment.BottomStart)) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.padding(start = 4.dp)
        ) {
          Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
          )
        }

        Column {
          Text(
            text = match.getMatchTitle(),
            style = typography.xsRegular.copy(
              fontWeight = FontWeight.Bold,
              color = Color.White
            ),
          )
          match.getMatchDescription()?.let {
            Text(
              text = it,
              style = typography.sRegularPrimary.copy(color = Color.White),
            )
          }
        }
      }
    }
  }
}

@Composable
private fun SeatingInfo(modifier: Modifier, ticket: Ticket) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Text(
        text = "Section ${ticket.section}",
        style = typography.mRegular.copy(fontWeight = FontWeight.Bold)
      )
      ticket.row?.let {
        Text(
          text = "Row ${ticket.row}",
          style = typography.sRegularPrimary.copy(color = Color.Gray)
        )
      }
    }
    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
      Text(
        text = "$${ticket.price}",
        style = typography.mRegular.copy(fontWeight = FontWeight.Bold)
      )
    }
  }
}

@Composable
private fun PaymentInfo(
  modifier: Modifier,
  cardData: SavedCard?,
  billingAddress: BillingAddress?,
  numTickets: Int,
  onAddCardClick: () -> Unit,
  onAddAddressClick: () -> Unit,
  onEditNumTickets: () -> Unit
) {
  PaymentRow(
    modifier = modifier,
    title = stringResource(R.string.credit_card),
    actionLabel = if (cardData == null) stringResource(R.string.add_card) else
      stringResource(R.string.update_card),
    onActionClick = onAddCardClick
  )

  Spacer(modifier = modifier.height(8.dp))

  PaymentRow(
    modifier = modifier,
    title = stringResource(R.string.billing_address),
    actionLabel = if (billingAddress == null) stringResource(R.string.add_address) else
      stringResource(R.string.generic_update),
    onActionClick = onAddAddressClick
  )

  PaymentRow(
    modifier = modifier,
    title = stringResource(R.string.num_tickets, numTickets),
    actionLabel = stringResource(R.string.generic_edit),
    onActionClick = onEditNumTickets
  )
}

@Composable
private fun PriceInfo(modifier: Modifier, price: Int, numberOfTickets: Int, total: Int) {
  Column(modifier = modifier.fillMaxWidth()) {
    PriceRow(
      modifier = modifier,
      label = "Price (USD)",
      value = "$${price} x $numberOfTickets",
    )

    ListDivider()

    PriceRow(
      label = "Total",
      value = total.toString(),
      isBold = true,
      modifier = modifier
    )
  }
}

@Composable
private fun PaymentRow(
  modifier: Modifier,
  title: String,
  actionLabel: String,
  onActionClick: () -> Unit
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Text(text = title, style = typography.mRegular)
    }
    TextButton(onClick = onActionClick) {
      Text(text = actionLabel.uppercase(), style = typography.xsRegular)
    }
  }
}

@Composable
private fun PriceRow(modifier: Modifier, label: String, value: String, isBold: Boolean = false) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label,
      style = if (isBold) typography.mRegular.copy(fontWeight = FontWeight.Bold)
      else typography.mRegular
    )
    Text(
      text = value,
      style = if (isBold) typography.mRegular.copy(fontWeight = FontWeight.Bold)
      else typography.mRegular
    )
  }
}

@Composable
private fun SlideToPlaceOrderButton(modifier: Modifier) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp)
      .background(mainColor, RoundedCornerShape(50.dp))
      .clickable { /* Handle slide to place order */ },
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = "Slide to place order",
      style = typography.mLarge.copy(
        color = topBarTextColor,
        fontWeight = FontWeight.Bold
      ),
      modifier = Modifier.padding(vertical = 12.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun OrderTicketsScreenPreview() {
  MaterialTheme {
    OrderTicketsContent(
      modifier = Modifier,
      data = OrderTicketsState.OrderTicketsData(
        Match(),
        initRandomVipTickets(matchId = 0),
        total = 88
      ),
      onAddAddressClick = {},
      onAddCardClick = {},
      onEditNumTickets = {},
      onBackClick = {}
    )
  }
}