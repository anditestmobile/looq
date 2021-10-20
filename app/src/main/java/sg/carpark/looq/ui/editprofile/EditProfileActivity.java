package sg.carpark.looq.ui.editprofile;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import sg.carpark.looq.R;
import sg.carpark.looq.databinding.ActivityEditProfileBinding;
import sg.carpark.looq.databinding.DialogCheckInSuccessBinding;
import sg.carpark.looq.databinding.DialogConfirmationBinding;
import sg.carpark.looq.ui.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EditProfileActivity extends BaseActivity {

    ActivityEditProfileBinding binding;
    protected Dialog dialog;
    private String name, phone, email, address;
    Object results = new Object();
    private int typePermission = 0;
    HashMap<String, Object> profile = new HashMap<String, Object>();
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    private File photoFile = null;
    private BitmapFactory.Options options;
    Bitmap bitmapCamera, bitmapGallery, bitmap;
    private String imageProfile, photoName;
    private File directory;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.edtName.setText(user.getName());
        binding.edtEmail.setText(user.getEmail());
        binding.edtPhone.setText(user.getPhone());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.btnSave.setOnClickListener(view -> {
            String message = null;
            name = binding.edtName.getText() != null ? binding.edtName.getText().toString() : null;
            phone = binding.edtPhone.getText() != null ? binding.edtPhone.getText().toString() : null;
            email = binding.edtEmail.getText() != null ? binding.edtEmail.getText().toString() : null;
//            address = binding.edtAddress.getText() != null ? binding.edtAddress.getText().toString() : null;

            if (name != null && name.isEmpty()) {
                message = getResources().getString(R.string.edit_profile_message_empty_name);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }else if (phone != null && phone.isEmpty()) {
                message = getResources().getString(R.string.edit_profile_message_empty_phone);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }else if (email != null && email.isEmpty()) {
                message = getResources().getString(R.string.edit_profile_message_empty_email);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                showDialogChangeProfile();
            }

        });
        binding.imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
                    typePermission = 1;
                    askPermission();
                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);
                    typePermission = 2;
                    askPermission();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);
        } else {
            if (typePermission == 1) {
                dispatchTakePictureIntent();
            } else {
                openGallery();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_REQUEST_CODE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.getMessage();
                Toast.makeText(getApplicationContext(), "Couldn't create image file", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            Uri photoURI = FileProvider.getUriForFile(this,
                    "sg.carpark.looq.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,   //prefix
                ".png",   //suffix
                storageDir  //directory
                ///data/user/0/id.co.qualitas.oem/files/storage/emulated/0/Pictures/JPEG_20201125_121251_-1693854433.jpg
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (typePermission == 1) {
                dispatchTakePictureIntent();
            } else {
                openGallery();
            }
        } else {
            Toast.makeText(getApplicationContext(), "This permission(s) required", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
//            File path = new File(getFilesDir(), "your/path");
//            if (!path.exists()) path.mkdirs();
//            File imageFile = new File(path, "image.jpg");
            //           // use imageFile to open your image
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            onSelectFromGalleryResult(data);
        } else {
//            Toast.makeText(getApplicationContext(), "Get Image Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void onCaptureImageResult() {
        if (!photoFile.exists()) photoFile.mkdirs();
//        File imageFile = new File(path, photoName);
        // use imageFile to open your image
        Bitmap thumbnail = null;
        if (photoFile.exists()) {
            try {
                thumbnail = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
            } catch (Exception e) {
                try {
                    options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    thumbnail = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                } catch (Exception excepetion) {
                    Toast.makeText(getApplicationContext(), excepetion.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (thumbnail != null) {
            String destinationImage = new File(photoFile.getAbsolutePath()).getName();
            bitmapCamera = thumbnail;
            photoName = destinationImage;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapCamera.compress(Bitmap.CompressFormat.PNG, 80, baos);
            byte[] byteArray = baos.toByteArray();
            imageProfile = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Glide.with(getApplicationContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_no_image)
                            .error(R.drawable.ic_no_image))
                    .asBitmap()
                    .circleCrop()
                    .load(Base64.decode(imageProfile, Base64.DEFAULT))
                    .into(binding.imgProfile);
        } else {
            Toast.makeText(getApplicationContext(), "Get Image Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        final InputStream imageStream;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(selectedImage));
        if (type == null) {
            type = MimeTypeMap.getFileExtensionFromUrl(selectedImage.toString());
        }
        if (type.equals("jpeg") || type.equals("png") || type.equals("jpg")) {
            Cursor cursor = cR.query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            String imagePath = new File(picturePath).getName();

//            bitmapGallery = BitmapFactory.decodeFile(picturePath);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            bitmapGallery = BitmapFactory.decodeFile(picturePath, options);

            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmapGallery = BitmapFactory.decodeStream(imageStream, null, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapGallery.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            photoName = imagePath;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapGallery.compress(Bitmap.CompressFormat.PNG, 80, baos);
            byte[] byteArray = baos.toByteArray();
            imageProfile = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Glide.with(getApplicationContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_no_image)
                            .error(R.drawable.ic_no_image))
                    .asBitmap()
                    .circleCrop()
                    .load(Base64.decode(imageProfile, Base64.DEFAULT))
                    .into(binding.imgProfile);
        } else {
            Toast.makeText(getApplicationContext(), "Get Image Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        byte[] image = null;
        if(imageProfile == null) {
            if (!user.getImage_1920().equals("false")) {
                image = Base64.decode(user.getImage_1920(), Base64.DEFAULT);
            }
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_no_image)
                            .error(R.drawable.ic_no_image))
                    .asBitmap()
                    .circleCrop()
                    .load(image)
                    .into(binding.imgProfile);
        }
    }



    private void showDialogChangeProfile() {
        if (dialog == null || !dialog.isShowing()) {

            DialogConfirmationBinding dialogBinding = DialogConfirmationBinding
                    .inflate(getLayoutInflater(), binding.getRoot(), false);

            dialogBinding.txtDialogMessage.setText(getString(R.string.edit_profile_dialog_message));
            dialogBinding.btnYes.setText(getString(R.string.edit_profile_dialog_pos_button_label));

            AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                    .setView(dialogBinding.getRoot())
                    .setCancelable(true)
                    .show();


            dialogBinding.btnNo.setOnClickListener(view1 -> dialog.dismiss());
            dialogBinding.btnYes.setOnClickListener(view1 -> {

//                showDialogSuccess();
                new ConnectionOdoo().execute();
                progress.show();
            });


            dialogBinding.getRoot().setOnClickListener(view -> dialog.dismiss());
        }
    }

    private void showDialogSuccess() {
        if (dialog == null || !dialog.isShowing()) {
            DialogCheckInSuccessBinding dialogBinding = DialogCheckInSuccessBinding.inflate(getLayoutInflater(), binding.getRoot(), false);

            dialogBinding.tvMessage.setText(getString(R.string.edit_profile_dialog_success_message));
            AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                    .setView(dialogBinding.getRoot())
                    .setCancelable(true)
                    .show();

            dialog.setOnDismissListener(dialogInterface -> onBackPressed());

            dialogBinding.getRoot().setOnClickListener(view -> {
                dialog.dismiss();

                onBackPressed();
            });
        }
    }


    private class ConnectionOdoo extends AsyncTask<Void, String, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

                try {
                    profile = new HashMap<String, Object>();
                    profile.put("name", name);
                    profile.put("email", email);
                    profile.put("phone", phone);
                    if(imageProfile != null) {
                        profile.put("image_1920", imageProfile);
                    }else{
                        profile.put("image_1920", user.getImage_1920());
                    }
                    results = oc.write("res.users", new Integer[]{cd.getUid()}, profile);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                    return false;
                }

        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Boolean result) {

                progress.dismiss();
                if (result) {
                    Toast.makeText(getApplicationContext(), "Change profile success", Toast.LENGTH_SHORT).show();
                    showDialogSuccess();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    if(imageProfile != null) {
                        user.setImage_1920(imageProfile);
                    }else{
                        user.setImage_1920(user.getImage_1920());
                    }
                    finish();
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Connection error when change profile", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }

        }
    }
}