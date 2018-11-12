package org.lineageos.eleven.model.tag;

public class LabelledMetadataTag {
	
	private final String label;
	private final MetadataTag tag;

	public LabelledMetadataTag(String label, MetadataTag tag) {
		this.label = label;
		this.tag = tag;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getContent() throws MetadataException {
		return tag.getContent();
	}
	
	public void setContent(String content) throws MetadataException {
		tag.setContent(content);
	}
	
	@Override
	public String toString() {
		return tag.toString();
	}

}
