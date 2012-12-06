package org.apache.maven.plugin.event.api;

import org.apache.maven.execution.BuildFailure;
import org.apache.maven.execution.BuildSuccess;
import org.apache.maven.execution.DefaultMavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuildUtilTest {
    @Mock
    private MavenSession session;
    private DefaultMavenExecutionResult result;
    private MavenProject project;
    private MavenProject otherProject;

    @Before
    public void setUp() throws Exception {
        result = new DefaultMavenExecutionResult();
        project = new MavenProject();
        otherProject = new MavenProject();

        when(session.getResult()).thenReturn(result);
        when(session.getProjects()).thenReturn(Arrays.asList(project, otherProject));
    }


    @Test
    public void isFailure_shouldReturnFalseIfAllProjectsHavePassed() {
        expectToPass(project);
        expectToPass(otherProject);

        assertFalse(BuildUtil.isFailure(session));
    }


    @Test
    public void isFailure_shouldReturnTrueIfAnyProjectHasFailed() {
        expectToPass(project);
        expectToFail(otherProject);

        assertTrue(BuildUtil.isFailure(session));
    }

    @Test
    public void isSuccessful_shouldReturnFalseWhenAProjectHasFailed() {
        expectToPass(project);
        expectToFail(otherProject);

        assertFalse(BuildUtil.isSuccessful(session));
    }

    @Test
    public void isSuccessful_shouldReturnTrueWhenAllProjectsAreSuccessful() {
        expectToPass(project);
        expectToPass(otherProject);

        assertTrue(BuildUtil.isSuccessful(session));
    }

    private void expectToPass(MavenProject project) {
        result.addBuildSummary(new BuildSuccess(project, 0));
    }

    private void expectToFail(MavenProject project) {
        result.addBuildSummary(new BuildFailure(project, 0, null));
    }
}
