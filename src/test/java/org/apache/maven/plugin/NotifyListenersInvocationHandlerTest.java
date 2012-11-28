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

import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openide.util.Lookup;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NotifyListenersInvocationHandlerTest {
    @Mock
    private ExecutionListener originalListener;
    @Mock
    private ExecutionEvent event;
    @Mock
    private Lookup lookup;
    @Mock
    private EndOfBuildListener listener;
    @Mock
    private Log log;
    private NotifyListenersInvocationHandler handler;
    private MavenProject project;
    private Properties listenerProperties;

    @Before
    public void setUp() throws Exception {
        project = new MavenProject();
        listenerProperties = new Properties();

        handler = new NotifyListenersInvocationHandler(originalListener, listenerProperties, log);
        handler.setLookup(lookup);

        when(lookup.lookupAll(EndOfBuildListener.class)).thenReturn(new ArrayList(Arrays.asList(listener)));
        when(event.getProject()).thenReturn(project);
    }


    @Test
    public void invoke_shouldNotifyListenersWhenTheBuildFails() throws Throwable {
        handler.invoke(null, method("projectFailed"), new Object[]{event});

        verify(originalListener).projectFailed(event);
        verify(listener).buildFailed(event, listenerProperties, log);
    }

    @Test
    public void invoke_shouldNotifyListenersWhenTheBuildPasses() throws Throwable {
        handler.invoke(null, method("projectSucceeded"), new Object[]{event});

        verify(originalListener).projectSucceeded(event);
        verify(listener).buildSuccessful(event, listenerProperties, log);
    }

    @Test
    public void invokeShouldAlwaysCallTheOriginalListener() throws Throwable {
        handler.invoke(null, method("projectDiscoveryStarted"), new Object[]{event});

        verify(originalListener).projectDiscoveryStarted(event);
        verifyZeroInteractions(listener);
    }

    private Method method(String methodName) {
        try {
            return ExecutionListener.class.getDeclaredMethod(methodName, ExecutionEvent.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
