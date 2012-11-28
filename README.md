# Maven Build Time Plugin

This maven plugin provides goals to start/stop a timer, when the timer is stopped it notifies listeners of the elapsed time and the project that was built.

## How do I implement a listener?

   - Create a new maven module and add the plugin's jar as a dependency
```xml
    <dependency>
        <groupId>b2s</groupId>
        <artifactId>maven-plugin-build-time</artifactId>
        <version>X.X.X</version>
        <type>jar</type>
        <scope>provided</scope>
    </dependency>
```
  - Now implement the TimerListener interface

  - For the plugin to discover your listener you need to annotate your TimerListener implementation with the [@ServiceProvider](http://bits.netbeans.org/dev/javadoc/org-openide-util-lookup/org/openide/util/lookup/ServiceProvider.html) annotation

## How do I add a listener to the plugin?

   All you need to do is add the module that contains the listener as a dependency to the plugin

```xml
   <plugin>
        <groupId>b2s</groupId>
        <artifactId>maven-plugin-build-time</artifactId>
        <version>X.X.X</version>
        <dependencies>
          <dependency>
            <groupId>custom-listener</groupId>
            <artifactId>custom-listener</artifactId>
            <version>Y.Y.Y</version>
          </dependency>
        </dependencies>
    </plugin>
```

## How do I pass arguments to my listener?

All you need to do is define `<listenerProperties>` in the plugins `<configuration>`

```xml
   <plugin>
        <groupId>b2s</groupId>
        <artifactId>maven-plugin-build-time</artifactId>
        <version>X.X.X</version>
        <configuration>
        <listenerProperties>
            <listenerProperty>
                <name>key1</name>
                <value>value1</value>
            </listenerProperty>
        </listenerProperties>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>custom-listener</groupId>
            <artifactId>custom-listener</artifactId>
            <version>Y.Y.Y</version>
          </dependency>
        </dependencies>
    </plugin>
```