package fr.thegostsniperfr.arffornia_boostrap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UIManager {
    private static final String UI_IDLE_PATH = "ui/idle.png";
    private static final String UI_D_JAVA_PATH = "ui/dJava.png";
    private static final String UI_E_JAVA_PATH = "ui/eJava.png";
    private static final String UI_D_LAUNCHER_PATH = "ui/dLauncher.png";
    private static final String UI_LAUNCHING_PATH = "ui/launching.png";


    private final JFrame frame;

    public UIManager() {
        this.frame = new JFrame("Arffornia Installer");
        this.frame.setSize(300, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setUndecorated(true);
        this.frame.setVisible(true);
        this.frame.setLocationRelativeTo(null);
        this.frame.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));

        this.loadBG(UI_IDLE_PATH);
    }

    private void loadBG(String imgPath) {
        try {
            InputStream img = getClass().getClassLoader().getResourceAsStream(imgPath);
            this.frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(img))));
        } catch (IOException e) {
            ArfforniaBootstrap.getInstance().getLogger().err(e.getMessage());
        }
        this.frame.pack();
    }

    public void loadUI(ProgressSteps.ProgressStep step) {
        if(step == ProgressSteps.ProgressStep.DOWNLOADING_JAVA) {
            this.loadBG(UI_D_JAVA_PATH);
        }

        if(step == ProgressSteps.ProgressStep.EXTRACTING_JAVA) {
            this.loadBG(UI_E_JAVA_PATH);
        }

        if(step == ProgressSteps.ProgressStep.DOWNLOADING_LAUNCHER) {
            this.loadBG(UI_D_LAUNCHER_PATH);
        }

        if(step == ProgressSteps.ProgressStep.LAUNCHING_LAUNCHER) {
            this.loadBG(UI_LAUNCHING_PATH);
        }
    }
}
