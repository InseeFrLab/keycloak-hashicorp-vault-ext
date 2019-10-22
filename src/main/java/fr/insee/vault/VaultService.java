package fr.insee.vault;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;


/**
 * VaultService
 */
public class VaultService {

	private KeycloakSession session;
	private static final Logger logger = Logger.getLogger(VaultService.class);
	

	public ByteBuffer getSecretFromVault(String vaultUrl, String realm, String secretName, String vaultToken) {
		try {
			JsonNode node = SimpleHttp.doGet(vaultUrl + "v1/secret/data/" + realm, session).header("X-Vault-Token", vaultToken).asJson();
		   byte[] secretBytes = node.get("data").get("data").get(secretName).textValue().getBytes(StandardCharsets.UTF_8);
			return ByteBuffer.wrap(secretBytes);
		} catch (IOException e) {
			logger.error("IOexception");
			return null;
		}
	}

	public  String get(String url) throws IOException {
		return SimpleHttp.doGet(url, session).asString();
	}

	public  boolean isVaultAvailable(String vaultUrl, String vaultToken) {
		// get vault health
		String healthVaultUrl = vaultUrl + "v1/sys/health";
		String vaultHealthResponse;
		try {
			vaultHealthResponse = get(healthVaultUrl);
		} catch (IOException e) {
			vaultHealthResponse = "";
			e.printStackTrace();
		}

		// parse json
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

	public VaultService(KeycloakSession session) {
		this.session = session;
	}
}