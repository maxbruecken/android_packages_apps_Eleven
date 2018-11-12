package org.lineageos.eleven.model.tag;

import android.content.Context;
import android.provider.MediaStore;

public class ArtistTag extends MetadataTagWithContentResolverIntegration {

    public ArtistTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.ARTIST, context, MediaStore.Audio.AudioColumns.ARTIST);
    }
}
