plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("kotlin-parcelize")
  id("com.google.gms.google-services")
  kotlin("kapt")
}

android {
  namespace = "com.diplomska.sportsaway"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.diplomska.sportsaway"
    minSdk = 29
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.0"
  }
  buildFeatures {
    compose = true
  }
}

dependencies {

  implementation("io.insert-koin:koin-core:3.4.3")
  implementation("io.insert-koin:koin-android:3.4.3")
  implementation("io.insert-koin:koin-test:3.4.3")
  implementation("io.insert-koin:koin-test-junit4:3.4.3")
  implementation("io.insert-koin:koin-androidx-compose:3.4.3")
  implementation("androidx.navigation:navigation-compose:2.7.5")
  implementation("com.squareup.okhttp3:okhttp:4.11.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
  implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.10.0")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
  implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
  implementation("com.google.firebase:firebase-analytics")
  implementation("com.google.firebase:firebase-firestore")
  implementation("com.google.firebase:firebase-auth")
  implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0")

  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.10.0")
  implementation("io.coil-kt:coil:2.6.0")
  implementation("io.coil-kt:coil-svg:2.0.0")
  implementation("io.coil-kt:coil-compose:2.7.0")

  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
  implementation(composeBom)
  androidTestImplementation(composeBom)
  implementation("androidx.activity:activity-compose:1.9.3")
  implementation("androidx.compose.material3:material3")
  implementation("androidx.compose.material:material")
  implementation("androidx.compose.foundation:foundation")
  implementation("androidx.compose.ui:ui")

  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.ui:ui-tooling")

  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")

}