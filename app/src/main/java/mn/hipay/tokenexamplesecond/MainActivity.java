package mn.hipay.tokenexamplesecond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import mn.hipay.tokenlibrary.ToasterMessage;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("TOKEN MAIN", getResources().getString(R.string.hps_entityid));
        ToasterMessage.show(MainActivity.this, "SHOW MESSAGE!");
        findViewById(R.id.cardConfig).setOnClickListener(
            view -> {
                startActivity(new Intent(this, mn.hipay.tokenlibrary.TokenMainActivity.class));
            }
        );
    }
}