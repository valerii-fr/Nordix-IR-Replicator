plugins {
    id("dev.nordix.irbridge.build_logic.common_ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":components:remotes"))
            implementation(project(":components:ir"))
            implementation(project(":components:ble"))
            implementation(project(":components:export"))
        }
    }
}

android {
    namespace = "dev.nordix.irbridge.feature.remotes"
}
