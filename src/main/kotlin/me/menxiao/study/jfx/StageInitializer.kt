package me.menxiao.study.jfx

import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

/**
 * StageInitializer mixes the Spring Boot capability and the JavaFX primary Stage together. You can use Spring
 * dependency injection, and then initialize the primary Stage with the help of Spring beans and configurations.
 */
@Component
class StageInitializer : ApplicationListener<StageReadyEvent> {
    override fun onApplicationEvent(event: StageReadyEvent) {
        val primaryStage = event.stage

        val javaVersion = System.getProperty("java.version")
        val javafxVersion = System.getProperty("javafx.version")
        val label = Label("Hello, JavaFX $javafxVersion, running on Java $javaVersion")
        val scene = Scene(StackPane(label), 640.0, 480.0)

        primaryStage.scene = scene
        primaryStage.title = "Hello"
        primaryStage.show()
    }
}
