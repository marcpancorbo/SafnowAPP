package com.example.safnow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Fragment {

    private RecyclerView rv;
    private final String TEMPORIZADOR = "Temporizador";

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
        option.setName(TEMPORIZADOR);
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
                        int position = getAdapterPosition();
                        switch (options.get(position).getName()) {
                            case TEMPORIZADOR:
                                openTimeSelector();
                                break;
                            default:
                                break;
                        }
                    }
                });
                this.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        switch (options.get(position).getName()) {
                            case TEMPORIZADOR:
                                openTimeSelector();
                                break;
                            default:
                                break;
                        }
                    }
                });
                this.textName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        switch (options.get(position).getName()) {
                            case TEMPORIZADOR:
                                openTimeSelector();
                                break;
                            default:
                                break;
                        }
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
        final PreferencesController controller = PreferencesController.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Set personalized layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.time_picker, null);
        builder.setView(view);

        final Spinner spinner = view.findViewById(R.id.timeSpinner);
        //Fill the spinner with data
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("5");
        arrayList.add("10");
        arrayList.add("20");
        arrayList.add("30");
        arrayList.add("60");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        int timePreferences = controller.getTimeNotification(getContext());
        boolean notificationActive = controller.getTimerNotificationActive(getContext());

        if (timePreferences != -1) {
            spinner.setSelection(arrayList.indexOf(String.valueOf(timePreferences)));
        }

        final Switch timeSwitch = view.findViewById(R.id.timeSwitch);
        timeSwitch.setChecked(notificationActive);


        builder.setMessage("Selecciona el tiempo: ")
                .setTitle("Timer");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                configureTimer(spinner.getSelectedItem().toString(), timeSwitch.isChecked());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method to configure notification timer
     *
     * @param spinnerItem       Receive the string which contains the time between notifications
     * @param timeSwitchBoolean Receive the boolean which controls if timer notifications is activated
     */
    private void configureTimer(String spinnerItem, boolean timeSwitchBoolean) {
        PreferencesController controller = PreferencesController.getInstance();
        controller.setTimerNotification(getContext(), Integer.parseInt(spinnerItem));
        controller.setTimerNotificationActive(getContext(), timeSwitchBoolean);
        if (timeSwitchBoolean) {
            long time = Integer.parseInt(spinnerItem) * 60000; //Convert to miliseconds
            AskNotificationTimer askNotificationTimer = AskNotificationTimer.getInstance(getActivity(), time);
            Toast.makeText(getContext(), "Has activado el timer", Toast.LENGTH_SHORT).show();
        } else {
            AskNotificationTimer askNotificationTimer = AskNotificationTimer.getInstance();
            if (askNotificationTimer != null) {
                Toast.makeText(getContext(), "Has cancelado el timer", Toast.LENGTH_SHORT).show();
                askNotificationTimer.cancelCheckTime();
                askNotificationTimer.cancelNotification();
            }
        }
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
