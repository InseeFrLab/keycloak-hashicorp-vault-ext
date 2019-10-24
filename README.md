# HashiCorp Vault provider for keycloak

* Create a jar with `mvn package`
* Copy the jar to `$KEYCLOAK_HOME/standalone/deployments`
* Add configuration in `standalone(-ha).xml`

```xml
<spi name="vault">
    <default-provider>hachicorp-vault</default-provider>
    <provider name="hachicorp-vault" enabled="true">
        <properties>
            <property name="token" value="token"/>
            <property name="url" value="https://vault-url/"/>
        </properties>
    </provider>
</spi>
```

optional property :
```xml
<property name="engine-name" value="token"/>
```

