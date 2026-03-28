import java.lang.module.ModuleFinder.compose

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.composeCompiler) // <-- needed for Compose


}

kotlin {
    jvmToolchain(21)
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {

            implementation(project(":common"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kermit)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.kermit)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        androidMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
            implementation("com.google.android.gms:play-services-location:21.0.1")

        }
        iosMain.dependencies {
        }
    }
}


android {
    namespace = "com.tech.cursor.feature"
    compileSdk = 36
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = 21
    }
}
