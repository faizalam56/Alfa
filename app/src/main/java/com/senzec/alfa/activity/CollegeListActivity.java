package com.senzec.alfa.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;
import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.college_list.CollegeListModel;
import com.senzec.alfa.model.college_list.Result;
import com.senzec.alfa.model.college_list.web_api.CollegeListWebApiModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiClientJSON;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.to_json.collegelist.CollegeWithMenu;
import com.senzec.alfa.utils.ConnectivityManagerClass;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.ProgressClass;
import com.senzec.alfa.utils.SharedPrefClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CollegeListActivity extends AppCompatActivity implements View.OnClickListener {

    private Planet[] planets ;
    private ArrayAdapter<Planet> listAdapter ;
    private Button btn_continue;
    ApiInterface apiInterface;
    ListView mainListView;
    PlanetViewHolder viewHolder;
    List<Result> tempCollegeList = new ArrayList<>();
    List<String> selectedGroupList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_college_list);

        mainListView = (ListView) findViewById( R.id.mainListView );
        btn_continue = (Button) findViewById(R.id.btn_profile_continue);
        btn_continue.setOnClickListener(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) findViewById(android.R.id.content));

        // When item is tapped, toggle checked properties of CheckBox and Planet.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                Planet planet = listAdapter.getItem( position );
                planet.toggleChecked();
                PlanetViewHolder viewHolder = (PlanetViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( planet.isChecked() );
            }
        });

        if(ConnectivityManagerClass.getInstance().isNetworkAvailable(CollegeListActivity.this) == true) {
            funcCollegeListWebApi();
        }else {
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("Network Failed! Check your Internet")
                    .setCustomImage(R.drawable.ic_disconnected)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onClick(View view) {
        if(ConnectivityManagerClass.getInstance().isNetworkAvailable(CollegeListActivity.this) == true) {
        switch (view.getId()){
            case R.id.btn_profile_continue:
     //           startActivity(new Intent(CollegeListActivity.this,GroupFeedActivity.class));
                sumOfSelectedItem();
                break;
        }
        }else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(CollegeListActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            sweetAlertDialog.setTitleText("No Network!");
            sweetAlertDialog.setContentText("Press 'OK' to Retry");
            sweetAlertDialog.setCustomImage(R.drawable.ic_disconnected);
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();

                }
            })
                    .show();
        }
    }

    public void sumOfSelectedItem(){
        for(int i = 0; i<listAdapter.getCount(); i++){
            Planet planet = listAdapter.getItem( i );
            if(listAdapter.getItem(i).isChecked() == true){
             //   String groupName = listAdapter.getItem(i).name;
                String groupID = tempCollegeList.get(i).getId();
                if(groupID.length()>0)
                { selectedGroupList.add(groupID);}
            }
        }
        if(selectedGroupList.isEmpty() == false){
            parseGson(selectedGroupList);
        }else {
            TastyToast.makeText(getApplicationContext(), " Select atleast 'One' ", TastyToast.LENGTH_LONG,
                    TastyToast.INFO);
        }

    }

    public void parseGson(List<String> group_id){

        String userId = new SharedPrefClass(CollegeListActivity.this).getLoginInfo();
        CollegeWithMenu profileMenu =
                new CollegeWithMenu(group_id, userId);

        Gson gson = new Gson();
        String profileJson = gson.toJson(profileMenu);
        System.out.print(profileJson);
        sendJoinedGroupJsonRequest(profileJson);
    }
    /** Holds planet data. */
    private static class Planet {
        private String name = "" ;
        private boolean checked = false ;
        public Planet() {}
        public Planet( String name ) {
            this.name = name ;
        }
        public Planet( String name, boolean checked ) {
            this.name = name ;
            this.checked = checked ;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isChecked() {
            return checked;
        }
        public void setChecked(boolean checked) {
            this.checked = checked;
        }
        public String toString() {
            return name ;
        }
        public void toggleChecked() {
            checked = !checked ;
        }
    }

    /** Holds child views for one row. */
    private static class PlanetViewHolder {
        private CheckBox checkBox ;
        private TextView textView ;
        public PlanetViewHolder() {}
        public PlanetViewHolder( TextView textView, CheckBox checkBox ) {
            this.checkBox = checkBox ;
            this.textView = textView ;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
        public TextView getTextView() {
            return textView;
        }
        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    /** Custom adapter for displaying an array of Planet objects. */
    private static class PlanetArrayAdapter extends ArrayAdapter<Planet> {

        private LayoutInflater inflater;

        public PlanetArrayAdapter(Context context, List<Planet> planetList ) {
            super( context, R.layout.simplerow, R.id.rowTextView, planetList );
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Planet to display
            Planet planet = this.getItem( position );
            CheckBox checkBox ;
            TextView textView ;
            if ( convertView == null ) {
                convertView = inflater.inflate(R.layout.simplerow, null);
                textView = (TextView) convertView.findViewById( R.id.rowTextView );
                checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );
                convertView.setTag( new PlanetViewHolder(textView,checkBox) );
                checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Planet planet = (Planet) cb.getTag();
                        planet.setChecked( cb.isChecked() );
                    }
                });
            }
            else {
                PlanetViewHolder viewHolder = (PlanetViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox() ;
                textView = viewHolder.getTextView() ;
            }
            checkBox.setTag( planet );
            checkBox.setChecked( planet.isChecked() );
            textView.setText( planet.getName() );
            return convertView;
        }
    }

    public Object onRetainCustomNonConfigurationInstance () {
        return planets ;
    }

    private void funcCollegeListWebApi()
    {
        apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        String user_id = new SharedPrefClass(CollegeListActivity.this).getLoginInfo();

        Call<CollegeListModel> callCollegeList = apiInterface.collegeListResponse(user_id);
        callCollegeList.enqueue(new Callback<CollegeListModel>() {
            @Override
            public void onResponse(Call<CollegeListModel> call, Response<CollegeListModel> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    if(response.body().getResponseCode() == 200){
                     //   Toast.makeText(CollegeListActivity.this, "Success", Toast.LENGTH_LONG).show();
              //          TastyToast.makeText(getApplicationContext(), "Data Saved Successful !", TastyToast.LENGTH_LONG,
                //                TastyToast.SUCCESS);
                        List<Result> collegeApiList = response.body().getResults();
                        parseCollegeList(collegeApiList);
                    }
                } else {
                    Toast.makeText(CollegeListActivity.this, "Confusion", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CollegeListModel> call, Throwable t) {
                call.cancel();
                TastyToast.makeText(getApplicationContext(), "Something went wrong!", TastyToast.LENGTH_LONG,
                        TastyToast.CONFUSING);
            }
        });

    }

    public void parseCollegeList(List<Result> collegeApiList){
        // Create and populate planets.
        planets = (Planet[]) getLastCustomNonConfigurationInstance() ;
        if ( planets == null ) {

            planets = new Planet[collegeApiList.size()];
            for(int i = 0; i<collegeApiList.size(); i++){
              //  planets = new Planet[i]; { new Planet(collegeApiList.get(i).getGroupName())};
                planets[i] = new Planet(collegeApiList.get(i).getGroupName());
            }

        }
        ArrayList<Planet> planetList = new ArrayList<Planet>();
        planetList.addAll( Arrays.asList(planets) );

        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new PlanetArrayAdapter(CollegeListActivity.this, planetList);
        mainListView.setAdapter( listAdapter );
        tempCollegeList = collegeApiList;
    }



    public void sendJoinedGroupJsonRequest(String profileJson){

        //    Toast.makeText(EditProfileActivity.this, "Progress Started", Toast.LENGTH_LONG).show();
        ProgressClass.getProgressInstance().startProgress(CollegeListActivity.this);
        ApiInterface apiInterface = ApiClientJSON.getClient(Consts.BASE_URL).create(ApiInterface.class);
        apiInterface.setUserJoinedGroup(profileJson).enqueue(new Callback<CollegeListWebApiModel>() {
            @Override
            public void onResponse(Call<CollegeListWebApiModel> call, Response<CollegeListWebApiModel> response) {

                if(response.isSuccessful() && response.code() == 200) {
                    CollegeListWebApiModel collegeListApiResponseModel = response.body();
                    if (collegeListApiResponseModel.getResponseCode() == 200) {
                        ProgressClass.getProgressInstance().stopProgress();
                        Toast.makeText(CollegeListActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(CollegeListActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Good job!")
                                .setContentText("Data save succesfully!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        startActivity(new Intent(CollegeListActivity.this, GroupFeedActivity.class));
                                    }
                                })
                                .show();

                    } else if(collegeListApiResponseModel.getResponseCode() == 404)  {
                        Toast.makeText(CollegeListActivity.this, "Login Declined", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CollegeListWebApiModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(CollegeListActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}