plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace ="info.upump.jymlight"
    compileSdk = 34

    defaultConfig {
        applicationId ="info.upump.jymlight"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName ="1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.7"}
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
        jvmTarget ="1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation (project(":database"))
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation (platform("androidx.compose:compose-bom:2022.10.00"))
    implementation ("androidx.compose.ui:ui:1.5.4")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.4")
    implementation ("androidx.compose.ui:ui-tooling:1.5.4")
    implementation ("androidx.compose.ui:ui-graphics:1.5.4")
    // compose
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")
    androidTestImplementation( platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementation("androidx.compose.material3:material3-window-size-class")
    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.8.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.material:material:1.5.4")

    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    // To use Kotlin annotation processing tool (kapt)
    // kapt("androidx.room:room-compiler:2.5.2")

    // Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.30.1") // Pager
    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.1") // Pager Indicators
    //accompanist-permissions
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")

    // constraint
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
}