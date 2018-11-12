package org.lineageos.eleven.model.tag;

import android.content.Context;
import android.provider.MediaStore;

public class AlbumTag extends MetadataTagWithContentResolverIntegration {

    public AlbumTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.ALBUM, context, MediaStore.Audio.AudioColumns.ALBUM);
    }
}
