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

import java.util.Properties;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ListenerContextFactoryTest {
    @Test
    public void build() {
        Log log = Mockito.mock(Log.class);
        MavenProject project = new MavenProject();

        ListenerContextFactory factory = new ListenerContextFactory();
        ListenerProperty[] listenerProperties = new ListenerProperty[2];
        listenerProperties[0] = property("key1", "value1");
        listenerProperties[1] = property("key2", "value2");

        ListenerContext context = factory.build(2L, project, log, listenerProperties);

        assertNotNull(context);
        assertEquals(project, context.getProject());
        assertEquals(log, context.getLog());
        assertEquals(2l, context.getElapsedMillisTime());

        Properties properties = context.getListenerProperties();
        assertNotNull(properties);
        assertEquals("value1", properties.get("key1"));
        assertEquals("value2", properties.get("key2"));
    }

    private ListenerProperty property(String key, String value) {
        ListenerProperty property = new ListenerProperty();
        property.setName(key);
        property.setValue(value);
        return property;
    }
}
