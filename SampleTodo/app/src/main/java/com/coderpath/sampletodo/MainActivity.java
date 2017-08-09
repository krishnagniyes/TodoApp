package com.coderpath.sampletodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 200;
    public int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);

        items = new ArrayList<>();

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();

        // TODO - Add Button should be enabled iff textfield is not empty.
    }

    private void setupListViewListener() {

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO - Show alert to tell user you are going to remove selected item.
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("selectedItem", items.get(position));
                pos = position;

                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    public  void onAddItem(View v) {

        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        // Don't add blank string.
        // TODO - Add alert to tell user that you can not add blank string.
        if (itemText.isEmpty() == false ) {
            items.add(itemText);
        }

        writeItems();
        etNewItem.setText("");
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch(IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // Extract name value from result extras
            String updatedItem = data.getExtras().getString("updatedItem") + this.pos;

            // update item at selected position.
            items.set(pos, updatedItem);
            // notify data set changed.
            itemsAdapter.notifyDataSetChanged();
        }
    }
}
