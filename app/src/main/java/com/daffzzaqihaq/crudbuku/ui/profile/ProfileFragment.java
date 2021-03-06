package com.daffzzaqihaq.crudbuku.ui.profile;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.daffzzaqihaq.crudbuku.R;
import com.daffzzaqihaq.crudbuku.model.login.LoginData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View{


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    private ProfilePresenter mProfilePresenter = new ProfilePresenter(this);

    private String iduser, name, alamat, noTelp;
    private int gender;

    // Penampung item
    private Menu action;

    private String mGender;
    private static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    private ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Menampilkan option fragment
        setHasOptionsMenu(true);
        // Mensetting spinner
        setupSpinner();
        // ngambil data yg sudah dikerjain sama presenter
        mProfilePresenter.getDataUser(getContext());
        return view;
    }

    private void setupSpinner() {
        // Membuat adapter Spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_options, android.R.layout.simple_spinner_item);
        // Menampilkan spinner 1 line
        genderSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // Memasukan Adapter Spinner ke dalam widget Spinner kita
        spinGender.setAdapter(genderSpinnerAdapter);

        // Listener Spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil posisi item yang dipilih
                String selection = (String) parent.getItemAtPosition(position);
                // Mencek pisisi apakah ada isinya
                if (!TextUtils.isEmpty(selection)){
                    // Mencek apakah 1 atau 2 dipilih user
                    if (selection.equals(getString(R.string.gender_male))){
                        mGender = "L";
                    }else if (selection.equals(getString(R.string.gender_female))){
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Save a new your account info...");
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccessUpdateUser(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        // Mengambil data ulang
        mProfilePresenter.getDataUser(getContext());

    }

    @Override
    public void showDataUser(LoginData loginData) {
        // Memasukan data yg sudah diambil oleh Presenter
        iduser = loginData.getId_user();
        name = loginData.getNama_user();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNo_telp();
        if (loginData.getJenkel().equals("L")){
            gender = 1;

        }else {
            gender = 2;
        }

        if (!TextUtils.isEmpty(iduser)){
            // Mengubah widget agar tidak bisa diedit
            readMode();
            // Menset nama title action bar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Hi, " + name);

            // Menampilkan data ke layar
            edtName.setText(name);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);
            // Mencek gebder dan memilih sesuai gender untuk ditampilkan pada Spinner
            switch (gender){
                case GENDER_MALE:
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }
        }else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Your Self");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        mProfilePresenter.logoutSession(getContext());
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:

                editMode();

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                // Mencek apakah idUser ada isinya
                if (!TextUtils.isEmpty(iduser)){
                    // Mencek apakah semua field ada isinya
                    if (TextUtils.isEmpty(edtName.getText().toString()) ||
                            TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                            TextUtils.isEmpty(edtNoTelp.getText().toString())) {
                        // Menampilkan alert dialog untuk memberitahu user tidak boleh ada field yg kosong
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }else {
                        // Apabila user sudah mengisi semua field
                        LoginData loginData = new LoginData();
                        // Mengisi inputan user ke model logindata
                        loginData.setId_user(iduser);
                        loginData.setNama_user(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNo_telp(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGender);

                        // Mengirim data ke presenter untuk dimasukan ke dalam database
                        mProfilePresenter.updateDataUser(getContext(), loginData);

                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                    }
                }else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void editMode() {
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {
        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);
        edtName.setFocusable(false);
        edtNoTelp.setFocusable(false);
        edtAlamat.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);
    }
}
