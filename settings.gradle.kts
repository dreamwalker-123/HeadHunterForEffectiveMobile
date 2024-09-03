pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "HeadHunterCompose"
include(":app")
include(":core:network")
include(":core:database")
include(":core:model")
include(":feature:entry")
include(":core:designSystem")
include(":feature:mainOrSearch")
include(":core:data")
include(":feature:vacancy")
include(":feature:favorite")
