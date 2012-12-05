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

import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.plugin.logging.Log;

import java.util.Properties;

public interface BuildListener {
    void projectDiscoveryStarted(Event event);

    void sessionStarted(Event event);

    void sessionEnded(Event event);

    void projectStarted(Event event);

    void projectSucceeded(Event event);

    void projectFailed(Event event);

    void mojoSkipped(Event event);

    void mojoStarted(Event event);

    void mojoSucceeded(Event event);

    void mojoFailed(Event event);

    void forkStarted(Event event);

    void forkSucceeded(Event event);

    void forkFailed(Event event);

    void forkedProjectStarted(Event event);

    void forkedProjectSucceeded(Event event);

    void forkedProjectFailed(Event event);


    public static class Event {
        public final ExecutionEvent event;
        public final Properties listenerProperties;
        public final Log log;

        public Event(ExecutionEvent event, Properties listenerProperties, Log log) {
            this.event = event;
            this.listenerProperties = listenerProperties;
            this.log = log;
        }
    }
}
