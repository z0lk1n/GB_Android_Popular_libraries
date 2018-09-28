package com.example.vitaly.gb_android_popular_libraries.ui.convertimage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.presenter.ConvertImagePresenter;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManagerImpl;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProviderImpl;

import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConvertImageActivity extends MvpAppCompatActivity implements ConvertImageView {

    private static final int PICK_IMAGE = 1;
    private FileConverterManager converter;
    private File storageDir;

    @BindView(R.id.convert_txt_view) TextView convertTextView;
    @BindView(R.id.convert_btn) Button convertButton;
    @BindView(R.id.convert_img_view) ImageView imageView;

    @InjectPresenter
    ConvertImagePresenter presenter;

    @ProvidePresenter
    public ConvertImagePresenter provideMainPresenter() {
        converter = new FileConverterManagerImpl(storageDir);
        presenter = new ConvertImagePresenter(new SchedulersProviderImpl(), converter);
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);
        ButterKnife.bind(ConvertImageActivity.this);
        storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @OnClick(R.id.convert_btn)
    public void onClickConvertBtn() {
        presenter.chooseImageClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                InputStream stream = getContentResolver().openInputStream(data.getData());
                presenter.sendByteArrayFromRequest(converter.getByteArrayFromStream(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void setImageOnView(String path) {
        imageView.setImageURI(Uri.fromFile(new File(path)));
    }

    @Override
    public void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConvertImageActivity.this)
                .setTitle("Convert image file...")
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    presenter.cancelFileConversion();
                    dialog.dismiss();
                });
        builder.show();
    }
}
