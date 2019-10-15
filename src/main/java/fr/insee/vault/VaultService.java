package fr.insee.vault;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * VaultService
 */
public class VaultService {

   public static ByteBuffer  getSecretFromVault(String vaultUrl, String secretPath, String vaultToken){
      //TODO: Implémenter la récupération depuis vault
      return ByteBuffer.allocate(100).put("secret".getBytes(StandardCharsets.UTF_8));
   }

   public static boolean isVaultAvailable(String vaultUrl, String vaultToken){
      //TODO: Vérifier que vault répond
      return true;
   }

}