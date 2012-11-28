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

import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.execution.ExecutionListener;
import org.apache.maven.plugin.logging.Log;
import org.openide.util.Lookup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;

public class NotifyListenersInvocationHandler implements InvocationHandler {
    private ExecutionListener originalListener;
    private Properties listenerProperties;
    private Log log;
    private Lookup lookup = Lookup.getDefault();

    public NotifyListenersInvocationHandler(ExecutionListener originalListener, Properties listenerProperties, Log log) {
        this.originalListener = originalListener;
        this.listenerProperties = listenerProperties;
        this.log = log;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isPassingBuild(method) || isFailingBuild(method)) {
            ExecutionEvent event = (ExecutionEvent) args[0];
            Collection<? extends EndOfBuildListener> listeners = lookup.lookupAll(EndOfBuildListener.class);
            for (EndOfBuildListener listener : listeners) {
                if (isFailingBuild(method)) {
                    listener.buildFailed(event, listenerProperties, log);
                } else {
                    listener.buildSuccessful(event, listenerProperties, log);
                }
            }
        }

        return method.invoke(originalListener, args);
    }

    private boolean isFailingBuild(Method method) {
        return method.getName().equals("projectFailed");
    }

    private boolean isPassingBuild(Method method) {
        return method.getName().equals("projectSucceeded");
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }

}