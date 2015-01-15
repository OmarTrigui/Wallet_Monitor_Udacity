package project.udacity.com.walletmonitor;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Statistics_Fragment extends Fragment {

    View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.statistics_view, container, false);

            String URL = myCustomProvider.URL;
            Uri item = Uri.parse(URL);
            Cursor c = getActivity().managedQuery(item, null, null, null, "_id");
            int paid = 0;
            int checkFirstDate = 0;

            if (c.moveToFirst()) {
                do{
                    if (checkFirstDate==0) {checkFirstDate=1;
                        ((TextView) rootView.findViewById(R.id.textView13)).setText(c.getString(c.getColumnIndex(myCustomProvider.DT)));
                    }

                    try {
                        paid += Integer.parseInt(c.getString(c.getColumnIndex(myCustomProvider.PRICE)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                } while (c.moveToNext());
            }



       ((TextView) rootView.findViewById(R.id.textView11)).setText(Integer.toString(paid));

        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        ((TextView) rootView.findViewById(R.id.textView15)).setText(date);
        ((TextView) rootView.findViewById(R.id.textView13)).setText(date);

        return rootView;
    }
}