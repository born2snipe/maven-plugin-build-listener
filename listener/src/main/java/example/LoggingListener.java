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
package example;

import org.apache.maven.execution.BuildSuccess;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.event.api.AbstractBuildListener;
import org.apache.maven.plugin.event.api.BuildListener;
import org.apache.maven.plugin.event.api.BuildUtil;
import org.apache.maven.project.MavenProject;
import org.openide.util.lookup.ServiceProvider;

import java.util.Map;

@ServiceProvider(service = BuildListener.class)
public class LoggingListener extends AbstractBuildListener {
    @Override
    public void sessionEnded(BuildListener.Event event) {
        event.log.info("Properties passed to listener:");
        for (Map.Entry<Object, Object> entry : event.listenerProperties.entrySet()) {
            event.log.info(entry.getKey() + " = " + entry.getValue());
        }

        MavenSession session = event.event.getSession();
        MavenProject project = session.getTopLevelProject();
        if (session.getResult().getBuildSummary(project) instanceof BuildSuccess) {
            event.log.info("FINISHED -- PASSED -- " + BuildUtil.calculateBuildTimeFor(session));
        } else {
            event.log.info("FINISHED -- FAILED -- " + BuildUtil.calculateBuildTimeFor(session));
        }
    }
}
