package fr.thegostsniperfr.arffornia_boostrap.updaters;

import fr.thegostsniperfr.arffornia_boostrap.ArfforniaBootstrap;
import fr.thegostsniperfr.arffornia_boostrap.api.ArfforniaAPI;
import fr.thegostsniperfr.java_toolbox.hash.HashType;
import fr.thegostsniperfr.java_toolbox.hash.HashUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class LauncherUpdater {
    private final Path launcherJarPath;

    public LauncherUpdater(Path launcherJarPath) {
        this.launcherJarPath = launcherJarPath;
        this.update();
    }

    public void update() {

        try {
            // Check is launcher.jar exist
            if(!Files.exists(launcherJarPath)
                   || HashUtils.getHashFromFilePath(this.launcherJarPath, HashType.SHA1).equals(ArfforniaAPI.getLauncherHash())
            )
            {
                Files.copy(ArfforniaAPI.getLauncherJarURL().openStream(), this.launcherJarPath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            ArfforniaBootstrap.getInstance().getLogger().err(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
