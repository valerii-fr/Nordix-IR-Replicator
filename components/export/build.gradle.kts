plugins {
    id("dev.nordix.irbridge.build_logic.common_ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":components:remotes"))
        }
    }
}

android {
    namespace = "dev.nordix.irbridge.export"
}