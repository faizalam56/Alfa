package com.senzec.alfa.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.myprofile.MyProfileModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Common;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.ImagesCache;
import com.senzec.alfa.utils.ProgressClass;
import com.senzec.alfa.utils.SharedPrefClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    private static final String TAG = "MyProfileActivity";
    LinearLayout mUploadCv;
    ImageView mBackIV, mEditProfileIV, mProfileIV;
    Button btn_create_job, btn_enquiries, mCompanyBTN, mInviteMemberBTN, mChatHistoryBtn;

    //ATTACHMENT
    private static final int REQUEST_CODE_ATTACHMENT = 721;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int READ_REQUEST_CODE = 42;
    private Uri mCropImagedUri;
    private final int CROP_IMAGE = 101;

    private Uri fileUri; // file url to store image/video
    File filePhoto;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_my_profile_new);

        initView();
        handleClick();
        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) findViewById(android.R.id.content));
    }

    public void initView(){

        mUploadCv = (LinearLayout)findViewById(R.id.idUploadCv) ;
        mBackIV = (ImageView)findViewById(R.id.idBackIV);
        mEditProfileIV = (ImageView)findViewById(R.id.idEditProfileIV);
        mProfileIV = (ImageView)findViewById(R.id.idProfileImage);
        btn_create_job = (Button) findViewById(R.id.btn_create_job);
        mChatHistoryBtn = (Button)findViewById(R.id.idChatHistory);
        mCompanyBTN = (Button)findViewById(R.id.idCompanyBtn);
        mInviteMemberBTN = (Button)findViewById(R.id.idInviteMemberBtn);

    }
    public void handleClick(){

        mUploadCv.setOnClickListener(MyProfileActivity.this);
        mBackIV.setOnClickListener(MyProfileActivity.this);
        mEditProfileIV.setOnClickListener(MyProfileActivity.this);
        btn_create_job.setOnClickListener(this);
        mChatHistoryBtn.setOnClickListener(this);
        mCompanyBTN.setOnClickListener(this);
        mInviteMemberBTN.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.idBackIV:
                startActivity(new Intent(MyProfileActivity.this, MainActivity.class));
                break;
            case R.id.idEditProfileIV:
                PopupMenu popup = new PopupMenu(MyProfileActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                popup.setOnMenuItemClickListener(MyProfileActivity.this);
                inflater.inflate(R.menu.file_chooser_menu, popup.getMenu());
                popup.show();
                break;
            case R.id.idUploadCv:
                Toast.makeText(MyProfileActivity.this, "Upload CV Clicked", Toast.LENGTH_LONG).show();
                showStorage();
                new SharedPrefClass(MyProfileActivity.this).setStorageTypeFile("cv_file_type");
                break;
            case R.id.btn_create_job:
                startActivity(new Intent(MyProfileActivity.this, AddJobActivity.class));
                break;
            case R.id.idCompanyBtn:
                startActivity(new Intent(MyProfileActivity.this, CompaniesActivity.class));
                break;
            case R.id.idInviteMemberBtn:
                Toast.makeText(MyProfileActivity.this, "Under Development", Toast.LENGTH_LONG).show();
                break;
            case R.id.idChatHistory:
                startActivity(new Intent(MyProfileActivity.this, ProfiledetailActivity.class));
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                captureImage();
                new SharedPrefClass(MyProfileActivity.this).setStorageTypeFile("");
                break;
            case R.id.item2:
                showStorageImage();
                new SharedPrefClass(MyProfileActivity.this).setStorageTypeFile("");
                break;
            case R.id.item3:
                break;
        }
        return true;
    }
    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        new SharedPrefClass(MyProfileActivity.this).setStorageType(Consts.STORAGE_CAMERA);
    }
    /**
     * Checking device has camera hardware or not
     * */
    private  void showStorageImage()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
        new SharedPrefClass(MyProfileActivity.this).setStorageType(Consts.STORAGE_INTERNAL);
    }
    private  void showStorage()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
        new SharedPrefClass(MyProfileActivity.this).setStorageType(Consts.STORAGE_INTERNAL);
    }
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
        String dir = "Alpha";
        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                dir);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + dir + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    private void launchUploadActivity(boolean isImage){
        funcAttachment(fileUri);
    }
    private void launchUploadActivity1(Uri uri) throws URISyntaxException {
        funcAttachment(uri);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if(requestCode == READ_REQUEST_CODE){
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    Log.i(TAG, "Uri: " + uri.toString());
                    try {
                        launchUploadActivity1(uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled recording
                    Toast.makeText(getApplicationContext(),"User cancelled selection", Toast.LENGTH_SHORT)
                            .show();
                } else {
                }
            }

                //SECOND
                // if the result is capturing Image
                if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                    Toast.makeText(getApplicationContext(),
                            "camera", Toast.LENGTH_SHORT)
                            .show();
                    if (resultCode == RESULT_OK) {
                        launchUploadActivity(true);
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
                    }

                    //
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                        Log.i(TAG, "Uri: " + uri.toString());
                        try {
                            launchUploadActivity1(uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        // user cancelled recording
                        Toast.makeText(getApplicationContext(),
                                "User cancelled selection", Toast.LENGTH_SHORT)
                                .show();

                    } else {
                    }
                }

            if(requestCode==CROP_IMAGE) {
                if (data != null) {
                    uploadImage(data.getData());
                }
            }
        }

    }
    // *****************FOR FILEPATH**********************
    public void funcAttachment(Uri uriImagePath)
    {
        System.out.println(uriImagePath);
        if(uriImagePath.getPath().endsWith(".png")||uriImagePath.getPath().endsWith(".jpeg")||uriImagePath.getPath().endsWith(".jpg"))
        {performCropImage(uriImagePath);}
        else{
            uploadImage(uriImagePath);
        }
    }

    public void uploadImage(Uri selectedImageUri){

        String mediaPath = null;
        File file = null;
        Call<MyProfileModel> callUpload = null;
        if(selectedImageUri.getScheme().equalsIgnoreCase("content")) {

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            // Set the Image in ImageView for Previewing the Media
            cursor.close();
            file = new File(mediaPath);
        }else if(selectedImageUri.getScheme().equalsIgnoreCase("file")){
            file = new File(selectedImageUri.getPath());
        }
        if(new SharedPrefClass(MyProfileActivity.this).getStorageTypeFile().equalsIgnoreCase("cv_file_type"))
        {
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("cvFile", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            ProgressClass.getProgressInstance().startProgress(MyProfileActivity.this);
            apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
            String user_id = new SharedPrefClass(MyProfileActivity.this).getLoginInfo();
            callUpload = apiInterface.uploadCVFile(fileToUpload, filename, user_id);

        }else{
        //    mProfileIV.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imgFile", file.getName(), requestBody);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            ProgressClass.getProgressInstance().startProgress(MyProfileActivity.this);
            apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
            String user_id = new SharedPrefClass(MyProfileActivity.this).getLoginInfo();
            callUpload = apiInterface.uploadImageFile(fileToUpload, filename, user_id);
        }

            callUpload.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    ProgressClass.getProgressInstance().stopProgress();
                    if (response.isSuccessful() && response.code() == 200) {
                        Toast.makeText(MyProfileActivity.this, "Success", Toast.LENGTH_LONG).show();

                        if(!new SharedPrefClass(MyProfileActivity.this).getStorageTypeFile().equalsIgnoreCase("cv_file_type")) {
                            String url = Consts.BASE_URL + response.body().getProfilePic();
                            setProfileImage(url);
                        }
                    } else {
                        Toast.makeText(MyProfileActivity.this, "Confusion", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {
                call.cancel();
                    ProgressClass.getProgressInstance().stopProgress();
                    Toast.makeText(MyProfileActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });
    }

    public void setProfileImage(String actualImageUrl){

                //IMAGE CACHE START
                ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
                cache.initializeCache();
                Bitmap bm = cache.getImageFromWarehouse(actualImageUrl);
                if(bm != null)
                {
                    Glide.with(MyProfileActivity.this)
                            .load(bm)
                            .into(mProfileIV);
                }
                else {
                    Glide.with(MyProfileActivity.this)
                            .load(actualImageUrl)
                            .error(R.drawable.img_my_profile)
                            .into(mProfileIV);
                }
    }
    // ----------------------CROP--------------------------

    /**Crop the image
     * @return returns <tt>true</tt> if crop supports by the device,otherwise false*/
    private boolean performCropImage(Uri mFinalImageUri){
        try {
            if(mFinalImageUri!=null){
                //call the standard crop action intent (the user device may not support it)
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                //indicate image type and Uri
                cropIntent.setDataAndType(mFinalImageUri, "image/*");
                //set crop properties
                cropIntent.putExtra("crop", "true");
                //indicate aspect of desired crop
                cropIntent.putExtra("aspectX", 2);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("scale", true);
                //indicate output X and Y
          //      cropIntent.putExtra("outputX", 180);
          //      cropIntent.putExtra("outputY", 180);
                //retrieve data on return
                cropIntent.putExtra("return-data", false);

                File f = createNewFile("CROP_");
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    Log.e("io", ex.getMessage());
                }

                mCropImagedUri = Uri.fromFile(f);
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
                //start the activity - we handle returning in onActivityResult
                startActivityForResult(cropIntent, CROP_IMAGE);
                return true;
            }
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return false;
    }

    private File createNewFile(String prefix){
        if(prefix==null || "".equalsIgnoreCase(prefix)){
            prefix="IMG_";
        }
        File newDirectory = new File(Environment.getExternalStorageDirectory()+"/mypics/");
        if(!newDirectory.exists()){
            if(newDirectory.mkdir()){
                Log.d("MyProfileActivity", newDirectory.getAbsolutePath()+" directory created");
            }
        }
        File file = new File(newDirectory,(prefix+System.currentTimeMillis()+".jpg"));
        if(file.exists()){
            //this wont be executed
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
    // ------------------------------------------------------
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }


}
