dependencies {
    implementation(project(":common"))

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:_")

}

tasks.jar { enabled = true }
tasks.bootJar { enabled = false }