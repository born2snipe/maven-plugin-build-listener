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

public abstract class AbstractBuildListener implements BuildListener {
    @Override
    public void projectDiscoveryStarted(Event event) {

    }

    @Override
    public void sessionStarted(Event event) {

    }

    @Override
    public void sessionEnded(Event event) {

    }

    @Override
    public void projectStarted(Event event) {

    }

    @Override
    public void projectSucceeded(Event event) {

    }

    @Override
    public void projectFailed(Event event) {

    }

    @Override
    public void mojoSkipped(Event event) {

    }

    @Override
    public void mojoStarted(Event event) {

    }

    @Override
    public void mojoSucceeded(Event event) {

    }

    @Override
    public void mojoFailed(Event event) {

    }

    @Override
    public void forkStarted(Event event) {

    }

    @Override
    public void forkSucceeded(Event event) {

    }

    @Override
    public void forkFailed(Event event) {

    }

    @Override
    public void forkedProjectStarted(Event event) {

    }

    @Override
    public void forkedProjectSucceeded(Event event) {

    }

    @Override
    public void forkedProjectFailed(Event event) {

    }
}
