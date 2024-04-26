package fr.thegostsniperfr.arffornia_boostrap;

import fr.thegostsniperfr.arffornia_boostrap.updaters.JavaUpdater;
import fr.thegostsniperfr.arffornia_boostrap.updaters.LauncherUpdater;

import fr.thegostsniperfr.java_toolbox.distribution.ArchType;
import fr.thegostsniperfr.java_toolbox.distribution.OsType;
import fr.thegostsniperfr.java_toolbox.distribution.OsUtils;
import fr.thegostsniperfr.java_toolbox.file.DirUtils;
import fr.thegostsniperfr.java_toolbox.logger.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class ArfforniaBootstrap {
    private static final String GAME_DIR_NAME = ".Arffornia_V.5";
    private static final String LAUNCHER_JAR_NAME = "ArfforniaLauncher.jar";

    private static ArfforniaBootstrap instance;

    private final Path gameDirPath;
    private final OsType osType;
    private final ArchType archType;
    private final Path launcherJarPath;


    private Logger logger;

    public ArfforniaBootstrap() {
        /*  TODO
            Check & download Java 17
            Check & download Arffornia launcher (last version)
            Launch Arffornia launcher with java 17 version
         */
        instance = this;

        this.gameDirPath = OsUtils.getAppdataDirPath().resolve(GAME_DIR_NAME);
        this.osType = OsType.getCurrentOsType();
        this.archType = ArchType.getCurrentArchitecture();
        this.launcherJarPath = gameDirPath.resolve(ArfforniaBootstrap.getLauncherJarName());

        this.initLogger();

        DirUtils.createDirIfNotExist(this.gameDirPath);

        // Update java
        JavaUpdater javaUpdater = new JavaUpdater(this.gameDirPath, this.osType, this.archType, System.out::println);

        // Update launcher
        new LauncherUpdater(this.launcherJarPath);

        // Launch launcher
        this.launchLauncher(javaUpdater.getJavaHomePath());
    }

    private void launchLauncher(Path javaHome) {
        String javaBinPath = javaHome.resolve("bin").resolve("java").toString();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(javaBinPath, "-jar", this.launcherJarPath.toString());
            Process process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initLogger() {
        this.logger = new Logger(this.gameDirPath.resolve("launcherLogs.txt"), "Arffornia Bootstrap");
        this.logger.info("Launching Arffornia Bootstrap");
    }

    public static ArfforniaBootstrap getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getGameDirPath() {
        return gameDirPath;
    }

    public OsType getOsType() {
        return osType;
    }

    public ArchType getArchType() {
        return archType;
    }

    public static String getLauncherJarName() {
        return LAUNCHER_JAR_NAME;
    }

    public Path getLauncherJarPath() {
        return launcherJarPath;
    }
}
