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
package com.codenvy.plugin.grunt.client;

import com.codenvy.ide.api.action.ActionManager;
import com.codenvy.ide.api.action.DefaultActionGroup;
import com.codenvy.ide.api.constraints.Constraints;
import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.extension.runner.client.RunnerLocalizationConstant;
import com.codenvy.plugin.grunt.client.menu.CustomGruntRunAction;
import com.codenvy.plugin.grunt.client.menu.LocalizationConstant;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import static com.codenvy.ide.api.action.IdeActions.GROUP_RUN;
import static com.codenvy.ide.api.constraints.Anchor.AFTER;


/**
 * Extension registering Grunt commands
 * @author Florent Benoit
 */
@Singleton
@Extension(title = "Grunt")
public class GruntExtension {

    @Inject
    public GruntExtension(ActionManager actionManager,
                          LocalizationConstant localizationConstant,
                          RunnerLocalizationConstant runnerLocalizationConstants,
                          CustomGruntRunAction customGruntRunAction) {

        actionManager.registerAction(localizationConstant.gruntCustomRunId(), customGruntRunAction);

        // Get Run menu
        DefaultActionGroup runMenuActionGroup = (DefaultActionGroup)actionManager.getAction(GROUP_RUN);

        // create constraint
        Constraints afterBuildConstraints = new Constraints(AFTER, runnerLocalizationConstants.customRunAppActionId());

        // Add Custom Grunt Run in build menu
        runMenuActionGroup.add(customGruntRunAction, afterBuildConstraints);

    }
}
