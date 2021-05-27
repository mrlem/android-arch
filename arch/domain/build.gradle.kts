import arch.Deps
import arch.ext.api

plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    // external
    api(kotlin("stdlib"))
    api(Deps.Rx.all)
}
