plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.26.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("org.example.localcalc")
    mainClass.set("org.example.localcalc.HelloApplication")
}

javafx {
    version = "17.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.controlsfx:controlsfx:11.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
tasks.register<Exec>("packageExe") {
    dependsOn("clean", "build")
    group = "packaging"
    description = "Собирает .exe для Windows (запускать на Windows!)"

    val jarName = "${project.name}-${project.version}.jar"

    commandLine(
        "jpackage",
        "--type", "exe",
        "--input", "build/libs",
        "--dest", "dist",
        "--name", "LocalCalc",
        "--main-jar", jarName,
        "--main-class", "org.example.localcalc.HelloApplication",

        "--icon", "src/main/resources/icon.ico",

        "--win-shortcut",
        "--win-menu"
    )
}