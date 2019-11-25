package fr.insee.vault;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;

import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

/**
 * VaultService
 */
public class VaultService {

	private KeycloakSession session;
	private static final Logger logger = Logger.getLogger(VaultService.class);
	
	public VaultService(KeycloakSession session) {
		this.session = session;
	}
	
	public ByteBuffer getSecretFromVault(String vaultUrl, String realm, String vaultSecretEngineName, String secretName, String vaultToken, String secretVersion) {
		try {
			JsonNode node = SimpleHttp.doGet(vaultUrl + "v1/" + vaultSecretEngineName + "/data/" + realm+ "?version=" +secretVersion, session)
			.header("X-Vault-Token", vaultToken)
			.asJson();
		   byte[] secretBytes = node.get("data").get("data").get(secretName).textValue().getBytes(StandardCharsets.UTF_8);
			return ByteBuffer.wrap(secretBytes);
		} catch (IOException e) {
			logger.error("secret not available", e);
			return null;
		}
	}

	public boolean isVaultAvailable(String vaultUrl, String vaultToken) {
		String healthVaultUrl = vaultUrl + "v1/sys/health";
		try {
			JsonNode vaultHealthResponseNode = SimpleHttp.doGet(healthVaultUrl, session)
					.asJson();
			boolean vaultIsInitialized = vaultHealthResponseNode.get("initialized").asBoolean();
			boolean vaultIsSealed = vaultHealthResponseNode.get("sealed").asBoolean();
			return (vaultIsInitialized && !vaultIsSealed);
		} catch (IOException e) {
			logger.error("vault service unavailable", e);
			return false;
		}
	}

}