package org.lineageos.eleven.model.tag;

public class MetadataTag {
    protected AudioFileWithMetadata audioFile;
	private FieldKey key;
    
    MetadataTag(AudioFileWithMetadata audioFile, FieldKey key) {
        this.audioFile = audioFile;
		this.key = key;
    }

    public String getContent() throws MetadataException {
        return audioFile.readField(key);
    }

    public void setContent(String value) throws MetadataException {
        audioFile.writeField(key, value);
    }
    
    @Override
    public String toString() {
    	try {
			return getContent();
		} catch (MetadataException e) {
			return "";
		}
    }
}
