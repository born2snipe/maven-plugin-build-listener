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

import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.classworlds.realm.ClassRealm;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Start a stop watch for the project being built
 *
 * @goal test
 */
public class TestMojo extends AbstractMojo {
    /**
     * The properties to be passed on to the listeners
     *
     * @parameter
     */
    private String[] properties = new String[0];

    /**
     * @component
     */
    private PlexusContainer container;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // we may need to try to do some fancy stuff here for thread safety
        ClassRealm mavensClassLoader = updateMavenClassLoaderWithPluginJars();

        // we will need to make sure we do not register our listener multiple times
        Thread thread = new Thread(new RegisterListenerRunnable(container, properties));
        thread.setContextClassLoader(mavensClassLoader);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {

        }
    }

    private ClassRealm updateMavenClassLoaderWithPluginJars() {
        URLClassLoader pluginsClassLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();

        ClassRealm mavensClassLoader = container.getContainerRealm();
        List<URL> existingJars = Arrays.asList(mavensClassLoader.getURLs());
        List<URL> pluginJars = new ArrayList<URL>(Arrays.asList(pluginsClassLoader.getURLs()));

        for (URL url : existingJars) {
            pluginJars.remove(url);
        }

        for (URL pluginJar : pluginJars) {
            mavensClassLoader.addURL(pluginJar);
        }
        return mavensClassLoader;
    }

}
