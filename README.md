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
            <property name="engine-name" value="secret"/>
        </properties>
    </provider>
</spi>
```

To choose the K/V2 key version to use in keycloak, add :your_version to the key label (example : ${vault.master:3}). By default, the last version will be used.