package manager.service.utils;

public enum Context {
	ACTIF("actif"),
	INACTIF("inactif"),
	OCCUPE("piece occupe"),
	VIDE("piece vide"),
	TROPLONG("trop long"),
	COUVREFEU("couvre feu"),
	ACCESINTERDIT("acces interdit"),
	TROPCHAUD("trop chaud"),
	TROPFROID("trop froid"),
	ERREUR("dispositif HS");
	
	public final String descriptor;
	
	private Context(String descriptor) {
		this.descriptor = descriptor;
	}
	
	public static Context getByDescriptor(final String descriptor) {
		for(Context context : values()) {
			if(context.descriptor.equalsIgnoreCase(descriptor)) {
				return context;
			}
		}
		return null;
	}
}
