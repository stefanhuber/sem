package fh_ku.personlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    protected List<Person> persons;

    public PersonAdapter() {
        this.persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            persons.add(new Person()
                    .setName("Person " + i)
                    .setEmail("person" + i + "@demo.com"));

        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView email;

        public PersonViewHolder(View itemView) {
            super(itemView);
            name  = (TextView) itemView.findViewById(R.id.person_name);
            email = (TextView) itemView.findViewById(R.id.person_email);
        }
    };

    public void moveItem(int fromPosition, int toPosition) {
        Person from = persons.remove(fromPosition);

        ArrayList<Person> input = new ArrayList<>();
        input.add(from);

        persons.addAll(toPosition,input);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void removeItem(int position) {
        persons.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public PersonAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonAdapter.PersonViewHolder viewHolder, int position) {
        viewHolder.name.setText(persons.get(position).getName());
        viewHolder.email.setText(persons.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return this.persons != null ? this.persons.size() : 0;
    }

}
