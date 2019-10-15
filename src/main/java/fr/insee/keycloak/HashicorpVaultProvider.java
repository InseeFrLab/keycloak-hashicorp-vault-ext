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

   public HashicorpVaultProvider(String vaultUrl, String vaultToken, String realmName) {
	}

   @Override
   public VaultRawSecret obtainSecret(String vaultSecretId) {

      return DefaultVaultRawSecret.forBuffer(
         Optional.of(
            VaultService.getSecretFromVault(vaultUrl, resolveSecretPath(vaultSecretId), vaultToken)));
      
   }

   private String resolveSecretPath(String vaultSecretId) {
      // TODO: implémenter le chemin vers le secret (à partir du nom de realm et de l'identifiant du secret)
      return realmName+"/"+vaultSecretId;
   }

   @Override
   public void close() {
   }


   
   
}