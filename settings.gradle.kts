pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "SportsAway"
include(":app")
include(":shared")
// :exercises is a standalone Jackson-based JVM module, excluded from the KMP migration.
// Re-add `include(":exercises")` if it becomes shippable again.
