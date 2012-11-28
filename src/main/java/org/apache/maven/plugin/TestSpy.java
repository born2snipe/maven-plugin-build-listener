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

import org.apache.maven.eventspy.AbstractEventSpy;
import org.apache.maven.execution.BuildSummary;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.project.MavenProject;

import java.util.List;

public class TestSpy extends AbstractEventSpy {
    private String[] listenerProperties;

    public TestSpy(String[] listenerProperties) {
        this.listenerProperties = listenerProperties;
    }

    @Override
    public void onEvent(Object event) throws Exception {
        //  String are able to be passed to the thread
        for (String listenerProperty : listenerProperties) {
            System.out.println(listenerProperty);
        }

        if (event instanceof MavenExecutionResult) {
            MavenExecutionResult resultEvent = (MavenExecutionResult) event;
            List<MavenProject> projects = resultEvent.getTopologicallySortedProjects();
            for (MavenProject project : projects) {
                // different build summary types for failure or success
                BuildSummary buildSummary = resultEvent.getBuildSummary(project);
                System.out.println(project + "(" + buildSummary + ") <" + buildSummary.getTime() + ">");
            }
        }
    }

}
