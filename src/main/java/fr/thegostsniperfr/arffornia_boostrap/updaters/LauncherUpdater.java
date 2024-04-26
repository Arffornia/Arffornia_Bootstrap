package fr.thegostsniperfr.arffornia_boostrap.updaters;

import fr.thegostsniperfr.arffornia_boostrap.ArfforniaBootstrap;
import fr.thegostsniperfr.arffornia_boostrap.ProgressSteps;
import fr.thegostsniperfr.arffornia_boostrap.api.ArfforniaAPI;
import fr.thegostsniperfr.java_toolbox.hash.HashType;
import fr.thegostsniperfr.java_toolbox.hash.HashUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class LauncherUpdater {
    private final Path launcherJarPath;
    private final ProgressSteps progressSteps;

    public LauncherUpdater(Path launcherJarPath, ProgressSteps progressSteps) {
        this.launcherJarPath = launcherJarPath;
        this.progressSteps = progressSteps;
        this.update();
    }

    public void update() {
        this.progressSteps.setProgressStep(ProgressSteps.ProgressStep.FETCHING_LAUNCHER);

        try {
            // Check is launcher.jar exist
            if(!Files.exists(launcherJarPath)
                   || HashUtils.getHashFromFilePath(this.launcherJarPath, HashType.SHA1).equals(ArfforniaAPI.getLauncherHash())
            )
            {
                this.progressSteps.setProgressStep(ProgressSteps.ProgressStep.DOWNLOADING_LAUNCHER);
                Files.copy(ArfforniaAPI.getLauncherJarURL().openStream(), this.launcherJarPath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            ArfforniaBootstrap.getInstance().getLogger().err(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
