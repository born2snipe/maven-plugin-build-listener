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
package org.apache.maven.plugin.event.api;

import org.apache.maven.execution.MavenSession;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EndOfBuildsListenerTest {
    @Mock
    private SuccessfulBuildListener successfulBuildListener, otherSuccessfulBuildListener;
    @Mock
    private FailedBuildListener failedBuildListener, otherFailedBuildListener;
    @Mock
    private Lookup lookup;
    @Mock
    private MavenSession session;
    @InjectMocks
    private EndOfBuildsListener listener;
    private MavenProject project;
    private MavenProject otherProject;
    private EventBuilder eventBuilder;

    @Before
    public void setUp() throws Exception {
        project = new MavenProject();
        otherProject = new MavenProject();

        eventBuilder = new EventBuilder();
        eventBuilder.expectProjects(project, otherProject);

        when(lookup.lookupAll(SuccessfulBuildListener.class)).thenReturn(new ArrayList(Arrays.asList(successfulBuildListener, otherSuccessfulBuildListener)));
        when(lookup.lookupAll(FailedBuildListener.class)).thenReturn(new ArrayList(Arrays.asList(failedBuildListener, otherFailedBuildListener)));
    }

    @Test
    public void sessionEnded_shouldNotNotifyListenersIfAProjectHasFailed() {
        eventBuilder.expectToFail(project).expectToPass(otherProject);

        BuildListener.Event event = eventBuilder.toEvent();
        listener.sessionEnded(event);

        verifyZeroInteractions(successfulBuildListener, otherSuccessfulBuildListener);
        verify(failedBuildListener).failedBuild(event);
        verify(otherFailedBuildListener).failedBuild(event);
    }


    @Test
    public void sessionEnded_shouldNotifyChildrenWhenAllTheProjectsAreSuccessful() {
        eventBuilder.expectToPass(project, otherProject);

        BuildListener.Event event = eventBuilder.toEvent();
        listener.sessionEnded(event);

        verify(successfulBuildListener).successfulBuild(event);
        verify(otherSuccessfulBuildListener).successfulBuild(event);
        verifyZeroInteractions(failedBuildListener, otherFailedBuildListener);
    }

}
