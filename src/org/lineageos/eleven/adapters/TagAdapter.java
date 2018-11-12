package org.lineageos.eleven.adapters;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.ContentUris;
import android.database.DataSetObserver;
import android.net.Uri;
import android.provider.MediaStore;
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
import org.lineageos.eleven.model.tag.TitleTag;
import org.lineageos.eleven.model.tag.TrackTag;
import org.lineageos.eleven.model.tag.YearTag;

public class TagAdapter extends ArrayAdapter<LabelledMetadataTag> {

    private static final int TAG_COUNT = 7;
    private Uri uri;
    private AudioFileWithMetadata audioFile;
    private Activity context;
    private LabelledMetadataTag[] data = new LabelledMetadataTag[TAG_COUNT];

    public TagAdapter(Activity context, long songId) {
        super(context, 0);
        
        this.context = context;
        this.uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
		this.audioFile = new AudioFileWithMetadata(context, uri);
		setData();
    }

    private void setData() {
        data[0] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_title), new TitleTag(context, audioFile));
        data[1] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_artist), new ArtistTag(context, audioFile));
        data[2] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_album), new AlbumTag(context, audioFile));
        data[3] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_genre), new GenreTag(context, audioFile));
        data[4] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_year), new YearTag(context, audioFile));
        data[5] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_track), new TrackTag(context, audioFile));
        data[6] = new LabelledMetadataTag(context.getString(R.string.metadata_tag_comment), new CommentTag(context, audioFile));
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return TAG_COUNT;
    }

    @Override
    public LabelledMetadataTag getItem(int position) {
        return data[position];
    }

    public void setContent(final int position, String content) throws MetadataException {
        data[position].setContent(content);

        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = context.getLayoutInflater().inflate(R.layout.list_item_tag, parent, false);
        } else {
            v = convertView;
        }

        WeakReference<TextView> label = new WeakReference<TextView>((TextView)v.findViewById(R.id.tag_label));
        WeakReference<TextView> content = new WeakReference<TextView>((TextView)v.findViewById(R.id.tag_content));

        LabelledMetadataTag tag = getItem(position);

        label.get().setText(tag.getLabel());
        content.get().setText(tag.toString());

        return v;
    }

    @Override
    public boolean isEmpty() {
        return audioFile == null;
    }
}
