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

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.util.Properties;

public class ListenerContext {
    private long elapsedMillisTime;
    private MavenProject project;
    private Log log;
    private Properties listenerProperties = new Properties();

    public long getElapsedMillisTime() {
        return elapsedMillisTime;
    }

    public void setElapsedMillisTime(long elapsedMillisTime) {
        this.elapsedMillisTime = elapsedMillisTime;
    }

    public MavenProject getProject() {
        return project;
    }

    public void setProject(MavenProject project) {
        this.project = project;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public Properties getListenerProperties() {
        return listenerProperties;
    }

    public void setListenerProperties(Properties listenerProperties) {
        this.listenerProperties = listenerProperties;
    }
}