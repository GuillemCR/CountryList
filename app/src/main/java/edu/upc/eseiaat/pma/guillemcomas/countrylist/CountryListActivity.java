package edu.upc.eseiaat.pma.guillemcomas.countrylist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    private ArrayList<String> country_list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        String[] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries));                                   //conversio de array a list

        final ListView list = (ListView) findViewById(R.id.country_list);

        //Adapter s'encarrega de fer possible el fet de lliscar i fer correr la llista amb el dit.
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, country_list);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, final int pos, long id) {
                String msg_item = getResources().getString(R.string.msg_item);
                Toast.makeText(
                        CountryListActivity.this,
                        String.format(msg_item + country_list.get(pos)),
                        Toast.LENGTH_SHORT).show();

                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                        builder.setTitle(R.string.confirmation);
                        String message = getResources().getString(R.string.confirm_message);
                        builder.setMessage(message + country_list.get(pos) + "?");

                        builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                country_list.remove(pos);                                           //no refresca fins reciclar
                                adapter.notifyDataSetChanged();                                     //refresca pel remove
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, null);
                        builder.create().show();
                        return true;                                                                //sense true es pensa que és un clcik
                    }
                });



            }
        });

    }
}
