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
import org.apache.maven.plugin.event.api.BuildListener;
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
        BuildListener.Event event = new BuildListener.Event((ExecutionEvent) args[0], listenerProperties, log);
        Collection<? extends BuildListener> listeners = lookup.lookupAll(BuildListener.class);
        for (BuildListener listener : listeners) {
            invokeListener(method, listener, event);
        }
        return method.invoke(originalListener, args);
    }

    private void invokeListener(Method method, BuildListener listener, BuildListener.Event event) throws Exception {
        Method methodToCall = BuildListener.class.getDeclaredMethod(method.getName(), BuildListener.Event.class);
        methodToCall.invoke(listener, event);
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }

}
