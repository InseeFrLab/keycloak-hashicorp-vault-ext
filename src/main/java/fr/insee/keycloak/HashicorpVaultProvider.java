package fr.insee.keycloak;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.keycloak.vault.DefaultVaultCharSecret;
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
   private VaultService service;

   

   @Override
   public VaultRawSecret obtainSecret(String vaultSecretId) {
     return DefaultVaultRawSecret.forBuffer(
         Optional.of(
            service.getSecretFromVault(vaultUrl, realmName, vaultSecretId, vaultToken)));
      
   }

   @Override
   public void close() {
   }

   public HashicorpVaultProvider(String vaultUrl, String vaultToken, String realmName, VaultService service) {
      this.vaultUrl = vaultUrl;
      this.vaultToken = vaultToken;
      this.realmName = realmName;
      this.service = service;
   }


   
   
}