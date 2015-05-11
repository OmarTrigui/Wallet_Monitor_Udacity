package project.udacity.com.walletmonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by omar on 1/14/15.
 */

public class Full_Description_Fragment extends Fragment {

    private View rootView;
    static TextView tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_full_description, container, false);

        tv = (TextView) rootView.findViewById(R.id.textView16);
        return rootView;
    }
}
