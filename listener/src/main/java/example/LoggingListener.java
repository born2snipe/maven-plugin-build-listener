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
