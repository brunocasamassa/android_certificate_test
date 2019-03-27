package com.example.brunogerassicasamassa.testroompersistence.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.brunogerassicasamassa.testroompersistence.activities.UserListActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private UserDAO userDAO;
    private CustomAsyncTask task;
    private Context context;

    public UserModel(UserDAO userDAO, Context context) {
        this.userDAO = userDAO;
        this.context = context;

    }


    public LiveData<List<User>> getUsers() {
        if (users == null) return new MutableLiveData<List<User>>();

        return users;
    }


    public void setOperation(int i, User user) {
        task = new CustomAsyncTask(context, userDAO);
        task.setOperation(i);
        task.setUser(user);
        task.execute();

    }


    class CustomAsyncTask extends AsyncTask<Void, Void, User> {

        private Context context;
        private UserDAO userDAO;
        private User user;

        public void setOperation(int operation) {
            this.operation = operation;
        }

        private int operation = -1;
        private List<User> arrayUsers;

        public CustomAsyncTask(Context applicationContext, UserDAO userDAOparam) {
            this.context = applicationContext;
            this.userDAO = userDAOparam;
        }


        private void listUser() {
            this.arrayUsers = userDAO.getAllUsers();
        }


        private void saveUser() {

            if (user == null) return;

            try {
                user.setId(generateID());
                userDAO.insert(user);

                Toast.makeText(context, userDAO.getUserByID(user.getId()).getName() + "adicionado", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        private int generateID() {
            List<User> arrayUsers = userDAO.getAllUsers();
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

            try {
                User nuser = userDAO.getUserByID(user.getId());
                userDAO.delete(nuser);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Verifique o ID digitado", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected User doInBackground(Void... params) {

            switch (operation) {
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

            if (user == null) return new User();

            return user;
        }

        private void cleanAll() {
            userDAO.deleteAll();
        }


        @Override
        protected void onPostExecute(User user) {

            switch (operation) {
                case 0:
                    Toast.makeText(context, String.valueOf(user.getId())
                            , Toast.LENGTH_SHORT).show();
                    break;
                case 2:

                    Intent intent = new Intent(context, UserListActivity.class);
                    intent.putParcelableArrayListExtra("userlist", (ArrayList<? extends Parcelable>) arrayUsers);
                    context.startActivity(intent);


                    break;
                case 1:
                    Toast.makeText(context, "deletado", Toast.LENGTH_SHORT).show();

            }


        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}