package com.diplomska.sportsaway.shared.utils


fun <T> chunkedList(list: List<T>, size: Int): List<List<T>> {
  val chunkedList = mutableListOf<List<T>>()
  var index = 0
  while (index < list.size) {
    val end = (index + size).coerceAtMost(list.size)
    chunkedList.add(list.subList(index, end))
    index += size
  }
  return chunkedList
}
