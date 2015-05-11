package project.udacity.com.walletmonitor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class Main_Activity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    DrawerLayout mDrawerLayout;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                getSupportActionBar().setTitle("Home");
                fragment = new home();
                break;

            case 1:
                getSupportActionBar().setTitle("My Statistics");
                fragment = new Statistics_Fragment();
                break;

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        Intent intent;

        switch (id) {
            case R.id.action_reset:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build
                        .setTitle("Reset Wallet ??")
                        .setMessage("Are you sure?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getContentResolver().delete(myCustomProvider.CONTENT_URI, null, null);
                                Toast.makeText(getApplicationContext(), "Your Wallet has been reset, pull down the list to refresh", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;

            case R.id.action_new:
                intent = new Intent(this, Add_Item_Activity.class);
                startActivity(intent);
                break;

            case R.id.action_feed:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"omar.trigui.tn@ieee.org"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "UDACITY APP FEEDBACK");
                intent.putExtra(Intent.EXTRA_TEXT, "Welcome,\n");
                startActivity(Intent.createChooser(intent, "Send Email"));

                break;

            case R.id.action_about:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About Me");
                builder.setIcon(R.drawable.ic_launcher);
                builder.setMessage("That's the udacity application v1.0\ndeveloped by Omar Trigui\nFrom Tunisia with Love");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.show();
                TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
                dialog.show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

}