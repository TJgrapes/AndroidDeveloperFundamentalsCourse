package com.example.mediumarticletextandscrollingviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView article_text = findViewById(R.id.article);

        // Register the article textView to have a context menu
        registerForContextMenu(article_text);
    }

    // Inflate the context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    // Implement context menu item click behavior
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.context_menu_edit:
                Toast.makeText(this,"Edit" ,Toast.LENGTH_SHORT ).show();
                return true;

            case R.id.context_menu_share:
                Toast.makeText(this,"Share" ,Toast.LENGTH_SHORT ).show();
                return true;

            case R.id.context_menu_delete:
                Toast.makeText(this,"Delete" ,Toast.LENGTH_SHORT ).show();

        }

        return super.onContextItemSelected(item);
    }
}
