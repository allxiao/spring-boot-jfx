# Spring Boot Application with JavaFX Starter

This a starter project that mixes the Spring Boot application with JavaFX application. It's a modified version of the
example project shared in [Tutorial: Reactive Spring Boot Part 3 – A JavaFX Spring Boot Application](
https://blog.jetbrains.com/idea/2019/11/tutorial-reactive-spring-boot-a-javafx-spring-boot-application/).

The main difference compared to the original tutorial is that the entrypoint is changed from JavaFX application to
Spring Boot application. In this way, you get unified Spring Boot experience before the JavaFX application is launched.
For example, the Java Platform Logging used in JavaFX is now handled by the Spring Boot logging system.

## Flow

1. Starts the Spring Boot application `SpringBootJfxApplication`. All Spring configurations are applied.
2. The `CommandLineRunner` - `AppLauncher` is called by the Spring Boot application.
3. `AppLauncher` launches a simple JavaFX application `StageEventEmitter`, which only emits a Spring event `StageReadyEvent`.
    * We do not prepare the primary stage here because we only have JavaFX context in this class. We won't be able to
        use Spring Boot feature for our primary stage.
4. `StageInitializer` handles the `StageReadyEvent` and initialize the JavaFX stage.
    * At this point, **you have both the Spring Boot and JavaFX features ready**. You can use Spring Boot configurations
        and IoC containers and then prepare the JavaFX stage accordingly.
