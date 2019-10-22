package fr.insee.vault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultStatus {
	private boolean initialized;
	private boolean sealed;
	
	public VaultStatus() {
		initialized = false;
		sealed = true;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	public boolean isSealed() {
		return sealed;
	}
	public void setSealed(boolean sealed) {
		this.sealed = sealed;
	}
}
