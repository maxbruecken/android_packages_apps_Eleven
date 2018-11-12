package org.lineageos.eleven.model.tag;

import android.content.Context;

public class CommentTag extends MetadataTag {

    public CommentTag(Context context, AudioFileWithMetadata audioFile) {
        super(audioFile, FieldKey.COMMENT);
    }
}
