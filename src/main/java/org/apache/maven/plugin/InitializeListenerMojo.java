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

import org.apache.maven.execution.AbstractExecutionListener;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenSession;

import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * Hooks up the listeners to notify when the build finishes
 *
 * @goal initialize
 * @requiresProject
 * @executionStrategy once-per-session
 */

public class InitializeListenerMojo extends AbstractMojo {
    /**
     * @parameter
     */
    private ListenerProperty[] listenerProperties = new ListenerProperty[0];

    /**
     * @parameter expression="${session}"
     * @readonly
     */
    private MavenSession session;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenExecutionRequest request = session.getRequest();
        ExecutionListener originalListener = request.getExecutionListener();

        if (isNotProxy(originalListener)) {
            ExecutionListener proxyListener = (ExecutionListener) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{ExecutionListener.class},
                    new NotifyListenersInvocationHandler(originalListener, convertListenerProperties(), getLog())
            );

            request.setExecutionListener(proxyListener);
        }
    }

    private boolean isNotProxy(ExecutionListener originalListener) {
        return originalListener instanceof AbstractExecutionListener;
    }

    private Properties convertListenerProperties() {
        Properties properties = new Properties();
        for (ListenerProperty listenerProperty : listenerProperties) {
            properties.put(listenerProperty.getName(), listenerProperty.getValue());
        }
        return properties;
    }

}
