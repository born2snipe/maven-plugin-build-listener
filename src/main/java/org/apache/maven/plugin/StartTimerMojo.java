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
 * Start a stop watch for the project being built
 *
 * @goal start
 */
public class StartTimerMojo extends AbstractMojo {
    private ListenerContextFactory listenerContextFactory = new ListenerContextFactory();
    private Lookup lookup = Lookup.getDefault();
    private StopWatchProvider stopWatchProvider = new StopWatchProvider();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        stopWatchProvider.get().start();

        ListenerContext listenerContext = listenerContextFactory.build(0L, (MavenProject) getPluginContext().get("project"), getLog());

        Collection<? extends TimerListener> listeners = lookup.lookupAll(TimerListener.class);
        for (TimerListener listener : listeners) {
            listener.onStart(listenerContext);
        }
    }

}
