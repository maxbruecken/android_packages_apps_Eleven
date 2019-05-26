package org.lineageos.eleven.adapters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentUris;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.lineageos.eleven.R;
import org.lineageos.eleven.model.tag.AlbumTag;
import org.lineageos.eleven.model.tag.ArtistTag;
import org.lineageos.eleven.model.tag.AudioFileWithMetadata;
import org.lineageos.eleven.model.tag.CommentTag;
import org.lineageos.eleven.model.tag.GenreTag;
import org.lineageos.eleven.model.tag.LabelledMetadataTag;
import org.lineageos.eleven.model.tag.MetadataException;
import org.lineageos.eleven.model.tag.MetadataTag;
import org.lineageos.eleven.model.tag.TitleTag;
import org.lineageos.eleven.model.tag.TrackTag;
import org.lineageos.eleven.model.tag.YearTag;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class TagAdapter extends ArrayAdapter<LabelledMetadataTag> {

    private AudioFileWithMetadata audioFile;
    private Activity context;

    public TagAdapter(Activity context, long songId) {
        super(context, 0);
        
        this.context = context;
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
		this.audioFile = new AudioFileWithMetadata(context, uri);
        new ReadData().execute(audioFile);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    public void setContent(final int position, String content) {
        new SaveData().execute(getItem(position), content);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = context.getLayoutInflater().inflate(R.layout.list_item_tag, parent, false);
        } else {
            v = convertView;
        }

        WeakReference<TextView> label = new WeakReference<TextView>(v.findViewById(R.id.tag_label));
        WeakReference<TextView> content = new WeakReference<TextView>(v.findViewById(R.id.tag_content));

        LabelledMetadataTag tag = getItem(position);

        if (tag == null) {
            return v;
        }

        label.get().setText(tag.getLabel());
        content.get().setText(tag.getContent());

        return v;
    }

    @Override
    public boolean isEmpty() {
        return audioFile == null;
    }

    private class ReadData extends AsyncTask<AudioFileWithMetadata, Void, ArrayList<LabelledMetadataTag>>
    {
        @Override
        protected ArrayList<LabelledMetadataTag> doInBackground(AudioFileWithMetadata... audioFiles) {
            ArrayList<LabelledMetadataTag> data = new ArrayList<>();
            addTag(data, context.getString(R.string.metadata_tag_title), new TitleTag(context, audioFile));
            addTag(data, context.getString(R.string.metadata_tag_artist), new ArtistTag(context, audioFile));
            addTag(data, context.getString(R.string.metadata_tag_album), new AlbumTag(context, audioFile));
            addTag(data, context.getString(R.string.metadata_tag_genre), new GenreTag(context, audioFile));
            addTag(data, context.getString(R.string.metadata_tag_year), new YearTag(context, audioFile));
            addTag(data, context.getString(R.string.metadata_tag_track), new TrackTag(context, audioFile));
            addTag(data, context.getString(R.string.metadata_tag_comment), new CommentTag(context, audioFile));
            return data;
        }

        private void addTag(ArrayList<LabelledMetadataTag> data, String label, MetadataTag underlyingTag) {
            try {
                LabelledMetadataTag tag = new LabelledMetadataTag(label,
                        underlyingTag);
                data.add(tag);
            }
            catch (MetadataException ex) {
            }
        }

        @Override
        protected void onPostExecute(ArrayList<LabelledMetadataTag> tags) {
            super.onPostExecute(tags);
            TagAdapter.this.clear();
            TagAdapter.this.addAll(tags);
            TagAdapter.this.notifyDataSetChanged();
        }
    }

    private class SaveData extends AsyncTask<Object, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Object... params) {
            try
            {
                ((LabelledMetadataTag)params[0]).setContent((String)params[1]);
                return true;
            }
            catch (MetadataException ex)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result)
            {
                TagAdapter.this.notifyDataSetChanged();
            }
            else
            {
                makeText(context, R.string.cannot_write_metadata, LENGTH_SHORT).show();
            }
        }
    }
}
