/*******************************************************************************
 * Copyright (c) 2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.plugin.grunt.client.menu;

import com.codenvy.api.analytics.logger.AnalyticsEventLogger;
import com.codenvy.ide.api.action.Action;
import com.codenvy.ide.api.action.ActionEvent;
import com.codenvy.ide.api.app.AppContext;
import com.codenvy.ide.api.app.CurrentProject;
import com.codenvy.ide.extension.runner.client.run.RunController;
import com.codenvy.plugin.grunt.client.GruntExtension;
import com.codenvy.plugin.grunt.client.presenter.SelectGruntTaskPagePresenter;
import com.google.inject.Inject;

import java.util.logging.Logger;

/**
 * Action that perform a custom run.
 * @author Florent Benoit
 */
public class CustomGruntRunAction extends Action {

    private static final Logger LOG = Logger.getLogger(CustomGruntRunAction.class.getName());

    private final AnalyticsEventLogger analyticsEventLogger;

    private AppContext appContext;

    private SelectGruntTaskPagePresenter selectGruntTaskPagePresenter;

    private RunController runController;

    @Inject
    public CustomGruntRunAction(LocalizationConstant localizationConstant,
                                AppContext appContext, RunController runController,
                                AnalyticsEventLogger analyticsEventLogger,
                                SelectGruntTaskPagePresenter selectGruntTaskPagePresenter) {
        super(localizationConstant.gruntCustomRunText(), localizationConstant.gruntCustomRunDescription());
        this.appContext = appContext;
        this.analyticsEventLogger = analyticsEventLogger;
        this.runController = runController;
        this.selectGruntTaskPagePresenter = selectGruntTaskPagePresenter;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        analyticsEventLogger.log(this);
        selectGruntTaskPagePresenter.showDialog();
    }

    /** {@inheritDoc} */
    @Override
    public void update(ActionEvent e) {
        CurrentProject currentProject = appContext.getCurrentProject();
        if (currentProject != null) {
            final String runner = currentProject.getProjectDescription().getRunner();
            if (runner != null && runner.contains("grunt")) {
                e.getPresentation().setVisible(true);
            } else {
                e.getPresentation().setVisible(false);
            }
            e.getPresentation().setEnabled(currentProject.getIsRunningEnabled() && !runController.isAnyAppRunning());
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }


}
