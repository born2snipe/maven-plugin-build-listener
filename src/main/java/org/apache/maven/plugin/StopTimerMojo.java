/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.apache.maven.plugin;

import org.apache.maven.project.MavenProject;
import org.openide.util.Lookup;

import java.util.Collection;

/**
 * Stop the stop watch for the project being built and notify the notifiers
 *
 * @goal stop
 */
public class StopTimerMojo extends AbstractMojo {
    /**
     * The properties to be passed on to the listeners
     *
     * @parameter
     */
    private ListenerProperty[] listenerProperties = new ListenerProperty[0];
    private StopWatchProvider stopWatchProvider = new StopWatchProvider();
    private Lookup lookup = Lookup.getDefault();
    private ListenerContextFactory listenerContextFactory = new ListenerContextFactory();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        StopWatch stopWatch = stopWatchProvider.get();
        stopWatch.stop();

        Collection<? extends TimerListener> buildTimeListeners = lookup.lookupAll(TimerListener.class);
        if (buildTimeListeners.isEmpty()) {
            getLog().warn("No instance(s) of " + TimerListener.class.getName() + " were provided");
        }

        MavenProject project = (MavenProject) getPluginContext().get("project");
        ListenerContext listenerContext = listenerContextFactory.build(stopWatch.getElapsedTime(), project, getLog(), listenerProperties);
        for (TimerListener buildTimeListener : buildTimeListeners) {
            buildTimeListener.onStop(listenerContext);
        }
    }

    public void setListenerProperties(ListenerProperty[] listenerProperties) {
        this.listenerProperties = listenerProperties;
    }
}
