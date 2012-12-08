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

import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.util.Properties;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventBuilder {
    private MavenSessionBuilder sessionBuilder = new MavenSessionBuilder();
    private Log log = mock(Log.class);
    private Properties listenerProperties = new Properties();

    public BuildListener.Event toEvent() {
        ExecutionEvent event = mock(ExecutionEvent.class);
        when(event.getSession()).thenReturn(sessionBuilder.toSession());
        return new BuildListener.Event(event, listenerProperties, log);
    }

    public EventBuilder expectLog(Log log) {
        this.log = log;
        return this;
    }

    public EventBuilder expectProperty(String name, String value) {
        listenerProperties.put(name, value);
        return this;
    }

    public EventBuilder expectProjects(MavenProject... projects) {
        sessionBuilder.expectProjects(projects);
        return this;
    }

    public EventBuilder expectToFail(MavenProject... projects) {
        for (MavenProject project : projects) {
            sessionBuilder.expectToFail(project);
        }
        return this;
    }

    public EventBuilder expectToPass(MavenProject... projects) {
        for (MavenProject project : projects) {
            sessionBuilder.expectToPass(project);
        }
        return this;
    }
}
