package com.example.safnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safnow.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Contact extends Fragment{

    private RecyclerView rv;
    public Contact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_contact, container, false);
        this.rv = root.findViewById(R.id.recyclerView);
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("Test");
        user1.setPhoneNumber("123456");
        User user2 = new User();
        user2.setName("Test3");
        user2.setPhoneNumber("1234567");
        User user3 = new User();
        user3.setName("Test4");
        user3.setPhoneNumber("12345674543");
        User user4 = new User();
        user4.setName("Test3");
        user4.setPhoneNumber("1234567");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new ContactAdapter(users));
        return root;
    }

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{
        private List<User> userList;

        public ContactAdapter(List<User> userList) {
            this.userList = userList;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView userName;
            private TextView phoneNumber;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.userName = itemView.findViewById(R.id.first_text_view);
                this.phoneNumber = itemView.findViewById(R.id.second_text_view);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            User user = userList.get(position);
            holder.userName.setText(user.getName() == null ? "Undefined":user.getName());
            holder.phoneNumber.setText(user.getPhoneNumber());
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

}
