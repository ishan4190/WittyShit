package com.example.varun.thewittyshit.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.thewittyshit.R;

import java.util.List;
import java.util.Map;

public class ExploreRecycleViewAdapter extends RecyclerView.Adapter<ExploreRecycleViewAdapter.ViewHolder> {
    // OnItemClickListener mItemClickListener;
    private List<Map<String, ?>> movieData;
    private Context context;

    public ExploreRecycleViewAdapter(Context myContext){

        context=myContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerView;

        recyclerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_explore_layout, parent, false);

        ViewHolder vh = new ViewHolder(recyclerView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bindCardData();
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView categoryImage1,categoryImage2,categoryImage3,categoryImage4;
        public TextView categoryName;
        Button seeAll;


        public ViewHolder(View itemView) {
            super(itemView);
            categoryImage1=(ImageView)itemView.findViewById(R.id.categoryImage1);
            categoryImage2=(ImageView)itemView.findViewById(R.id.categoryImage2);
            categoryImage3=(ImageView)itemView.findViewById(R.id.categoryImage3);
            categoryImage4=(ImageView)itemView.findViewById(R.id.categoryImage4);
            categoryName=(TextView)itemView.findViewById(R.id.category_name);
            seeAll=(Button) itemView.findViewById(R.id.seeall);
        }

        public void bindCardData() {
            categoryName.setText("Category Name");
            categoryImage1.setImageResource(R.drawable.bgnav3);
            categoryImage2.setImageResource(R.drawable.bgnav3);
            categoryImage3.setImageResource(R.drawable.bgnav3);
            categoryImage4.setImageResource(R.drawable.bgnav3);
            seeAll.setText("see all");
        }
    }
}

