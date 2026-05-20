package com.diplomska.sportsaway.common.shared.parcelize

expect interface Parcelable

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
expect annotation class Parcelize()

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.BINARY)
expect annotation class IgnoredOnParcel()

@OptIn(ExperimentalMultiplatform::class)
@OptionalExpectation
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
expect annotation class RawValue()
