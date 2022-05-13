plugins {
    kotlin("multiplatform") version "1.7.20-dev-853"
    application
}

group = "org.archguard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
}

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2/")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-jupyter-api-gradle-plugin:0.11.0-87")
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains:markdown:0.3.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            apply(plugin = "org.jetbrains.kotlin.jupyter.api")
            dependencies {
                implementation("io.ktor:ktor-server-core:2.0.1")
                implementation("io.ktor:ktor-server-netty:2.0.1")
                implementation("io.ktor:ktor-server-websockets:2.0.1")

                implementation("ch.qos.logback:logback-classic:1.2.11")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

                implementation("org.jetbrains.kotlinx:kotlin-jupyter-api:0.11.0-87")
                implementation("org.jetbrains.kotlinx:kotlin-jupyter-kernel:0.11.0-87")

                implementation("io.ktor:ktor-server-netty:1.6.7")
                implementation("io.ktor:ktor-html-builder:1.6.7")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:5.1.0")
            }

        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.290-kotlin-1.6.10")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:17.0.2-pre.290-kotlin-1.6.10")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("org.archguard.application.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}