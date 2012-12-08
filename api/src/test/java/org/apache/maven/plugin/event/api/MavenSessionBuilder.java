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

import org.apache.maven.execution.BuildFailure;
import org.apache.maven.execution.BuildSuccess;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MavenSessionBuilder {
    private MavenSession session = mock(MavenSession.class);
    private final DefaultMavenExecutionResult result = new DefaultMavenExecutionResult();
    private List<MavenProject> projects = new ArrayList<MavenProject>();

    public MavenSessionBuilder() {
        when(session.getResult()).thenReturn(result);
        when(session.getProjects()).thenReturn(projects);
    }

    public MavenSessionBuilder expectProjects(MavenProject... projects) {
        this.projects.addAll(Arrays.asList(projects));
        return this;
    }

    public MavenSession toSession() {
        return session;
    }

    public MavenSessionBuilder expectToPass(MavenProject project) {
        result.addBuildSummary(new BuildSuccess(project, 0));
        return this;
    }

    public MavenSessionBuilder expectToFail(MavenProject project) {
        result.addBuildSummary(new BuildFailure(project, 0, null));
        return this;
    }
}
