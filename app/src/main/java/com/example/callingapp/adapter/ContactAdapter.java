package com.example.callingapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.callingapp.R;
import com.example.callingapp.moudle.Contact;
import com.example.callingapp.utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<Contact> list;
    private String TAG = "sssssssss";

    public ContactAdapter(Activity activity, ArrayList<Contact> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(v);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).name + "");
        holder.phone.setText(list.get(position).number + "");
        holder.actionsLinear.setVisibility(View.GONE);
        holder.sendCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = list.get(holder.getAdapterPosition()).number;
                String final_number = "0" + phone.substring(phone.length() - 9, phone.length());
                String encodedHash = Uri.encode("#");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + encodedHash + final_number));
                activity.startActivity(intent);
            }
        });
        holder.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = list.get(holder.getAdapterPosition()).number;
                String final_number = "0" + phone.substring(phone.length() - 9, phone.length());
                String encodedHash = Uri.encode("#");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:*111*5*" + final_number + encodedHash));
                activity.startActivity(intent);
            }
        });
        holder.copyShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = list.get(holder.getAdapterPosition()).number;
                String final_number = phone.substring(phone.length() - 8, phone.length());
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", final_number);
                clipboard.setPrimaryClip(clip);
                Utils._Toast(activity, "تم الحفظ");
            }
        });
        holder.copyComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = list.get(holder.getAdapterPosition()).number;
                String final_number = "0" + phone.substring(phone.length() - 9, phone.length());
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", final_number);
                clipboard.setPrimaryClip(clip);
                Utils._Toast(activity, "تم الحفظ");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.actionsLinear.getVisibility() == View.VISIBLE) {
                    Animation slide_up = AnimationUtils.loadAnimation(activity,
                            R.anim.slide_up);
                    holder.actionsLinear.startAnimation(slide_up);
                    slide_up.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            holder.actionsLinear.setVisibility(View.GONE);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    holder.actionsLinear.setVisibility(View.INVISIBLE);
                    Animation slide_down = AnimationUtils.loadAnimation(activity,
                            R.anim.slide_down);
                    holder.actionsLinear.startAnimation(slide_down);
                    slide_down.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            holder.actionsLinear.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private LinearLayout actionsLinear;
        private TextView sendCall;
        private TextView sendMsg;
        private TextView copyShort;
        private TextView copyComplete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            actionsLinear = (LinearLayout) itemView.findViewById(R.id.actionsLinear);
            sendCall = (TextView) itemView.findViewById(R.id.sendCall);
            sendMsg = (TextView) itemView.findViewById(R.id.sendMsg);
            copyShort = (TextView) itemView.findViewById(R.id.copyShort);
            copyComplete = (TextView) itemView.findViewById(R.id.copyComplete);
        }
    }
}
