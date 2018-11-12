package org.lineageos.eleven.model.tag;

import android.content.Context;
import android.provider.MediaStore;

import org.lineageos.eleven.model.tag.MetadataTag;

public class TrackTag extends MetadataTag {

    public TrackTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.TRACK);
    }
}
