plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("kapt") version "2.1.20"
    id("io.ktor.plugin") version "2.2.3"
    id("com.google.protobuf") version "0.9.5"
}

group = "cl.emilym"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(platform("software.amazon.awssdk:bom:2.27.21"))
    implementation("software.amazon.awssdk:s3")

    implementation("com.google.protobuf:protobuf-java:4.31.0")

    implementation("info.picocli:picocli:4.7.7")
    kapt("info.picocli:picocli-codegen:4.7.7")
}

kapt {
    arguments {
        arg("project", "${project.group}/${project.name}")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Copy> {
    filesMatching("**/*.proto") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("cl.emilym.s3syncutil.MainKt")
}

ktor {
    fatJar {
        archiveFileName.set("s3syncutil.jar")
    }
}


sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
        java {
            srcDirs("src/main/java", "generated-sources/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.30.2"
    }
}