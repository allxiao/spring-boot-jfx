package me.menxiao.study.jfx

import javafx.application.Application
import javafx.application.Platform
import javafx.stage.Stage
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationEvent
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

@SpringBootApplication
class SpringBootJfxApplication

class StageReadyEvent(stage: Stage) : ApplicationEvent(stage) {
    val stage get() = source as Stage
}

/**
 * AppLauncher is a single CommandLineRunner which just spawns the JavaFX entry application.
 */
@Component
@EnableConfigurationProperties(AppLauncher.AppLauncherConfig::class)
class AppLauncher(private val config: AppLauncherConfig) : CommandLineRunner, ApplicationContextAware {
    override fun run(vararg args: String) {
        if (config.enabled) {
            Application.launch(AppEventEmitter::class.java, *args)
        }
    }

    override fun setApplicationContext(ctx: ApplicationContext) {
        applicationContext = ctx as ConfigurableApplicationContext
    }

    companion object {
        private var applicationContext: ConfigurableApplicationContext? = null
    }

    /**
     * AppEventEmitter starts the JavaFX application, and forwards the primary stage to Spring Boot IoC world via event.
     */
    class AppEventEmitter : Application() {
        override fun start(primaryStage: Stage) {
            applicationContext!!.publishEvent(StageReadyEvent(primaryStage))
        }

        override fun stop() {
            Platform.exit()
        }
    }

    /**
     * Config to enable / disable the JavaFX launch, which may be useful in tests.
     */
    @ConfigurationProperties(prefix = "app-launcher")
    class AppLauncherConfig {
        var enabled: Boolean = true
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootJfxApplication>(*args)
}
