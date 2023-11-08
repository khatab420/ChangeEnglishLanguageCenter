package com.khataab.changeenglishlanguagecenter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


public class Music_List_Fragment extends Fragment {
    private RecyclerView recyclerView;
   AudioAdapter adapter;
   Database_Helper databaseHelper;
   String bookName;
   String typeName;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_music__list_, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Populate your data
        databaseHelper=new Database_Helper(getActivity());
        Bundle args = getArguments();
        if (args != null) {
            bookName = args.getString("bookName", "");
          typeName = args.getString("typeName", "");

            // Set the text of TextViews
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("Name: "+bookName+",Typname:  "+typeName);
            Toast.makeText(getActivity(), ""+stringBuffer, Toast.LENGTH_SHORT).show();

        }


        // Add more items as needed

        // Create an adapter and set it to the RecyclerView
        ArrayList<AudioModel> musicFileNames = new ArrayList<>();


     if(isNetworkConnected()){
         DatabaseReference metadataRef = FirebaseDatabase.getInstance().getReference("MusicMetadata");

         metadataRef.orderByChild("BookName").equalTo(bookName)
                 .addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         musicFileNames.clear(); // Clear the existing list
                         for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                             // Check if the 'Type' is also "Listening"
                             String fileName = childSnapshot.child("FileName").getValue(String.class);
                             String BookName = childSnapshot.child("BookName").getValue(String.class);
                             String fileType = childSnapshot.child("Type").getValue(String.class);
                             String downloadUrl = childSnapshot.child("DownloadUrl").getValue(String.class);
                             String unit = childSnapshot.child("Unit").getValue(String.class);

                             if (fileName != null && fileType.equals("Listening")) {
                                 musicFileNames.add(new AudioModel(downloadUrl, fileName, fileType, unit, BookName));
                             }
                         }
                         adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {
                         Log.e("errorMIne", error.getMessage());
                     }
                 });





     }else{
         Cursor cursor=databaseHelper.getAllData();


         if(cursor!=null && cursor.getCount()>0) {
             while (cursor.moveToNext()) {
                 String fileName = cursor.getString(4);
                 String BookName = cursor.getString(0);
                 String fileType = cursor.getString(2);
                 String downloadUrl = cursor.getString(1);
                 String unit = cursor.getString(3);
                 musicFileNames.add(new AudioModel(downloadUrl, fileName, fileType, unit, BookName));
             }

         }else{
             Toast.makeText(getActivity(), "No file to get", Toast.LENGTH_SHORT).show();
         }


     }

        adapter = new AudioAdapter(musicFileNames,getActivity());
        recyclerView.setAdapter(adapter);




        return view;
    }
    @Override
    public void onPause() {
        super.onPause();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}

