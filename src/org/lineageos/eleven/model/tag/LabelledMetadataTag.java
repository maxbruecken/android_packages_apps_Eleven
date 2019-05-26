package org.lineageos.eleven.model.tag;

public class LabelledMetadataTag {
	
	private final String label;
	private final MetadataTag tag;
	private String content;

	public LabelledMetadataTag(String label, MetadataTag tag) throws MetadataException {
		this.label = label;
		this.tag = tag;
		this.content = tag.getContent();
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) throws MetadataException {
	    this.content = content;
		tag.setContent(content);
	}
	
	@Override
	public String toString() {
		return content;
	}
}
