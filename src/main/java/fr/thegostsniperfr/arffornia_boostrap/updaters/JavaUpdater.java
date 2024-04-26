package fr.thegostsniperfr.arffornia_boostrap.updaters;

import fr.thegostsniperfr.java_downloader.Callback;
import fr.thegostsniperfr.java_downloader.JavaDownloader;
import fr.thegostsniperfr.java_downloader.JavaVersionInfo;
import fr.thegostsniperfr.java_toolbox.distribution.ArchType;
import fr.thegostsniperfr.java_downloader.distribution.JavaType;
import fr.thegostsniperfr.java_toolbox.distribution.OsType;

import java.io.IOException;
import java.nio.file.Path;

public class JavaUpdater {

    private final Path gameDir;
    private final OsType osType;
    private final ArchType archType;
    private final Callback callback;
    private Path javaHomePath;

    public JavaUpdater(Path gameDir, OsType osType, ArchType archType, Callback callback) {
        this.gameDir = gameDir;
        this.osType = osType;
        this.archType = archType;
        this.callback = callback;

        this.update();
    }

    public void update() {
        JavaDownloader javaDownloader = new JavaDownloader(
                gameDir.resolve("java"), new JavaVersionInfo(
                "17",
                JavaType.JRE,
                this.osType,
                this.archType,
                true),
                this.callback,
                false);

        try {
            this.javaHomePath = javaDownloader.downloadAndExtract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getJavaHomePath() {
        return javaHomePath;
    }
}
