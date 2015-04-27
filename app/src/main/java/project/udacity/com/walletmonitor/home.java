package project.udacity.com.walletmonitor;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class home extends Fragment implements LoaderCallbacks<Cursor> {

    static  ListView myList;
    private boolean mTwoPane;
    private SimpleCursorAdapter adapt;

    View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home, container, false);

        myList = (ListView) rootView.findViewById(R.id.listView);

        final String[] projection = {myCustomProvider.ITEM_ID, myCustomProvider.ITEM, myCustomProvider.DESC,
                myCustomProvider.PRICE, myCustomProvider.DT};

        final String[] uiBindFrom = {myCustomProvider.ITEM, myCustomProvider.DESC, myCustomProvider.PRICE, myCustomProvider.DT};

        final int[] uiBindTo = {R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView10};

        Cursor cursor = getActivity().getContentResolver().query(myCustomProvider.CONTENT_URI, projection,
                null, null, null);

        adapt = new SimpleCursorAdapter(
                getActivity(), R.layout.list_item,
                cursor, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        myList.setAdapter(adapt);

        if (rootView.findViewById(R.id.textView16) != null) {
            mTwoPane = true;
                         FragmentManager fragmentManager = getFragmentManager();
                         fragmentManager.beginTransaction().replace(R.id.descript, new Full_Description_Fragment()).commit();

        }
        else mTwoPane = false;

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setSelected(true);
                String URL = myCustomProvider.URL;
                Uri item = Uri.parse(URL);
                Cursor c = getActivity().managedQuery(item, null, null, null, "_id");
                int count = 0;
                String desc="";
                if (c.moveToFirst()) {
                    do{
                        if (count==position) {
                            desc = c.getString(c.getColumnIndex(myCustomProvider.DESC));
                            break;
                        }
                        count++;
                    } while (c.moveToNext());
                }

                if (mTwoPane) Full_Description_Fragment.tv.setText(desc);
                else
                {
                    Intent i = new Intent(getActivity(),Detail_Item_Activity.class);
                    i.putExtra("desc",desc);
                    startActivity(i);
                }

            }
        });

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();


        final String[] projection = {myCustomProvider.ITEM_ID, myCustomProvider.ITEM, myCustomProvider.DESC,
                myCustomProvider.PRICE, myCustomProvider.DT};

        final String[] uiBindFrom = {myCustomProvider.ITEM, myCustomProvider.DESC, myCustomProvider.PRICE, myCustomProvider.DT};

        final int[] uiBindTo = {R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView10};

        Cursor cursor = getActivity().getContentResolver().query(myCustomProvider.CONTENT_URI, projection,
                null, null, null);


        adapt = new SimpleCursorAdapter(
                getActivity(), R.layout.list_item,
                cursor, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        myList.setAdapter(adapt);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {myCustomProvider.ITEM_ID, myCustomProvider.ITEM, myCustomProvider.DESC,
                myCustomProvider.PRICE};

        return new CursorLoader(getActivity(),
                myCustomProvider.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapt.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapt.swapCursor(null);
    }

}

