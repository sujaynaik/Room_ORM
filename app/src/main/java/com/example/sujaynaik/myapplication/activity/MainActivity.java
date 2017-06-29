package com.example.sujaynaik.myapplication.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sujaynaik.myapplication.R;
import com.example.sujaynaik.myapplication.adapter.UserAdapter;
import com.example.sujaynaik.myapplication.app.MyApplication;
import com.example.sujaynaik.myapplication.interfaces.ListListener;
import com.example.sujaynaik.myapplication.model.User;
import com.example.sujaynaik.myapplication.util.Utils;
import com.example.sujaynaik.myapplication.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();
    private Context context = MainActivity.this;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layoutBottom)
    BottomNavigationView layoutBottom;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bAdd)
    FloatingActionButton bAdd;
    Button bSave;

    private MyApplication myApplication;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    private UserViewModel mViewModel;
    private boolean isDelete = false;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myApplication = (MyApplication) getApplicationContext();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Test");

        mViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                return (T) new UserViewModel(myApplication.getDb().userDao());
            }
        }).get(UserViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        setAdapter();
        getUsers();

        layoutBottom.setOnNavigationItemSelectedListener(this);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Utils.hideCircularReveal(bAdd);
                Utils.createCircularReveal2(layoutBottom);*/
                if (!isDelete) {
                    bottomSheetDialog = new BottomSheetDialog(context);
                    View bottomSheetView = getLayoutInflater().inflate(R.layout.item_user_edit, null);
                    final EditText tvFname = (EditText) bottomSheetView.findViewById(R.id.tvFname);
                    final EditText tvLname = (EditText) bottomSheetView.findViewById(R.id.tvLname);
                    bSave = (Button) bottomSheetView.findViewById(R.id.bSave);

                    bSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String fname = tvFname.getText().toString().trim();
                            String lname = tvLname.getText().toString().trim();

                            if (fname.isEmpty() && lname.isEmpty()) {
                                Utils.toastAlert(context, "Some fields are empty");
                            } else {
                                User user = new User();
                                user.setFirstName(fname);
                                user.setLastName(lname);
                                addUser(user);
                            }
                        }
                    });

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.setCanceledOnTouchOutside(true);
                    if (!bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.show();
                    }

                } else {
                    for (User user : selectedUser) {
                        deleteUser(user);
                    }
                }
            }
        });
    }

    private List<User> selectedUser = new ArrayList<>();

    private void setAdapter() {
        userAdapter = new UserAdapter(context, userList);
        recyclerView.setAdapter(userAdapter);

        userAdapter.setOnListListener(new ListListener<User>() {
            @Override
            public void onItemClick(User user, int position) {

            }

            @Override
            public void onItemLongClick(User user, int position) {

            }

            @Override
            public void onItemSelected(User user, boolean isSelected, int position) {
                if (isSelected) {
                    selectedUser.add(user);
                } else {
                    selectedUser.remove(user);
                }

                if (selectedUser.size() > 0) {
                    bAdd.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
                    isDelete = true;
                } else {
                    bAdd.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));
                    isDelete = false;
                }
            }
        });
    }

    private void addUser(User user) {
        if (bSave != null) {
            bSave.setEnabled(false);
        }
        mDisposable.add(mViewModel.insertUsers(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (bSave != null) {
                            bSave.setEnabled(true);
                            bottomSheetDialog.dismiss();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Utils.loge(TAG, "Unable to insert user : ", throwable);
                    }
                }));
    }

    private void deleteUser(final User user) {
        bAdd.setEnabled(false);
        mDisposable.add(mViewModel.deleteUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        bAdd.setEnabled(true);

                        selectedUser.remove(user);
                        if (selectedUser.size() > 0) {
                            bAdd.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
                            isDelete = true;
                        } else {
                            bAdd.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_input_add));
                            isDelete = false;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Utils.loge(TAG, "Unable to delete user : ", throwable);
                    }
                })
        );
    }

    private void getUsers() {
        mDisposable.add(mViewModel.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<User> users) throws Exception {
                        userList = users;
                        userAdapter.refresh(userList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                        Utils.loge(TAG, "Unable to get users : ", throwable);
                    }
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case 0:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_part1:
                Utils.hideCircularReveal2(layoutBottom);
                bAdd.setVisibility(View.INVISIBLE);
                Utils.createCircularReveal2(bAdd);
                break;

            case R.id.action_part2:
                Utils.hideCircularReveal2(layoutBottom);
                bAdd.setVisibility(View.INVISIBLE);
                Utils.createCircularReveal2(bAdd);
                break;

            case R.id.action_part3:
                Utils.hideCircularReveal2(layoutBottom);
                bAdd.setVisibility(View.INVISIBLE);
                Utils.createCircularReveal2(bAdd);
                break;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    /*private void setDataList(){
        for (int i = 0; i < 100; i++){
            String fname = "First name " + i;
            String lname = "Last name " + i;

            User user = new User();
            user.setFirstName(fname);
            user.setLastName(lname);
            addUser(user);
        }
    }*/
}
