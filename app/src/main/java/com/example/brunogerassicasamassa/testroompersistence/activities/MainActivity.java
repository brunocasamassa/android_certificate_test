package com.example.brunogerassicasamassa.testroompersistence.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.model.User;
import com.example.brunogerassicasamassa.testroompersistence.model.UserDAO;
import com.example.brunogerassicasamassa.testroompersistence.utils.Permissao;
import com.example.brunogerassicasamassa.testroompersistence.utils.Database;
import com.example.brunogerassicasamassa.testroompersistence.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editAge;
    private EditText editMail;

    private Button saveButton;
    private Button deleteButton;
    private Button listButton;
    private Button songButton;
    private Button deleteAll;
    private UserDAO userDatabase;
    private CustomAsyncTask customAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDatabase = Database.getInstance(MainActivity.this).createUserDAO();

        editName = findViewById(R.id.name);
        editAge = findViewById(R.id.age);
        editMail = findViewById(R.id.mail);
        deleteAll = findViewById(R.id.delete_all);

        saveButton = findViewById(R.id.save);
        deleteButton = findViewById(R.id.delete);
        listButton = findViewById(R.id.list);
        songButton = findViewById(R.id.song);

        saveButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        songButton.setOnClickListener(this);
        deleteAll.setOnClickListener(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            String[] permissoes = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
            if ((Permissao.validaPermissoes(1, MainActivity.this, permissoes))) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                double longitude = location.getLongitude();
                double latitude = location.getLatitude();


                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.putCoordinate("latitude", latitude);
                sessionManager.putCoordinate("longitude", longitude);


            }

        }


    }

    @Override
    public void onClick(final View v) {

        User user = new User();
        populate(user);

        switch (v.getId()) {

            case R.id.save:
                customAsyncTask = new CustomAsyncTask(getApplicationContext(), userDatabase, 0, user);
                customAsyncTask.execute();
                break;
            case R.id.delete:
                showDeleteMessage(user);
                break;
            case R.id.list:
                customAsyncTask = new CustomAsyncTask(getApplicationContext(), userDatabase, 2, user);
                customAsyncTask.execute();
                break;
            case R.id.delete_all:
                customAsyncTask = new CustomAsyncTask(getApplicationContext(), userDatabase, 3, user);
                customAsyncTask.execute();
                break;
            case R.id.song:
                Intent intent = new Intent(MainActivity.this, SongListActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void populate(User user) {


        if (!TextUtils.isEmpty(editName.getText())) {
            user.setName(editName.getText().toString());

        } else return;

        if (!TextUtils.isEmpty(editMail.getText())) {
            user.setMail(editMail.getText().toString());

        } else return;

        if (!TextUtils.isEmpty(editAge.getText())) {
            if (TextUtils.isDigitsOnly(editAge.getText())) {
                user.setAge(Integer.valueOf(editAge.getText().toString()));
            }

        } else return;
    }

    private void showDeleteMessage(final User user) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Deletar usuario ");
        alertDialog.setMessage("digite o id que deseja excluir:");
        final EditText editText = new EditText(getApplicationContext());
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("DELETAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(editText.getText())) return;

                user.setId(Integer.valueOf(editText.getText().toString()));
                customAsyncTask = new CustomAsyncTask(getApplicationContext(), userDatabase, 1, user);
                customAsyncTask.execute();
            }
        }).create().show();

    }

}


class CustomAsyncTask extends AsyncTask<Void, Void, User> {

    private Context context;
    private UserDAO database;
    private User user;
    private int i = -1;
    private List<User> arrayUsers;

    public CustomAsyncTask(Context applicationContext, UserDAO database, int i, User user) {
        this.context = applicationContext;
        this.database = database;
        this.i = i;
        this.user = user;

    }

    private void listUser() {
        this.arrayUsers = database.getAllUsers();
    }


    private User getUser() {
        return this.user;
    }

    private void saveUser() {

        user.setId(generateID());
        database.insert(user);

    }

    private int generateID() {
        List<User> arrayUsers = database.getAllUsers();
        Random random = new Random();
        int value = random.nextInt(100000);
        Log.e("ID_VALUE_", String.valueOf(value));

        for (User user : arrayUsers) {
            if (user.getId() != 0) {
                if (user.getId() != value) {


                } else {
                    return generateID();
                }

            }
        }
        return value;
    }


    private void deleteUser() {

        User nuser = database.getUserByID(user.getId());
        database.delete(nuser);

    }

    @Override
    protected User doInBackground(Void... params) {

        switch (i) {
            case 0:
                saveUser();
                break;
            case 1:
                deleteUser();
                break;
            case 2:
                listUser();
                break;
            case 3:
                cleanAll();
                break;
        }

        return user;
    }

    private void cleanAll() {
        database.deleteAll();
    }


    @Override
    protected void onPostExecute(User user) {

        switch (i) {
            case 0:
                Toast.makeText(context, String.valueOf(user.getId())
                        , Toast.LENGTH_SHORT).show();
                break;
            case 2:

                    Intent intent =  new Intent(context, UserListActivity.class);
                    intent.putParcelableArrayListExtra("userlist", (ArrayList<? extends Parcelable>) arrayUsers);
                    context.startActivity(intent);


                break;
            case 1:
                Toast.makeText(context, "deletado", Toast.LENGTH_SHORT).show();

        }

    }
}


