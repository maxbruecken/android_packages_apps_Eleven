package org.lineageos.eleven.model.tag;

import android.content.Context;
import android.provider.MediaStore;

public class TitleTag extends MetadataTagWithContentResolverIntegration {

    public TitleTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.TITLE, context, MediaStore.MediaColumns.TITLE);
    }
}
