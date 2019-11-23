package fr.insee.keycloak;

import java.util.Optional;

import org.keycloak.vault.DefaultVaultRawSecret;
import org.keycloak.vault.VaultProvider;
import org.keycloak.vault.VaultRawSecret;

import fr.insee.vault.VaultService;

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
      String vaultSecretName;
      String vaultSecretVersion;
      if (vaultSecretId.contains(":")){
         vaultSecretName = vaultSecretId.split(":")[0];
         vaultSecretVersion = vaultSecretId.split(":")[1];
      }
      else {
         vaultSecretName = vaultSecretId;
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