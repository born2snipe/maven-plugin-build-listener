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
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;

import java.util.Properties;

public class BuildUtil {
    public static long calculateBuildTimeFor(MavenSession session) {
        return System.currentTimeMillis() - session.getRequest().getStartTime().getTime();
    }

    public static long calculateTotalTimeOfAllProjects(MavenSession session) {
        long total = 0;
        MavenExecutionResult result = session.getResult();
        for (MavenProject mavenProject : session.getProjects()) {
            total += result.getBuildSummary(mavenProject).getTime();
        }
        return total;
    }

    public static boolean isSuccessful(MavenSession session) {
        MavenExecutionResult result = session.getResult();
        for (MavenProject project : session.getProjects()) {
            if (result.getBuildSummary(project) instanceof BuildFailure) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFailure(MavenSession session) {
        return !isSuccessful(session);
    }

    public static boolean isSurefireSkipped() {
        Properties properties = System.getProperties();
        return properties.containsKey("skipTests")
                || properties.containsKey("maven.test.skip");
    }

    public static boolean isFailsafeSkipped() {
        return isSurefireSkipped()
                || System.getProperties().containsKey("skipITs");
    }
}
