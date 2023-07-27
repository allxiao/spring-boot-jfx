package me.menxiao.study.jfx

import javafx.application.Application
import javafx.stage.Stage
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationEvent
import org.springframework.context.annotation.Profile
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
@Profile("!test")
class AppLauncher : CommandLineRunner, ApplicationContextAware {
    override fun run(vararg args: String) {
        Application.launch(StageEventEmitter::class.java, *args)
    }

    override fun setApplicationContext(ctx: ApplicationContext) {
        applicationContext = ctx
    }

    companion object {
        private var applicationContext: ApplicationContext? = null
    }

    /**
     * StageEventEmitter starts the JavaFX application, and forwards the primary stage to Spring Boot IoC world via event.
     */
    class StageEventEmitter : Application() {
        override fun start(primaryStage: Stage) {
            applicationContext!!.publishEvent(StageReadyEvent(primaryStage))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootJfxApplication>(*args)
}
