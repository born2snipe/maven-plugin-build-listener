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

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.util.Lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StopTimerMojoTest {
    @Mock
    private ListenerContextFactory listenerContextFactory;
    @Mock
    private Log log;
    @Mock
    private Lookup lookup;
    @Mock
    private StopWatch stopWatch;
    @Mock
    private StopWatchProvider stopWatchProvider;
    @Mock
    private TimerListener listener;
    @InjectMocks
    private StopTimerMojo mojo;
    private MavenProject project;

    @Before
    public void setUp() throws Exception {
        project = new MavenProject();
        HashMap pluginContext = new HashMap();
        pluginContext.put("project", project);

        mojo.setPluginContext(pluginContext);
        when(stopWatchProvider.get()).thenReturn(stopWatch);
    }

    @Test
    public void execute_shouldNotifyTheListenersOfTheElapsedTime() throws MojoExecutionException, MojoFailureException {
        ListenerContext listenerContext = new ListenerContext();

        when(lookup.lookupAll(TimerListener.class)).thenReturn(new ArrayList(Arrays.asList(listener)));
        when(stopWatch.getElapsedTime()).thenReturn(2L);
        when(listenerContextFactory.build(2L, project, log)).thenReturn(listenerContext);

        mojo.execute();

        verify(stopWatch).stop();
        verify(listener).onStop(listenerContext);
    }

    @Test
    public void execute_shouldLogAWarningIfNoListenersAreProvided() throws MojoExecutionException, MojoFailureException {
        when(lookup.lookupAll(TimerListener.class)).thenReturn(new ArrayList());

        mojo.execute();

        verify(log).warn("No instance(s) of " + TimerListener.class.getName() + " were provided");
    }
}
