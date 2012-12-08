# Maven Build Listener Plugin

This maven plugin provides access to hook in listeners for a variety of events fired by maven

 - `api` contains the interface to implement
 - `example-listener` contains a custom listener
 - `plugin` contains the code for the maven plugin
 - `example-plugin-usage` contains how to configure the plugin in your `pom.xml`


Maven Repository:
```xml
    <repositories>
      <repository>
        <id>b2s-repo</id>
        <url>http://b2s-repo.googlecode.com/svn/trunk/mvn-repo</url>
      </repository>
    </repositories>
```
