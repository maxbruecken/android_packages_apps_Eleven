package org.lineageos.eleven.model.tag;

public enum FieldKey {
	
	ALBUM("ALBUM"), ARTIST("ARTIST"), TITLE("TITLE"), COMMENT("COMMENT"), YEAR("YAER"), GENRE("GENRE"), TRACK("TRACK");
	
	private String name;

	private FieldKey(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
