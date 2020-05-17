package com.example.safnow;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safnow.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Contact extends Fragment {
    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private RecyclerView rv;

    public Contact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_contact, container, false);
        if (this.getContext().getApplicationContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
        this.rv = root.findViewById(R.id.recyclerView);
        List<User> users = GetAllContacts().stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new ContactAdapter(users));
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<User> GetAllContacts() {
        ContentResolver cr = this.getContext().getContentResolver();
        List<User> users = new ArrayList<>();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                while (cursor.moveToNext()) {
                    User user = new User();
                    user.setName(cursor.getString(nameIndex));
                    user.setPhoneNumber(cursor.getString(numberIndex).replaceAll("\\s+", ""));
                    if (users.stream().noneMatch(user1 -> user1.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber()))) {
                        users.add(user);
                    }
                }

            } finally {
                cursor.close();
            }
        }
        return users;
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
        private List<User> userList;

        public ContactAdapter(List<User> userList) {
            this.userList = userList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView userName;
            private TextView phoneNumber;
            private ImageButton starButton;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.userName = itemView.findViewById(R.id.first_text_view);
                this.phoneNumber = itemView.findViewById(R.id.second_text_view);
                this.starButton = itemView.findViewById(R.id.starButton);

            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            User user = userList.get(position);
            PreferencesController preferencesController = PreferencesController.getInstance();
            Set<String> contactsFavShared = preferencesController.getContactsFavorite(Objects.requireNonNull(getContext()));
            if (!contactsFavShared.isEmpty()) {
                for (User u : userList) {
                    if (contactsFavShared.contains(u.getPhoneNumber())) {
                        if (u.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber())) {
                            user.setFavorite(true);
                        }
                    }
                }
            }else{
                userList.get(0).setFavorite(true);
                Toast toast = Toast.makeText(Objects.requireNonNull(getContext()),"Se ha marcado de forma predetermina el primer contacto como favorito",Toast.LENGTH_LONG);
                toast.show();
            }

            if (user.getFavorite()) {
                holder.starButton.setImageResource(R.drawable.star_icon);
            } else {
                holder.starButton.setImageResource(R.drawable.star_icon_sin_fondo);
            }
            holder.userName.setText(user.getName() == null ? "Undefined" : user.getName());
            holder.phoneNumber.setText(user.getPhoneNumber());
            holder.starButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getFavorite()) {
                        if (contactsFavShared.size() == 1) {
                            Toast toast = Toast.makeText(Objects.requireNonNull(getContext()), "Has de tener mínimo un contacto marcado", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            holder.starButton.setImageResource(R.drawable.star_icon_sin_fondo);
                            user.setFavorite(false);
                            preferencesController.deleteContactFav(Objects.requireNonNull(getContext()), user.getPhoneNumber());
                        }
                    } else {
                        holder.starButton.setImageResource(R.drawable.star_icon);
                        user.setFavorite(true);
                        preferencesController.setContactFavorite(Objects.requireNonNull(getContext()), user.getPhoneNumber());
                    }
                }
            });


        }


        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

}
