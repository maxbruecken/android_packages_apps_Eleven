package org.lineageos.eleven.model.tag;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class AudioFileWithMetadata {
	
	private final Context context;
	private final Uri fileUri;
	private AudioFile audioFile;

	public AudioFileWithMetadata(final Context context, Uri fileUri) {
		this.context = context;
		this.fileUri = fileUri;
	}
	
	public String readField(FieldKey key) throws MetadataException {
		Tag tag = getAudioFile().getTagOrCreateAndSetDefault();
		return tag.getFirst(org.jaudiotagger.tag.FieldKey.valueOf(key.getName()));
	}
	
	public void writeField(FieldKey key, String content) throws MetadataException {
		try {
			getAudioFile().getTag().setField(org.jaudiotagger.tag.FieldKey.valueOf(key.getName()), content);
			getAudioFile().commit();
		} catch (KeyNotFoundException e) {
			throw new MetadataException(e);
		} catch (FieldDataInvalidException e) {
			throw new MetadataException(e);
		} catch (CannotWriteException e) {
			throw new MetadataException(e);
		}
	}
	
	public Uri getFileUri() {
		return fileUri;
	}

	private AudioFile getAudioFile() throws MetadataException {
		if (audioFile == null) {
			read();
		}
		return audioFile;
	}
	
	private void read() throws MetadataException {
		try {
			audioFile = AudioFileIO.read(new File(getFilePathByUri(fileUri)));
		} catch (IOException e) {
			throw new MetadataException(e);
		} catch (TagException e) {
			throw new MetadataException(e);
		} catch (ReadOnlyFileException e) {
			throw new MetadataException(e);
		} catch (InvalidAudioFrameException e) {
			throw new MetadataException(e);
		} catch (CannotReadException e) {
			throw new MetadataException(e);
		}
	}
	
	private String getFilePathByUri(Uri uri)
    {
        Uri filePathUri = uri;
        String scheme = filePathUri.getScheme();
        if (scheme == null) scheme = "";
        if (scheme.compareTo("content") == 0)
        {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
				if (cursor.moveToFirst())
				{
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					filePathUri = Uri.parse(cursor.getString(column_index));
					return filePathUri.getPath();
				}
			}
			finally {
				cursor.close();
			}
        }
        else if (scheme.compareTo("file") == 0)
        {
            return filePathUri.getPath();
        }
        else
        {
            return filePathUri.getPath();
        }
        throw new RuntimeException("Invalid uri or unknown uri scheme.");
    }

}
