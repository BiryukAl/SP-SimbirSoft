@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.simbirsoft"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.simbirsoft"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kotlin {
        jvmToolchain(8) // fixed bug :app:kspDebugKotlin or :app:kaptDebugKotlin
    }

    viewBinding {
        enable = true
    }

}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.bundles.screen)
    ksp(libs.lifecycle.compiler)
    implementation(libs.bundles.db)
    ksp(libs.room.compiler)
    implementation(libs.bundles.tests)

}