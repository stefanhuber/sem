package fhku.responsiveexample;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String mode = "";

    public void loadDetails (View view) {
        Button button = (Button) view;

        if (mode.equals("two_pane")) {
            FragmentManager fm = getSupportFragmentManager();
            DetailFragment df = (DetailFragment) fm.findFragmentById(R.id.fragment2);
            df.changeText((String) button.getText());
        } else {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.hide(fm.findFragmentById(R.id.fragment1));

            DetailFragment df = new DetailFragment();
            df.setText((String) button.getText());
            ft.add(R.id.fragment_layout, df);

            ft.addToBackStack(null);

            ft.commit();
        }
    }

    public static class OverviewFragment extends Fragment {

        public static interface OverviewClickListener {
            public void onClick(String name);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.overview_fragment,container,false);
        }
    }

    public static class DetailFragment extends Fragment {

        private TextView tv;
        private String text = "";

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.detail_fragment,container,false);
            this.tv = (TextView) view.findViewById(R.id.fragment_text);
            this.tv.setText(this.text);
            return view;
        }

        public void changeText(String text) {
            this.tv.setText(text);
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        this.mode = findViewById(R.id.fragment2) != null ? "two_pane" : "one_pane";

    }
}
