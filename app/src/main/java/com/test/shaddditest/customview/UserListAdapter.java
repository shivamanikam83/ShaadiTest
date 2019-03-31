package com.test.shaddditest.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.shaddditest.R;
import com.test.shaddditest.json.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;
    private int lastPosition = -1;
    private Handler handler;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        handler = new Handler();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder userViewHolder, final int i) {
        final User user = userList.get(i);

        if(user!=null)
        {
            userViewHolder.profile_name.setText(user.getName().getTitle() + " " + user.getName().getFirst() + " " + user.getName().getLast());
            Glide.with(context)
                    .load(user.getPicture().getMedium())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(userViewHolder.profile_image);
            String desc_text = user.getDob().getAge() + " yrs, \n"+user.getLocation().getStreet()+", "+user.getLocation().getCity()+", "+user.getLocation().getState();
            String[] strArray = desc_text.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String s : strArray) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap + " ");
            }

            userViewHolder.profile_desc.setText(builder.toString());

            userViewHolder.connectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCardItem(i);
                }
            });

            userViewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCardItem(i);
                }
            });

        }
    }

    private void updateCardItem(int pos)
    {
        List<User> updatedList = new ArrayList<User>();
        User user = userList.get(pos);
        updatedList.addAll(userList);
        updatedList.remove(user);

        final UserDiff diffCallback = new UserDiff(this.userList, updatedList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.userList.clear();
        this.userList.addAll(updatedList);
        //notifyDataSetChanged();
        diffResult.dispatchUpdatesTo(this);
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 1000);
    }

    public void updateData(List<User> list)
    {
        userList.clear();
        userList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView profile_name, profile_desc;
        CircleImageView profile_image;
        Button cancelBtn, connectBtn;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_name = itemView.findViewById(R.id.profile_name);
            profile_desc = itemView.findViewById(R.id.profile_desc);
            profile_image = itemView.findViewById(R.id.profile_image);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
            connectBtn = itemView.findViewById(R.id.connectBtn);
        }
    }
}
