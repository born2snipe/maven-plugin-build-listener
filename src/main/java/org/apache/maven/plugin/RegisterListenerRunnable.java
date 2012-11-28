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

import org.apache.maven.eventspy.internal.EventSpyDispatcher;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

public class RegisterListenerRunnable implements Runnable {
    private final PlexusContainer container;
    private String[] listenerProperties;

    public RegisterListenerRunnable(PlexusContainer container, String[] listenerProperties) {
        this.container = container;
        this.listenerProperties = listenerProperties;
    }

    @Override
    public void run() {
        try {
            EventSpyDispatcher dispatcher = container.lookup(EventSpyDispatcher.class);
            dispatcher.getEventSpies().add(new TestSpy(listenerProperties));

        } catch (ComponentLookupException e) {

        }
    }
}
