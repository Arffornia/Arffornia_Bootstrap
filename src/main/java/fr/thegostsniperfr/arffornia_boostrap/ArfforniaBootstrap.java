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
    private final ProgressSteps progressSteps;

    private Logger logger;

    public ArfforniaBootstrap() {
        instance = this;

        this.gameDirPath = OsUtils.getAppdataDirPath().resolve(GAME_DIR_NAME);
        this.osType = OsType.getCurrentOsType();
        this.archType = ArchType.getCurrentArchitecture();
        this.launcherJarPath = gameDirPath.resolve(ArfforniaBootstrap.getLauncherJarName());

        this.initLogger();
        this.progressSteps = new ProgressSteps(this.logger);

        DirUtils.createDirIfNotExist(this.gameDirPath);

        // Update java
        JavaUpdater javaUpdater = new JavaUpdater(this.gameDirPath, this.osType, this.archType, progressSteps::onJavaStep);

        // Update launcher
        new LauncherUpdater(this.launcherJarPath, this.progressSteps);

        // Launch launcher
        this.launchLauncher(javaUpdater.getJavaHomePath());
    }

    public void progressStep(String step) {
        System.out.println("TEST step: " + step);
    }

    private void launchLauncher(Path javaHome) {
        this.progressSteps.setProgressStep(ProgressSteps.ProgressStep.LAUNCHING_LAUNCHER);
        String javaBinPath = javaHome.resolve("bin").resolve("java").toString();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(javaBinPath, "-jar", this.launcherJarPath.toString());
            processBuilder.start();
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
