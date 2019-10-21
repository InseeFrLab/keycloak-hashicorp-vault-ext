package fr.insee.vault;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * VaultService
 */
public class VaultService {

   public static ByteBuffer  getSecretFromVault(String vaultUrl, String secretPath, String vaultToken){
      //TODO: Implémenter la récupération depuis vault
      return ByteBuffer.allocate(100).put("secret".getBytes(StandardCharsets.UTF_8));
   }
   
   public static String run(String url) throws IOException {
	   OkHttpClient client = new OkHttpClient();
	   Request request = new Request.Builder()
			   .url(url)
			   .build();
	   try (Response response = client.newCall(request).execute()) {
       return response.body().string();
     }
   }
   
   public static boolean isVaultAvailable(String vaultUrl, String vaultToken){
	   //get vault health
	   String healthVaultUrl = vaultUrl + "v1/sys/health";
       String vaultHealthResponse;
	try {
		vaultHealthResponse = run(healthVaultUrl);
	} catch (IOException e) {
		vaultHealthResponse = "";
		e.printStackTrace();
	}
	  
       //parse json
	   ObjectMapper objectMapper = new ObjectMapper();
	   VaultStatus status = new VaultStatus();
	try {
		status = objectMapper.readValue(vaultHealthResponse, VaultStatus.class);
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	   
	   return (status.isInitialized() && !(status.isSealed()));
   }
}