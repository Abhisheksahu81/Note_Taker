package com.abhi.notetaker;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder>{

    Context context;
    RealmResults<Notes> noteslist;

    public Adapter(Context context, RealmResults<Notes> noteslist) {
        this.context = context;
        this.noteslist = noteslist;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Notes notes = noteslist.get(position);
        holder.title_output.setText(notes.getTitle());
        holder.description_output.setText(notes.getDescription());

        String formattedtime = DateFormat.getDateTimeInstance().format(notes.createdTime);
        holder.time_output.setText(formattedtime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu menu  = new PopupMenu(context,view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("DELETE"))
                        {
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            notes.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Note Deleted ",Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView title_output;
        TextView description_output;
        TextView time_output;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title_output =        itemView.findViewById(R.id.title_output);
            description_output  = itemView.findViewById(R.id.description_output);
            time_output =         itemView.findViewById(R.id.time_output);
        }
    }
}
