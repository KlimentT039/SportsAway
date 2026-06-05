plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.kotlin.parcelize)
}

kotlin {
  jvmToolchain(17)

  androidTarget {
    compilations.all {
      compileTaskProvider.configure {
        compilerOptions {
          jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
          freeCompilerArgs.add(
            "-P=plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.diplomska.sportsaway.common.shared.parcelize.Parcelize"
          )
        }
      }
    }
  }

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { target ->
    target.binaries.framework {
      baseName = "Shared"
      isStatic = true
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.serialization.json)
      implementation(libs.kotlinx.datetime)

      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.serialization.kotlinx.json)
      implementation(libs.ktor.client.logging)

      api(libs.koin.core)

      api(libs.androidx.lifecycle.viewmodel)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
    }
    androidMain.dependencies {
      implementation(libs.ktor.client.okhttp)
      api(libs.gitlive.firebase.auth)
      api(libs.gitlive.firebase.firestore)
    }
    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
    }
  }
}

android {
  namespace = "com.diplomska.sportsaway.shared"
  compileSdk = libs.versions.android.shared.compile.sdk.get().toInt()
  defaultConfig {
    minSdk = libs.versions.android.shared.min.sdk.get().toInt()
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}
