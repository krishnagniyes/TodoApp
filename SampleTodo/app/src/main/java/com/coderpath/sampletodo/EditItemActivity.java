package com.coderpath.sampletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import static com.coderpath.sampletodo.R.id.etNewItem;
import static com.coderpath.sampletodo.R.id.lvItems;

public class EditItemActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    Button btnSaveItem = null;
    EditText etUpdateItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String selectedItem = getIntent().getStringExtra("selectedItem");

        // Update Item.
        etUpdateItem = (EditText)findViewById(R.id.etUpdateItem);
        etUpdateItem.setText(selectedItem);

        // Save updated item.
        btnSaveItem = (Button) findViewById(R.id.saveButton);
    }

    public  void onSaveItem(View v) {

        String itemText = etUpdateItem.getText().toString();

        Intent data = new Intent();
        // Pass updated data back as a result.
        data.putExtra("updatedItem",itemText);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        finish();
    }
}
