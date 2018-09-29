package com.example.vitaly.gb_android_popular_libraries.ui.convertimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.presenter.ConvertImagePresenter;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManager;
import com.example.vitaly.gb_android_popular_libraries.util.FileConverterManagerImpl;
import com.example.vitaly.gb_android_popular_libraries.util.SchedulersProviderImpl;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConvertImageActivity extends MvpAppCompatActivity implements ConvertImageView {

    private static final int PICK_IMAGE = 1;
    private FileConverterManager converter;
    private AlertDialog alertDialog;

    @BindView(R.id.tv_convert_image) TextView convertTextView;
    @BindView(R.id.fab_convert_image) FloatingActionButton fab;

    @InjectPresenter
    ConvertImagePresenter presenter;

    @ProvidePresenter
    public ConvertImagePresenter provideMainPresenter() {
        converter = new FileConverterManagerImpl(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        return new ConvertImagePresenter(new SchedulersProviderImpl(), converter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_image);
        ButterKnife.bind(ConvertImageActivity.this);
    }

    @OnClick(R.id.fab_convert_image)
    public void onClickConvertBtn() {
        presenter.chooseImageClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) {
                return;
            }
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
    public void setImageListOnView(String imageList) {
        convertTextView.setText(imageList);
    }

    @Override
    public void showProgressDialog() {
        final ProgressBar progressBar = new ProgressBar(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.convert_dialog_title)
                .setView(progressBar)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    presenter.cancelFileConversion();
                    dialog.dismiss();
                });
        alertDialog = builder.show();
    }

    @Override
    public void closeProgressDialog() {
        if (alertDialog == null) {
            return;
        }
        alertDialog.dismiss();
        alertDialog = null;
    }
}
