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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BuildUtilTest {
    @Mock
    private MavenSession session;
    private MavenProject project;
    private MavenProject otherProject;
    private MavenSessionBuilder builder;

    @Before
    public void setUp() throws Exception {
        project = new MavenProject();
        otherProject = new MavenProject();

        builder = new MavenSessionBuilder();
        builder.expectProjects(project, otherProject);
    }


    @Test
    public void isFailure_shouldReturnFalseIfAllProjectsHavePassed() {
        builder.expectToPass(project).expectToPass(otherProject);

        assertFalse(BuildUtil.isFailure(builder.toSession()));
    }


    @Test
    public void isFailure_shouldReturnTrueIfAnyProjectHasFailed() {
        builder.expectToPass(project).expectToFail(otherProject);

        assertTrue(BuildUtil.isFailure(builder.toSession()));
    }

    @Test
    public void isSuccessful_shouldReturnFalseWhenAProjectHasFailed() {
        builder.expectToPass(project);
        builder.expectToFail(otherProject);

        assertFalse(BuildUtil.isSuccessful(builder.toSession()));
    }

    @Test
    public void isSuccessful_shouldReturnTrueWhenAllProjectsAreSuccessful() {
        builder.expectToPass(project);
        builder.expectToPass(otherProject);

        assertTrue(BuildUtil.isSuccessful(builder.toSession()));
    }
}
