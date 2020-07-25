package com.myproject.salesdemomvvm.repositories;

import android.os.AsyncTask;
import android.util.Log;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.addfollowup.FollowupStatusModel;
import com.myproject.salesdemomvvm.allclients.AllClientsModel;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.home.HomeModel;
import com.myproject.salesdemomvvm.home.SliderModel;
import com.myproject.salesdemomvvm.login.ExecutiveModel;
import com.myproject.salesdemomvvm.network.ApiHelper;
import com.myproject.salesdemomvvm.network.ServiceBuilder;
import com.myproject.salesdemomvvm.room.DatabaseClient;
import com.myproject.salesdemomvvm.utils.Methods;
import com.myproject.salesdemomvvm.viewfollowups.ViewFollowupModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository {

    private ApiHelper api = ServiceBuilder.getRetrofitInstance().create(ApiHelper.class);

    //Login
    public LiveData<List<ExecutiveModel>> loginExecutive(String id, String password, String token) {
        final MutableLiveData<List<ExecutiveModel>> loginResponse = new MutableLiveData<>();

        if (Methods.isNetworkAvailable()) {
            Call<List<ExecutiveModel>> callRequest = api.loginExecutive(id, password, token);
            callRequest.enqueue(new Callback<List<ExecutiveModel>>() {
                @Override
                public void onResponse(Call<List<ExecutiveModel>> call, Response<List<ExecutiveModel>> response) {
                    if (response.isSuccessful()) {
                        loginResponse.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<ExecutiveModel>> call, Throwable t) {
                    Methods.toast(MyApplication.getInstance()
                            .getResources().getString(R.string.fail));
                }
            });
            return loginResponse;
        } else {
            return null;
        }
    }

    //Slider
    public LiveData<ArrayList<SliderModel>> getSlider() {

        if (Methods.isNetworkAvailable()) {
            final MutableLiveData<ArrayList<SliderModel>> sliderResponse = new MutableLiveData<>();
            Call<ArrayList<SliderModel>> requestCall = api.getSlider();
            requestCall.enqueue(new Callback<ArrayList<SliderModel>>() {
                @Override
                public void onResponse(Call<ArrayList<SliderModel>> call, Response<ArrayList<SliderModel>> response) {
                    if (response.isSuccessful()) {
                        sliderResponse.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<SliderModel>> call, Throwable t) {
                    Methods.toast(MyApplication.getInstance()
                            .getResources().getString(R.string.fail));
                }
            });
            return sliderResponse;
        } else {
            return null;
        }
    }

    //HomeList
    public LiveData<ArrayList<HomeModel>> getHomeList() {

        final MutableLiveData<ArrayList<HomeModel>> homeResponse = new MutableLiveData<>();
        ArrayList<HomeModel> arrayList = new ArrayList<>();
        arrayList.add(new HomeModel("0", R.drawable.add_client, "  Add \nClient", "0"));
        arrayList.add(new HomeModel("1", R.drawable.all_clients, "     All \nClients", "0"));
        arrayList.add(new HomeModel("2", R.drawable.profile, "Profile", "0"));
        arrayList.add(new HomeModel("3", R.drawable.add_client, "Change \nPassword", "0"));
        arrayList.add(new HomeModel("4", R.drawable.helpline, "Helpline", "0"));

        homeResponse.setValue(arrayList);
        return homeResponse;
    }


    //AddClient
    public LiveData<String> addClient(String executive_username, String executive_name,
                                      String company_name, String name, String contact,
                                      String name1, String contact1, String name2, String contact2,
                                      String address, String area, String city, String pincode) {

//        InsertClient insertClient = new InsertClient();
//        insertClient.execute();

        if (Methods.isNetworkAvailable()) {

            final MutableLiveData<String> addClientResponse = new MutableLiveData<>();

            final Call<String> requestCall = api.addClint(executive_username, executive_name,
                    company_name, name, contact, name1, contact1, name2, contact2, address,
                    area, city, pincode);
            requestCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        addClientResponse.setValue(response.body().trim());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    addClientResponse.setValue(t.toString());
                }
            });

            return addClientResponse;
        } else {
            return null;
        }
    }

    //UpdatePassword
    public LiveData<String> updatePassword(String username, String password) {

        if (Methods.isNetworkAvailable()) {
            final MutableLiveData<String> updatePasswordResponse = new MutableLiveData<>();
            Call<String> requestCall = api.updatePassword(username, password);
            requestCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        updatePasswordResponse.setValue(response.body().trim());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    updatePasswordResponse.setValue(t.toString());
                }
            });
            return updatePasswordResponse;
        } else {
            return null;
        }
    }

    //AllClient
    public LiveData<ArrayList<AllClientsModel>> getClients(String username, String search_entity) {

        if (Methods.isNetworkAvailable()) {
            final MutableLiveData<ArrayList<AllClientsModel>> clientResponse = new MutableLiveData<>();
            final Call<ArrayList<AllClientsModel>> requestCall;
            if (search_entity.isEmpty()) {
                requestCall = api.getAllClients(username);
            } else {
                requestCall = api.searchClients(username, search_entity);
            }

            requestCall.enqueue(new Callback<ArrayList<AllClientsModel>>() {
                @Override
                public void onResponse(Call<ArrayList<AllClientsModel>> call, Response<ArrayList<AllClientsModel>> response) {
                    if (response.isSuccessful()) {
                        clientResponse.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<AllClientsModel>> call, Throwable t) {
                    Methods.toast(MyApplication.getInstance()
                            .getResources().getString(R.string.fail));
                }
            });

            return clientResponse;
        } else {
            return null;
        }
    }


    //AddFollowup
    public LiveData<String> addFollowup(String ticketno, String clientname, String description,
                                        String status, String next_followup, String upload_url,
                                        String by_username, String by_name) {

        if (Methods.isNetworkAvailable()) {
            final MutableLiveData<String> addFollowupResponse = new MutableLiveData<>();

            Call<String> requestCall = api.addFollowup(ticketno, clientname, description, status,
                    next_followup, upload_url, by_username, by_name);

            requestCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        addFollowupResponse.setValue(response.body().trim());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Methods.toast(t.toString());
                }
            });
            return addFollowupResponse;
        } else {
            return null;
        }
    }


    //ViewFollowup
    public LiveData<ArrayList<ViewFollowupModel>> getFollowup(String ticket_no) {

        if (Methods.isNetworkAvailable()) {
            final MutableLiveData<ArrayList<ViewFollowupModel>> viewFollowupResponse =
                    new MutableLiveData<>();

            Call<ArrayList<ViewFollowupModel>> requestCall = api.getAllFollowup(ticket_no);
            requestCall.enqueue(new Callback<ArrayList<ViewFollowupModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ViewFollowupModel>> call, Response<ArrayList<ViewFollowupModel>> response) {
                    if (response.isSuccessful()) {
                        viewFollowupResponse.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ViewFollowupModel>> call, Throwable t) {
                    Methods.toast(t.toString());
                }
            });

            return viewFollowupResponse;
        } else {
            return null;
        }

    }

    //StatusList
    public LiveData<ArrayList<FollowupStatusModel>> getStatusList() {
        final MutableLiveData<ArrayList<FollowupStatusModel>> statusResponse = new MutableLiveData<>();

        ArrayList<FollowupStatusModel> arrayList = new ArrayList<>();
        arrayList.add(new FollowupStatusModel("Conclude"));
        arrayList.add(new FollowupStatusModel("Cancel"));
        arrayList.add(new FollowupStatusModel("Final offer given"));
        arrayList.add(new FollowupStatusModel("Hold for client"));
        arrayList.add(new FollowupStatusModel("Hold for quotation"));

        statusResponse.setValue(arrayList);
        return statusResponse;
    }

    //UploadPdf
    public LiveData<String> uploadPDF(String path) {

        final MutableLiveData<String> responseUrl = new MutableLiveData<>();

        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        if (Methods.isNetworkAvailable()) {

            Call<String> requestCall = api.uploadPdf(fileToUpload, filename);
            requestCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        responseUrl.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Methods.toast(t.toString());
                }
            });
            return responseUrl;
        } else {
            return null;
        }


    }

//private class InsertClient extends AsyncTask<Void, Void, Void>{
//    @Override
//    protected Void doInBackground(Void... voids) {
//
//        DatabaseClient.getInstance().getAppDatabase()
//                .appDao().insertClient(
//                        new AllClientsModel(
//
//                        ));
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//    }
//}


    //AddClientRoom

    public String insertClient(AllClientsModel model){

        DatabaseClient.getInstance().getAppDatabase().appDao().insertClient(model);

        return "Inserted";
    }

}
