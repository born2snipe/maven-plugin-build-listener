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
package example;

import org.apache.maven.plugin.event.api.BuildListener;
import org.apache.maven.plugin.event.api.FailedBuildListener;
import org.apache.maven.plugin.event.api.SuccessfulBuildListener;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders({
        @ServiceProvider(service = FailedBuildListener.class),
        @ServiceProvider(service = SuccessfulBuildListener.class)
})
public class BuildFinishedListener implements FailedBuildListener, SuccessfulBuildListener {
    @Override
    public void failedBuild(BuildListener.Event event) {
        event.log.info("BOTH LISTENERS (FAILED)");
    }

    @Override
    public void successfulBuild(BuildListener.Event event) {
        event.log.info("BOTH LISTENERS (SUCCESS)");
    }
}
