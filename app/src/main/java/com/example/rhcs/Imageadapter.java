package com.example.rhcs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Imageadapter extends RecyclerView.Adapter<Imageadapter.imgholder> {

    Context mcontext, cn;
    List<Uploadimage> muploads;
    String center, cc;
    SharedPreferences sharedPreferences;
    private OnItemClickListener mListener;
    AlertDialog.Builder builder;

    public Imageadapter(Context context, List<Uploadimage> uploads) {
        mcontext = context;
        muploads = uploads;
    }

    @NonNull
    @Override
    public imgholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.img, parent, false);
        cn = view.getContext();
        return new imgholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull imgholder holder, int position) {
        sharedPreferences = mcontext.getSharedPreferences("share", MODE_PRIVATE);
        center = sharedPreferences.getString("name", "");
        Uploadimage uploadimage = muploads.get(position);
        holder.textView.setText(uploadimage.getImgname());

        Picasso.get().load(uploadimage.getImgurl()).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class imgholder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public imgholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.nam);
            imageView = itemView.findViewById(R.id.fimg);

            SharedPreferences sharedPreferences = mcontext.getSharedPreferences("sh", MODE_PRIVATE);
            cc = sharedPreferences.getString("mann", "");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });
            if (cc.equals("manager")) {
                itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        contextMenu.setHeaderTitle("Make Action");
                        MenuItem del = contextMenu.add(Menu.NONE, 2, 2, "delete");

                        del.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (mListener != null) {
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        mListener.onDeleteClick(position);
                                    }
                                }
                                return false;
                            }
                        });

                    }
                });
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);

    }

    public void setOnItemCLickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
