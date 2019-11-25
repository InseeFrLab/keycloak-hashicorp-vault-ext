package fr.insee.keycloak;

import java.util.Optional;

import org.keycloak.vault.DefaultVaultRawSecret;
import org.keycloak.vault.VaultProvider;
import org.keycloak.vault.VaultRawSecret;

import fr.insee.vault.VaultService;
import org.jboss.logging.Logger;

/**
 * HashicorpVaultProviderFactory
 */
public class HashicorpVaultProvider implements VaultProvider {
   private String vaultUrl;
   private String vaultToken;
   private String realmName;
   private String vaultSecretEngineName;
   private VaultService service;

   @Override
   public VaultRawSecret obtainSecret(String vaultSecretId) {
      String[] vaultSecretComponent = vaultSecretId.split(":");
      int nbComponent = vaultSecretComponent.length;
      String vaultSecretName = "";
      String vaultSecretVersion;
      if (nbComponent > 0 && vaultSecretComponent[nbComponent - 1].matches("[0-9]+")){
         vaultSecretName = vaultSecretName.concat(vaultSecretComponent[0]);
         for (int i = 1 ; i < nbComponent - 1 ; i++){
            vaultSecretName = vaultSecretName.concat(":").concat(vaultSecretComponent[i]);
         }
         vaultSecretVersion = vaultSecretComponent[nbComponent - 1];
      }
      else {
         vaultSecretName = vaultSecretName.concat(vaultSecretId);
         vaultSecretVersion = "0";
      }
     return DefaultVaultRawSecret.forBuffer(
         Optional.of(
            service.getSecretFromVault(vaultUrl, realmName, vaultSecretEngineName, vaultSecretName, vaultToken, vaultSecretVersion)));
   }

   @Override
   public void close() {
   }

   public HashicorpVaultProvider(String vaultUrl, String vaultToken, String realmName, String vaultSecretEngineName, VaultService service) {
      this.vaultUrl = vaultUrl;
      this.vaultToken = vaultToken;
      this.realmName = realmName;
      this.vaultSecretEngineName = vaultSecretEngineName;
      this.service = service;
   }


   
   
}