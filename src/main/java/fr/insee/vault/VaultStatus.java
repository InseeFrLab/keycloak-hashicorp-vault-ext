package fr.insee.vault;

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
