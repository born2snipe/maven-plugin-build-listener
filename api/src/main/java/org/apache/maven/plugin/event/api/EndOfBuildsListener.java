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

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = BuildListener.class)
public final class EndOfBuildsListener extends AbstractBuildListener {
    private Lookup lookup = Lookup.getDefault();

    @Override
    public void sessionEnded(Event event) {
        if (BuildUtil.isSuccessful(event.event.getSession())) {
            for (SuccessfulBuildListener listener : lookup.lookupAll(SuccessfulBuildListener.class)) {
                listener.successfulBuild(event);
            }
        } else {
            for (FailedBuildListener listener : lookup.lookupAll(FailedBuildListener.class)) {
                listener.failedBuild(event);
            }
        }
    }
}
