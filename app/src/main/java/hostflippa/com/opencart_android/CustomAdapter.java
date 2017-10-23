package hostflippa.com.opencart_android;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Inzimam on 14-Oct-17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<MyData> dataList;

    public CustomAdapter(List<MyData> dataList) {
        this.dataList = dataList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_lay, parent, false);
        Log.e("LayoutInflated", "Working");

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("OnBIndMethod", "OnBind Working");
        MyData data = dataList.get(position);
        holder.questionId.setText(data.getQuestionId());
        holder.questionText.setText(data.getQuestionText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView questionId, questionText;

        public MyViewHolder(View itemView) {
            super(itemView);
            questionId = (TextView) itemView.findViewById(R.id.question_id);
            questionText = (TextView) itemView.findViewById(R.id.question_text);
            Log.e("FindViewById", "Working");
        }
    }
}
