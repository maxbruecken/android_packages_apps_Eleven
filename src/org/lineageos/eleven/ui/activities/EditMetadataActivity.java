package org.lineageos.eleven.ui.activities;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.lineageos.eleven.Config;
import org.lineageos.eleven.R;
import org.lineageos.eleven.adapters.TagAdapter;
import org.lineageos.eleven.model.tag.LabelledMetadataTag;
import org.lineageos.eleven.model.tag.MetadataException;
import org.lineageos.eleven.utils.MusicUtils;

/**
 * This class is used to display and edit metadata for a song
 *
 * @author Maxim Becker (maxim.becker@gmx.de)
 */
public class EditMetadataActivity extends Activity implements AdapterView.OnItemClickListener {

    /**
     * Tag list view
     */
    private ListView mListView;

    /**
     * List view adapter
     */
    private TagAdapter mAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();

        setList();

        setTitle(getString(R.string.app_name).toUpperCase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        MusicUtils.refresh();
    }

    private void setView() {
        // Fade it in
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // Set the layout
        setContentView(R.layout.list_base);
    }

    private void setList() {
        // Initialize the list
        mListView = (ListView)findViewById(R.id.list_base);

        mAdapter = new TagAdapter(this, getIntent().getLongExtra(Config.ID, 0));
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        LabelledMetadataTag tag = (LabelledMetadataTag)mAdapter.getItem(position);

        final EditText input = new EditText(this);
        input.setSingleLine(true);
        try {
			input.setText(tag.getContent());
		} catch (MetadataException e) {
			makeText(this, R.string.cannot_read_metadata, LENGTH_SHORT).show();
		}
        input.setInputType(input.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.post(new Runnable() {

            @Override
            public void run() {
                openKeyboard(input);
                input.requestFocus();
                input.selectAll();
            };
        });

        new AlertDialog.Builder(this)
                .setTitle(tag.getLabel())
                .setView(input)
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
							mAdapter.setContent(position, input.getText().toString());
						} catch (MetadataException e) {
							makeText(EditMetadataActivity.this, R.string.cannot_write_metadata, LENGTH_SHORT).show();
						}
                        closeKeyboard(input);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        closeKeyboard(input);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * Opens the soft keyboard
     */
    protected void openKeyboard(EditText input) {
        final InputMethodManager mInputMethodManager = (InputMethodManager)this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.toggleSoftInputFromWindow(input.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Closes the soft keyboard
     */
    protected void closeKeyboard(EditText input) {
        final InputMethodManager mInputMethodManager = (InputMethodManager)this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
}
