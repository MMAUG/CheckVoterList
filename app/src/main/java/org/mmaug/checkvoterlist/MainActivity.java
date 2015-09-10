package org.mmaug.checkvoterlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public class MainActivity extends AppCompatActivity {

  private static final String BASE_URL = "https://checkvoterlist.uecmyanmar.org/";
  private static final String voterName = "အောင်ဆန်းစုကြည်";
  private static final String voteDob = "1945-06-19";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView result = (TextView) findViewById(R.id.result);

    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    CheckVoterService checkVoterService = retrofit.create(CheckVoterService.class);

    Map<String, String> params = new HashMap<>();
    params.put("dateofbirth", voteDob);
    params.put("nrcno", null);
    params.put("father_name", null); // optional
    Call<Voter> voterCall = checkVoterService.searchVoter(voterName, params);
    voterCall.enqueue(new Callback<Voter>() {
      @Override public void onResponse(Response<Voter> response) {
        Voter voter = response.body();
        result.setText(
            voter.getVoterName() + "\n" + voter.getDateOfBirth() + "\n" + voter.getNrcno());
      }

      @Override public void onFailure(Throwable throwable) {
        // Handle the failure
      }
    });
  }

  public interface CheckVoterService {
    @GET("api") Call<Voter> searchVoter(@Query("voter_name") String voterName,
        @QueryMap Map<String, String> optionalQueries);
  }

  public class Voter {
    @SerializedName("dateofbirth") private String dateOfBirth;
    private String village;
    @SerializedName("father_name") private String fatherName;
    private String nrcno;
    private String state;
    @SerializedName("voter_name") private String voterName;
    @SerializedName("dateofbirth_num") private String dateofbirthNum;
    @SerializedName("mother_name") private String motherName;
    private String township;
    private String district;

    public String getDateOfBirth() {
      return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
    }

    public String getVillage() {
      return village;
    }

    public void setVillage(String village) {
      this.village = village;
    }

    public String getFatherName() {
      return fatherName;
    }

    public void setFatherName(String fatherName) {
      this.fatherName = fatherName;
    }

    public String getNrcno() {
      return nrcno;
    }

    public void setNrcno(String nrcno) {
      this.nrcno = nrcno;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getVoterName() {
      return voterName;
    }

    public void setVoterName(String voterName) {
      this.voterName = voterName;
    }

    public String getDateofbirthNum() {
      return dateofbirthNum;
    }

    public void setDateofbirthNum(String dateofbirthNum) {
      this.dateofbirthNum = dateofbirthNum;
    }

    public String getMotherName() {
      return motherName;
    }

    public void setMotherName(String motherName) {
      this.motherName = motherName;
    }

    public String getTownship() {
      return township;
    }

    public void setTownship(String township) {
      this.township = township;
    }

    public String getDistrict() {
      return district;
    }

    public void setDistrict(String district) {
      this.district = district;
    }
  }
}
