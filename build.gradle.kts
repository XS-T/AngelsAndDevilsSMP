plugins {
	java
	id("org.jetbrains.kotlin.jvm") version "2.0.0"
	id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("io.papermc.paperweight.userdev") version "1.7.1"
	id("xyz.jpenilla.run-paper") version "2.3.0"
}

val paper_version: String by project
val project_version: String by project
val minecraft_version: String by project
val project_name: String by project
val project_package: String by project
val project_display_name: String by project
val project_plugin_class: String by project
val project_owners: String by project

group = project_package
version = project_version

repositories {
	google()
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://jitpack.io")
}

dependencies {
	// Paper
	paperweight.paperDevBundle(paper_version)
	compileOnly("io.papermc.paper:paper-api:$paper_version")

	// LuckPerms
	compileOnly("net.luckperms:api:5.4")

	// MySQL
	implementation("mysql:mysql-connector-java:8.0.28")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

	// Kotlin Coroutine for Bukkit
	implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.16.0")
	implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.16.0")

	// Dependency Injection
	implementation("com.google.inject:guice:7.0.0")
	implementation("dev.misfitlabs.kotlinguice4:kotlin-guice:3.0.0")

	// Database
	implementation("com.github.jasync-sql:jasync-mysql:2.1.7")

	// Command Framework
	implementation("cloud.commandframework:cloud-paper:1.8.4")
	implementation("cloud.commandframework:cloud-bukkit:1.8.4")
	implementation("cloud.commandframework:cloud-annotations:1.8.4")
	implementation("cloud.commandframework:cloud-minecraft-extras:1.8.4")
	implementation("cloud.commandframework:cloud-kotlin-coroutines:1.8.4")
	implementation("cloud.commandframework:cloud-kotlin-coroutines-annotations:1.8.4")
	implementation("cloud.commandframework:cloud-kotlin-extensions:1.8.4")
	annotationProcessor("cloud.commandframework:cloud-annotations:1.8.4")

	// Adventure
	implementation("net.kyori:adventure-platform-bukkit:4.2.0")
	implementation("net.kyori:adventure-extra-kotlin:4.12.0")
}

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
	withSourcesJar()
}


configurations {
	implementation.get().isCanBeResolved = true
}

tasks {
	assemble {
		dependsOn("reobfJar")
	}

	javadoc {
		options.encoding = Charsets.UTF_8.name()
	}
	processResources {
		filteringCharset = Charsets.UTF_8.name()
	}

	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}

	compileKotlin {
		kotlinOptions {
			jvmTarget = "21"
		}
	}

	shadowJar {
		archiveFileName.set("$project_name.jar")
		mergeServiceFiles()
		configurations = listOf(project.configurations.implementation.get())
	}

	build {
		dependsOn(shadowJar)
	}

	runServer {
		minecraftVersion(minecraft_version)
		jvmArgs("-Dcom.mojang.eula.agree=true")
	}
}


bukkit {
	name = project_display_name
	version = project_version
	authors = listOf("CrewCo Team", *project_owners.split(",").toTypedArray())
	main = "$project_package.$project_plugin_class"
	apiVersion = "1.20"
	//depend = listOf("")
}