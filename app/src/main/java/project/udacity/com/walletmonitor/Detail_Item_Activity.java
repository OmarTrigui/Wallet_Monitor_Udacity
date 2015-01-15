package project.udacity.com.walletmonitor;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by omar on 1/14/15.
 */
public class Detail_Item_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_full_description);

        Bundle b = getIntent().getExtras();
        String value = b.getString("desc");

        ((TextView) findViewById(R.id.textView16)).setText(value);

    }
}
