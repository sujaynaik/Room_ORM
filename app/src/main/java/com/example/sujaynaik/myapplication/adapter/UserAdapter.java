package com.example.sujaynaik.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sujaynaik.myapplication.R;
import com.example.sujaynaik.myapplication.interfaces.ListListener;
import com.example.sujaynaik.myapplication.model.User;
import com.example.sujaynaik.myapplication.util.MyFont;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sujaynaik on 6/28/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private String TAG = UserAdapter.class.getSimpleName();
    private Context context;
    private List<User> userList;
    private ListListener<User> listListener;
    private List<String> selectedList = new ArrayList<>();

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public void refresh(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setOnListListener(ListListener<User> listListener) {
        this.listListener = listListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvId.setText(user.getUid() + "");
        holder.tvFname.setText(user.getFirstName());
        holder.tvLname.setText(user.getLastName());

        if (user.isSelected()) {
            holder.layoutParent.setBackgroundColor(context.getResources().getColor(R.color.gray_g));
        } else {
            holder.layoutParent.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * git remote add origin git@github.com:sujaynaik/Room_ORM.git
     git push -u origin master
     */

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.layoutParent)
        LinearLayout layoutParent;
        @BindView(R.id.tvId)
        TextView tvId;
        @BindView(R.id.tvFname)
        TextView tvFname;
        @BindView(R.id.tvLname)
        TextView tvLname;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MyFont myFont = new MyFont(context);

            myFont.setTypeface(tvId, myFont.getFontBold());
            myFont.setTypeface(tvFname, myFont.getFontRegular());
            myFont.setTypeface(tvLname, myFont.getFontLight());

            layoutParent.setOnClickListener(this);
            layoutParent.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layoutParent:
                    if (listListener != null) {
                        int pos = getAdapterPosition();
                        User user = userList.get(pos);
                        listListener.onItemClick(user, pos);

                        if (selectedList.size() > 0) {
                            if (user.isSelected()) {
                                user.setSelected(false);
                                selectedList.remove(pos + "");
                            } else {
                                user.setSelected(true);
                                selectedList.add(pos + "");
                            }
                            notifyItemChanged(pos);
                            listListener.onItemSelected(user, user.isSelected(), pos);
                        }
                    }
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.layoutParent:
                    if (listListener != null) {
                        int pos = getAdapterPosition();
                        User user = userList.get(pos);
                        listListener.onItemLongClick(user, pos);

                        if (selectedList.size() == 0) {
                            if (user.isSelected()) {
                                user.setSelected(false);
                                selectedList.remove(pos + "");
                            } else {
                                user.setSelected(true);
                                selectedList.add(pos + "");
                            }
                            notifyItemChanged(pos);
                            listListener.onItemSelected(user, user.isSelected(), pos);

                        } else {
                            return false;
                        }
                    }
                    break;
            }
            return false;
        }
    }
}
