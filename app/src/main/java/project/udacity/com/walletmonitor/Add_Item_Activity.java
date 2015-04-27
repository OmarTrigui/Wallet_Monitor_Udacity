package project.udacity.com.walletmonitor;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by omar on 1/13/15.
 */
public class Add_Item_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);
    }

    public void onClickAddName(View view) {

        ContentValues values = new ContentValues();

        String item =  ((EditText)findViewById(R.id.txtItem)).getText().toString().trim();
        values.put(myCustomProvider.ITEM,item);

        String desc =  ((EditText)findViewById(R.id.txtDesc)).getText().toString().trim();
        values.put(myCustomProvider.DESC,desc);

        String price = ((EditText)findViewById(R.id.txtPrice)).getText().toString().trim();
        values.put(myCustomProvider.PRICE,price);


        if (item.length()==0) {Toast.makeText(getApplicationContext(),"Invalid Item",Toast.LENGTH_LONG).show();return;}
        if (desc.length()==0) {Toast.makeText(getApplicationContext(),"Invalid Description",Toast.LENGTH_LONG).show();return;}
        if (price.length()==0) {Toast.makeText(getApplicationContext(),"Invalid Price",Toast.LENGTH_LONG).show();return;}


        values.put(myCustomProvider.DT, new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));


        getContentResolver().insert(
                myCustomProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                "Your item has been successfully added", Toast.LENGTH_LONG).show();
    }

}
