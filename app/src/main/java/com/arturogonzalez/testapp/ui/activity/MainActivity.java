package com.arturogonzalez.testapp.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arturogonzalez.testapp.R;
import com.arturogonzalez.testapp.data.EmployeeController;
import com.arturogonzalez.testapp.model.Employee;
import com.arturogonzalez.testapp.ui.fragment.EmployeeListFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ArrayList<Employee> employees;

    private static final int CAMERA = 1;
    private static final int FILE = 2;
    private ImageView imageEmployee;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        employees = bundle.getParcelableArrayList(getString(R.string.employee_list_bundle));
        String employeeSelected = bundle.getString(getString(R.string.employee_id_bundle));
        configureDrawer(employeeSelected);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_gallery);
        Fragment fragment = new EmployeeListFragment();
        fragment.setArguments(bundle);
        showFragment(fragment,true);
    }

    private void configureDrawer(String employeeSelected) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( this ) ;
        drawer = findViewById(R.id. drawer_layout ) ;
        View hView =  navigationView.getHeaderView(0);
        TextView nameTxt = hView.findViewById(R.id.name_employee_drawer);
        TextView idTxt = hView.findViewById(R.id.id_employee_drawer);
        Employee employee = employees.get(EmployeeController.positionOfEmployee(employeeSelected,employees));
        imageEmployee = hView.findViewById(R.id.imageView_drawer);
        nameTxt.setText(employee.getName());
        idTxt.setText(employee.getId());
        imageEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
            }
        });
    }

    public void changePic(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }else {
            final CharSequence[] items = {"Camera", "Library", "Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.select_picture);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA);
                    } else if (items[item].equals("Library")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, FILE);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
            drawer.closeDrawer(GravityCompat. START ) ;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id. nav_settings ) {
            changePic();
        }
        drawer.closeDrawer(GravityCompat. START ) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings ) {
            changePic();
        }
        drawer.closeDrawer(GravityCompat. START ) ;
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case CAMERA:
                if(resultCode == RESULT_OK){
                    Bitmap bmp = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                            byteArray.length);
                    imageEmployee.setImageBitmap(bitmap);
                }
                break;
            case FILE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageEmployee.setImageURI(selectedImage);
                }
                break;
        }
    }

    public void showFragment(Fragment fragment, boolean addToStack){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_content, fragment);

        if (addToStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}