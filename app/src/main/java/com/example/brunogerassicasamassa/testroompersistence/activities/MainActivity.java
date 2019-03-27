package com.example.brunogerassicasamassa.testroompersistence.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.model.User;
import com.example.brunogerassicasamassa.testroompersistence.model.UserDAO;
import com.example.brunogerassicasamassa.testroompersistence.model.UserModel;
import com.example.brunogerassicasamassa.testroompersistence.utils.Permissao;
import com.example.brunogerassicasamassa.testroompersistence.utils.Database;
import com.example.brunogerassicasamassa.testroompersistence.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editAge;
    private EditText editMail;

    private Button saveButton;
    private Button deleteButton;
    private Button listButton;
    private Button songButton;
    private Button deleteAll;
    private UserDAO userDAO;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDAO = Database.getInstance(MainActivity.this).createUserDAO();
        userModel = new UserModel(userDAO,getApplicationContext());

        findViews();

        persistUserCoordinates();



    }

    private void persistUserCoordinates() {
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

    private void findViews() {
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
    }

    @Override
    public void onClick(final View v) {

        User user = new User();
        populate(user);

        switch (v.getId()) {

            case R.id.save:
                userModel.setOperation(0,user);
                break;
            case R.id.delete:
                showDeleteMessage(user);
                break;
            case R.id.list:
                userModel.setOperation(2,user);

                break;
            case R.id.delete_all:
                userModel.setOperation(3,user);

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

                userModel.setOperation(1,user);

            }
        }).create().show();

    }

}




