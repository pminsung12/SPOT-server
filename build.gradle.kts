plugins {
    id("java")
    id("io.sentry.jvm.gradle")
    id("com.diffplug.spotless")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

allprojects {
    group = "org.depromeet.spot"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "io.sentry.jvm.gradle")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // subproject 모두에 필요한 의존성을 관리합니다.
    dependencies {
        // lombok
        compileOnly("org.projectlombok:lombok:_")
        annotationProcessor("org.projectlombok:lombok:_")
        testCompileOnly("org.projectlombok:lombok:_")
        testAnnotationProcessor("org.projectlombok:lombok:_")

        // configuration processor
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:_")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test:_")
        testImplementation(platform("org.junit:junit-bom:_"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        // secret manager
        // implementation("io.awspring.cloud:spring-cloud-starter-aws-secrets-manager-config:_")
    }

    // 코드 포맷터 spotless
    spotless {
        java {
            // Google Java 포맷 적용
            googleJavaFormat().aosp()
            // 아래 순서로 import문 정렬
            importOrder("java", "javax", "jakarta", "org", "com")
            // 사용하지 않는 import 제거
            removeUnusedImports()
            // 각 라인 끝에 있는 공백을 제거
            trimTrailingWhitespace()
            // 파일 끝에 새로운 라인 추가
            endWithNewline()
        }
    }

    // git commit시 자동으로 spotless 적용되도록 설정
    // 최초 적용시 && script 변경시 ./gradlew compileJava 한번 실행해주세요
    tasks.register<Copy>("updateGitHooks") {
        from(file("${rootProject.rootDir}/.githooks/pre-commit"))
        into(file("${rootProject.rootDir}/.git/hooks"))
    }

    tasks.register<Exec>("makeGitHooksExecutable") {
        if (System.getProperty("os.name").contains("Windows")) {
            commandLine("attrib", "+x", "${rootProject.rootDir}/.git/hooks/pre-commit")
        } else {
            commandLine("chmod", "+x", "${rootProject.rootDir}/.git/hooks/pre-commit")
        }
        dependsOn("updateGitHooks")
    }

    tasks.named<JavaCompile>("compileJava") {
        dependsOn("makeGitHooksExecutable")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

// root 모듈은 실행 파일이 없으므로 bootJar를 생성하지 않는다.
tasks.bootJar { enabled = false }
tasks.jar { enabled = true }