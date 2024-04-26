package fr.thegostsniperfr.arffornia_boostrap;

import fr.thegostsniperfr.java_downloader.Callback;
import fr.thegostsniperfr.java_toolbox.logger.Logger;

public class ProgressSteps {
    private final Logger logger;

    private ProgressStep progressStep;
    private final UIManager ui;

    public ProgressSteps(Logger logger, UIManager ui) {
        this.logger = logger;
        this.ui = ui;
        this.progressStep = ProgressStep.STARTING;
    }

    public enum ProgressStep {
        STARTING,
        FETCHING_JAVA,
        DOWNLOADING_JAVA,
        EXTRACTING_JAVA,
        DONE_JAVA,
        FETCHING_LAUNCHER,
        DOWNLOADING_LAUNCHER,
        LAUNCHING_LAUNCHER
    }

    public void onJavaStep(Callback.Step step) {
        if(step == Callback.Step.FETCHING) {
            this.setProgressStep(ProgressStep.FETCHING_JAVA);
        }

        if(step == Callback.Step.DOWNLOADING) {
            this.setProgressStep(ProgressStep.DOWNLOADING_JAVA);
        }

        if(step == Callback.Step.EXTRACTING) {
            this.setProgressStep(ProgressStep.EXTRACTING_JAVA);
        }

        if(step == Callback.Step.DONE) {
            this.setProgressStep(ProgressStep.DONE_JAVA);
        }

        this.ui.loadUI(this.progressStep);
    }

    public void setProgressStep(ProgressStep progressStep) {
        this.progressStep = progressStep;
        this.logger.info(this.progressStep.toString());
    }
}
