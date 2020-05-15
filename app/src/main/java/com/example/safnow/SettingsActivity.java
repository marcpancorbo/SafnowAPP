package com.example.safnow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Fragment {

    private RecyclerView rv;

    public SettingsActivity() {
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
        View root = inflater.inflate(R.layout.activity_settings, container, false);
        this.rv = root.findViewById(R.id.recyclerViewSettings);
        List<Option> options = new ArrayList<>();
        Option option = new Option();
        option.setName("Temporizador");
        option.setDescription("Personaliza el tiempo entre notificacion");
        option.setIcon(R.drawable.temporizador);
        options.add(option);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new SettingAdapter(options));
        return root;
    }

    class SettingAdapter extends RecyclerView.Adapter<SettingsActivity.SettingAdapter.ViewHolder> {
        private List<Option> options;

        public SettingAdapter(List<Option> options) {
            this.options = options;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView icon;
            private TextView textName;
            private TextView textDescription;

            ViewHolder(View itemView) {
                super(itemView);
                this.icon = itemView.findViewById(R.id.IVicon);
                this.textName = itemView.findViewById(R.id.textName);
                this.textDescription = itemView.findViewById(R.id.textDescription);
                this.textDescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openTimeSelector();
                    }
                });
                this.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openTimeSelector();
                    }
                });
                this.textName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openTimeSelector();
                    }
                });
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_setting, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Option option = options.get(position);
            holder.textName.setText(option.getName());
            //TODO Poder guardar un icono en el objeto option para poder rellenar dinamicamente el imageview
            holder.textDescription.setText(option.getDescription());
        }

        @Override
        public int getItemCount() {
            return options.size();
        }

    }

    private void openTimeSelector() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Set personalized layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.time_picker, null));

        builder.setMessage("Selecciona el tiempo: ")
                .setTitle("Timer");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("administrador", "OK!");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private static class Option {

        private String description;
        private String name;
        private int icon;


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }


}
