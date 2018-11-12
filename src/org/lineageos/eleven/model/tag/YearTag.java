package org.lineageos.eleven.model.tag;

import android.content.Context;
import android.provider.MediaStore;

public class YearTag extends MetadataTagWithContentResolverIntegration {

    public YearTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.YEAR, context, MediaStore.Audio.AudioColumns.YEAR);
    }
}
