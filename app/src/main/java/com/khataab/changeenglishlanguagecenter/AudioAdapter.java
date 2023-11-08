package com.khataab.changeenglishlanguagecenter;

import android.app.Activity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ItemViewHolder> {

    static List<AudioModel> AudioList;
    Activity activity;

    public AudioAdapter( List<AudioModel> audioList,Activity activity) {
        super();
        AudioList = audioList;
        this.activity = activity;
    }


        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
            return new ItemViewHolder(view,activity);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            AudioModel item = AudioList.get(position);
            holder.txt_fileName.setText(item.getFileName());
            holder.txt_typeName.setText(item.getTypeName());
        }

        @Override
        public int getItemCount() {
            return AudioList.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_typeName;
        TextView txt_fileName;
        ImageView ivPlayPause;
        Activity secondActivity;
        Button button_download;

        Database_Helper databaseHelper;
            private CustomSnackbar customSnackbar;
            private boolean isSnackbarVisible = false;


        public ItemViewHolder(@NonNull View itemView,Activity activity) {
            super(itemView);
            secondActivity=activity;
            databaseHelper=new Database_Helper(secondActivity);

            txt_typeName = itemView.findViewById(R.id.txt_type_to_show);
            txt_fileName = itemView.findViewById(R.id.txt_file_name_to_show);
            button_download=itemView.findViewById(R.id.btn_download_file);
            ivPlayPause = (ImageView) itemView.findViewById(R.id.ivPlayPause);
            ivPlayPause.setOnClickListener(this);
            button_download.setOnClickListener(this);
            customSnackbar = new CustomSnackbar(secondActivity);

            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (v.getId() == R.id.ivPlayPause) {
                    if (position != RecyclerView.NO_POSITION) {
                        AudioModel item = AudioList.get(position);
                        File privateDir = secondActivity.getFilesDir();
                        String fileName = item.getFileName(); // Name it as you like
                        File outputFile = new File(privateDir, fileName);
                        if(outputFile.exists()) {

                            if (item.isPlaying()) {
                                // Change to paused state
                                // Use your pause icon resource
                                ivPlayPause.setImageResource(R.drawable.ic_play_arrow);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        customSnackbar.close();
                                        item.setPlaying(false);

                                    }
                                });
                            } else {
                                // Change to play state
//                                File privateDir = secondActivity.getFilesDir();
//                                String fileName = item.getFileName();
//                                File outputFile = new File(privateDir, fileName);

                                ivPlayPause.setImageResource(R.drawable.ic_pause); // Use your play icon resource
                                customSnackbar.showCustomSnackbar(outputFile, "Custom adapter");
                                item.setPlaying(true);
                            }

                        }else {
                            Toast.makeText(secondActivity, "You should First Download", Toast.LENGTH_SHORT).show();
                            downloadAndPlayMusic(item.getDownloadedURl(),item.getFileName());
                        }

                    }
                }

                if ((v.getId()==R.id.btn_download_file)){
                    AudioModel selectedFileName = AudioList.get(position);
                    DatabaseReference metadataRef = FirebaseDatabase.getInstance().getReference("MusicMetadata");
                    metadataRef.orderByChild("FileName").equalTo(selectedFileName.getFileName()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                String downloadUrl = childSnapshot.child("DownloadUrl").getValue(String.class);
                                String fileName = childSnapshot.child("FileName").getValue(String.class);
                                String fileType=childSnapshot.child("Type").getValue(String.class);
                                String unit=childSnapshot.child("Unit").getValue(String.class);
                                String BookName=childSnapshot.child("Unit").getValue(String.class);
                                if (downloadUrl != null) {
                                    // Download and play the file using the download URL
                                    downloadAndPlayMusic(downloadUrl,fileName);

                                boolean result= databaseHelper.insertData(BookName,downloadUrl,fileType,unit,fileName);
                                if(result== true){
                                    Toast.makeText(secondActivity, "File Dowloanded and saved", Toast.LENGTH_SHORT).show();
                                }
                                }
                                else {
                                    Log.d("DownloadError","URI is empty");
                                    Toast.makeText(secondActivity, "Could not be downloaded Dowloanded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle any errors
                            Log.e("FirebaseError", databaseError.getMessage());
                        }
                    });
                }
            }


            private void downloadAndPlayMusic (String downloadUrl,String filename) {
                // Create a local file in your app's private storage
                File privateDir = secondActivity.getFilesDir();
                String fileName = filename; // Name it as you like
                File outputFile = new File(privateDir, fileName);
                if (outputFile.exists()) {
                    // File already exists in private storage, play it
                   // playMusic(outputFile.getAbsolutePath());
                    Toast.makeText(secondActivity, "File is already downloaded", Toast.LENGTH_SHORT).show();
                } else {

                    // Download the file from Firebase Storage
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageReference = storage.getReferenceFromUrl(downloadUrl);

                    storageReference.getFile(outputFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // The file has been downloaded to app's private storage
                                    // Now you can play the file using a media player
//                                    playMusic(outputFile.getAbsolutePath());
                                    Toast.makeText(secondActivity, "File Downloaded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle the failure to download the file
                                    Log.e("DownloadError", e.getMessage());
                                }
                            });
                }
            }


//            private void playMusic (String filePath){
//
//                try {
//                    mediaPlayer.setDataSource(filePath);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                } catch (IOException e) {
//                    Log.e("MediaPlayerError", e.getMessage());
//                }
//            }


        }
    }




