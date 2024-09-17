package com.diplomska.sportsaway.common.shared.errorhandling.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorBundle(
  @StringRes val errorTitle: Int,
  @StringRes val errorDescription: Int,
  val onButtonClick: () -> Unit
) : Parcelable