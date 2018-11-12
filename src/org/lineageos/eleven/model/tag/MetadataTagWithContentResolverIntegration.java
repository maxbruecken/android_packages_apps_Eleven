package org.lineageos.eleven.model.tag;

import android.content.ContentValues;
import android.content.Context;

import org.lineageos.eleven.model.tag.AudioFileWithMetadata;
import org.lineageos.eleven.model.tag.MetadataTag;

public abstract class MetadataTagWithContentResolverIntegration extends MetadataTag {
    private final Context context;
    private final String column;

    public MetadataTagWithContentResolverIntegration(AudioFileWithMetadata audioFile, FieldKey key, Context context, String column) {
        super(audioFile, key);
        this.context = context;
        this.column = column;
    }

    @Override
    public void setContent(String value) throws MetadataException {
        super.setContent(value);

        notifyContentResolver();
    }

	private void notifyContentResolver() throws MetadataException {
		ContentValues values = new ContentValues(1);
		values.put(column, getContent());
		context.getContentResolver().update(audioFile.getFileUri(), values, null, null);
	}
}
