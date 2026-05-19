package com.diplomska.sportsaway.shared

interface Platform {
  val name: String
}

expect fun currentPlatform(): Platform
