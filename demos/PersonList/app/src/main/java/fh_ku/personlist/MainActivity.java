package fh_ku.personlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.person_list);
        rv.setAdapter(new PersonAdapter());
        rv.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper touchHelper = new ItemTouchHelper(new PersonTouchCallback((PersonAdapter) rv.getAdapter()));
        touchHelper.attachToRecyclerView(rv);
    }
}
