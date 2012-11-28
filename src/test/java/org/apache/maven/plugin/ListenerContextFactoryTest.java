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
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ListenerContextFactoryTest {
    @Test
    public void build() {
        Log log = Mockito.mock(Log.class);
        MavenProject project = new MavenProject();

        ListenerContextFactory factory = new ListenerContextFactory();

        ListenerContext context = factory.build(2L, project, log);

        assertNotNull(context);
        assertEquals(project, context.getProject());
        assertEquals(log, context.getLog());
        assertEquals(2l, context.getElapsedMillisTime());
    }
}
