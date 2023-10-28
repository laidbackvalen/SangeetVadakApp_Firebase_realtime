plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.firebasesetup"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.firebasesetup"
        minSdk = 24
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //LOTTIEFILES
    implementation("com.airbnb.android:lottie:6.0.1")

    //FIREBASE
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics:21.3.0")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    
    //VIEWPAGER 2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

   //ROUNDED IMAGE VIEW
    implementation("com.makeramen:roundedimageview:2.3.0")

    //SLIDER IMAGE used in play music main activity
    implementation ("com.github.smarteist:autoimageslider:1.4.0")
    //also add  """  jcenter() """" in dependencyResolutionManagement{ repositories {
// inside settings.gradle (project settings) file
}
