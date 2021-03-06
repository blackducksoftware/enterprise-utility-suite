/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/
package com.blackducksoftware.tools.appuseradjuster.appidentifiersperuser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.tools.appuseradjuster.AppUserAdjusterConfig;
import com.blackducksoftware.tools.connector.codecenter.application.ApplicationPojo;

/**
 * Filters an application list based on a configured set of criteria (patterns
 * to match).
 *
 * @author sbillings
 *
 */
public class AppListFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()
            .getName());

    private final List<ApplicationPojo> unfilteredApps;

    private final String startsWithString;

    private final Pattern liveAppPattern;

    private final String targetVersion;

    private List<ApplicationPojo> filteredApps;

    public AppListFilter(AppUserAdjusterConfig config,
            List<ApplicationPojo> unfilteredApps, String appIdentifier) {
        this.unfilteredApps = unfilteredApps;
        startsWithString = appIdentifier + config.getSeparatorString();
        liveAppPattern = config.getLiveAppPattern();
        targetVersion = config.getApplicationVersion();
    }

    /**
     * Get the list of applications that meet the configured criteria.
     *
     * @return
     */
    public List<ApplicationPojo> getFilteredList() {
        if (filteredApps == null) {
            filteredApps = new ArrayList<ApplicationPojo>(unfilteredApps.size());
            for (ApplicationPojo app : unfilteredApps) {
                // search works on description too, so make sure the name really
                // matches
                if (!app.getName().startsWith(startsWithString)) {
                    logger.debug(
                            "Filtered out application [{} / {}] because it has the wrong app identifier",
                            app.getName(), app.getVersion());
                    continue; // skip it
                }
                // filter out non-live apps
                if (!isLiveApp(app.getName())) {
                    logger.debug(
                            "Filtered out application [{} / {}] because it is not a live app",
                            app.getName(), app.getVersion());
                    continue;
                }
                // filter out wrong versions
                if (!isTargetVersionOfApp(app.getVersion())) {
                    logger.debug(
                            "Filtered out application [{} / {}] because it has the wrong version",
                            app.getName(), app.getVersion());
                    continue;
                }
                filteredApps.add(app);
            }
        }
        return filteredApps;
    }

    private boolean isLiveApp(String appName) {
        if (liveAppPattern == null) {
            return true; // if no "live app pattern" specified: all apps are
            // "live"
        }
        Matcher liveAppMatcher = liveAppPattern.matcher(appName);

        if (liveAppMatcher.matches()) {
            return true;
        }

        return false; // this CC app is not the live version of the app
    }

    private boolean isTargetVersionOfApp(String appVersion) {
        if (targetVersion == null) {
            return true; // if no version specified, match 'em all
        } else if (appVersion.equals(targetVersion)) {
            return true;
        }

        return false; // this CC app is not the specified version of the app
    }
}
