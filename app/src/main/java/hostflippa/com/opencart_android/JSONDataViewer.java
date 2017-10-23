package hostflippa.com.opencart_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONDataViewer extends AppCompatActivity {

    Context context;
    //    private List<Movie> movieList
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private List<MyData> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsondata_viewer);
        this.context = this;
        AndroidNetworking.initialize(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        mRecyclerView.setHasFixedSize(true);

//        loadDummyData();
        loadData();
        Log.e("DataListPopulated", "Data list populated");
        mAdapter = new CustomAdapter(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapter", "Setting Adapter");
        mRecyclerView.setAdapter(mAdapter);
        Log.e("AdapterSet", "Adapter Set Success");
    }

    private void loadDummyData() {

        String question = "Question ";
        int qNo = 1;
        for (int i = 0; i < 10; i++) {
            qNo++;
            MyData data = new MyData("" + qNo, question + qNo);
            Log.e("Question" + " " + qNo,
                    "\nQuestion id = " + data.getQuestionId() +
                            " Question text = " + data.getQuestionText());
            dataList.add(data);

        }
    }

    private void loadData() {
        AndroidNetworking.post("http://www.skafs.com/survey/api/index.php")
                .addBodyParameter("tag", "getquestion")
                .addBodyParameter("category_id", "2")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("LoadingData", "" + "Successful" + response);
//                        Toast.makeText(context, "Response:\n" + "Loading Data" + response,
//                                Toast.LENGTH_LONG).show();
                        boolean success = response.optBoolean("success");
                        if (success) {
                            Log.e("InsideSuccess", "Success = " + success);
                            try {
                                JSONArray questions =
                                        response.optJSONArray("questions");
                                Log.e("Questions", questions.toString());
                                for (int i = 0; i < questions.length(); i++) {
                                    JSONObject obj = questions.getJSONObject(i);
                                    MyData data = new MyData(
                                            obj.getString("question_id"),
                                            obj.getString("question_text"));
                                    Log.e("Question" + " " + i,
                                            "\nQuestion id = " + data.getQuestionId() +
                                                    " Question text = " + data.getQuestionText());
                                    dataList.add(data);
                                }
                                mAdapter.notifyDataSetChanged();
                                Log.e("ListData", "List Size is = "+dataList.size());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Log.i("ParsingJSON", "" + "Failed getting json");
                    }
                });
    }
}
