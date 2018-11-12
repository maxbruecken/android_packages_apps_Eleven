package org.lineageos.eleven.model.tag;

import android.content.Context;
import android.provider.MediaStore;

public class GenreTag extends MetadataTagWithContentResolverIntegration {

    public GenreTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.GENRE, context, MediaStore.Audio.GenresColumns.NAME);
    }
}
