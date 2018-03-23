package android.lalo.com.youtubeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, TextView.OnEditorActionListener {

    private YouTubePlayerView youTubePlayerView;
    private String key = "AIzaSyBS-P2nKbX8ktjhD1HGIew6CVYfX1x7jGA";
    private String uri = "K4wEI5zhHB0";
    private EditText editText;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> uris = new ArrayList<String>();
        uris.add(uri);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.you);
        editText = findViewById(R.id.editText);
        editText.setText(null);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setHint("YouTube ID");
        editText.setOnEditorActionListener(this);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, uris);
        listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playVideo(arrayAdapter.getItem(i));
            }
        });
        youTubePlayerView.initialize(key, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Toast.makeText(this, "Disfruta tu video", Toast.LENGTH_LONG).show();
        this.youTubePlayer = youTubePlayer;
        if (!b) {
            youTubePlayer.cueVideo(arrayAdapter.getItem(arrayAdapter.getCount() - 1));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "YouTube Nos Ha Fallado", Toast.LENGTH_LONG).show();
        this.youTubePlayer = null;
    }

    public void update(View view) {
        if (youTubePlayer != null) {
            arrayAdapter.add(editText.getText().toString());
            arrayAdapter.notifyDataSetChanged();
            editText.setText(null);
            playVideo(arrayAdapter.getItem(arrayAdapter.getCount() - 1));
        } else {
            Toast.makeText(this, "YouTube Nos Ha Fallado", Toast.LENGTH_LONG).show();
        }
    }

    public void playVideo(String url) {
        youTubePlayer.cueVideo(url);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_GO) {
            update(textView);
            return true;
        }
        return false;
    }
}
