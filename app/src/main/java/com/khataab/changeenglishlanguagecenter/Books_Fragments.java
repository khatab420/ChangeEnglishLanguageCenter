package com.khataab.changeenglishlanguagecenter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Books_Fragments extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_books__fragments, container, false);


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageButton btnStartUp = view.findViewById(R.id.btn_start_up);
        ImageButton btnOpenUp = view.findViewById(R.id.btn_open_up);
        ImageButton btnWarmUp = view.findViewById(R.id.btn_warm_up);
        ImageButton btnRunUp = view.findViewById(R.id.btn_run_up);
        ImageButton btnRoundOne = view.findViewById(R.id.btn_round_one);
        ImageButton btnRoundTwo = view.findViewById(R.id.btn_round_two);
        ImageButton btnRoundThree = view.findViewById(R.id.btn_round_three);
        ImageButton btnRoundFour = view.findViewById(R.id.btn_round_four);
        ImageButton btnRoundFive = view.findViewById(R.id.btn_round_five);
        ImageButton btnRoundSix = view.findViewById(R.id.btn_round_six);
        ImageButton btnPioneer = view.findViewById(R.id.btn_pioneer);
        ImageButton btnPioneerPlus = view.findViewById(R.id.btn_pioneer_plus);
        View progressBar;

        btnStartUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomPopupMenu(v,"Start Up");

            }
        });

        btnOpenUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_open_up" ImageButton
                showCustomPopupMenu(v,"Open up");

            }
        });

        btnWarmUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_warm_up" ImageButton
                showCustomPopupMenu(v,"Warm Up");

            }
        });

        btnRunUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_run_up" ImageButton
                showCustomPopupMenu(v,"Run Up");

            }
        });

        btnRoundOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_round_one" ImageButton
                showCustomPopupMenu(v,"Round One");

            }
        });

        btnRoundTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_round_two" ImageButton
                showCustomPopupMenu(v,"Round Two");

            }
        });

        btnRoundThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_round_three" ImageButton
                showCustomPopupMenu(v,"Round Three");


            }
        });

        btnRoundFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_round_four" ImageButton
                showCustomPopupMenu(v,"Round Four");


            }
        });

        btnRoundFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_round_five" ImageButton
                showCustomPopupMenu(v,"Round Five");


            }
        });

        btnRoundSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_round_six" ImageButton
                showCustomPopupMenu(v,"Round Six");


            }
        });

        btnPioneer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_pioneer" ImageButton
                showCustomPopupMenu(v,"Pioner");


            }
        });

        btnPioneerPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the "btn_pioneer_plus" ImageButton
                showCustomPopupMenu(v,"Pioner Plus");


            }
        });

    }



    private void showCustomPopupMenu(View view, String bookName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View customMenuView = getLayoutInflater().inflate(R.layout.custom_menu, null);
        builder.setView(customMenuView);

        RecyclerView recyclerView = customMenuView.findViewById(R.id.custom_menu_recycler_view);

        List<MenuItemData> menuItems = new ArrayList<>();
        menuItems.add(new MenuItemData("Listening", R.drawable.listening_icon));
        menuItems.add(new MenuItemData("Reading", R.drawable.reading_icon));
        menuItems.add(new MenuItemData("Speaking", R.drawable.speaking));
        menuItems.add(new MenuItemData("Grammar", R.drawable.grammar_icon));
        menuItems.add(new MenuItemData("Vocabulary", R.drawable.vocabulary_icon));
        menuItems.add(new MenuItemData("More Listening", R.drawable.books));
        CustomMenuAdapter adapter = new CustomMenuAdapter(menuItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_spacing); // Define your spacing in dimensions
        recyclerView.addItemDecoration(new CustomItemDecoration(spacingInPixels));

        AlertDialog customMenuDialog = builder.create();
        customMenuDialog.setOnShowListener(dialog -> {
            animateDialog(customMenuView);
        });

        adapter.setOnItemClickListener(position -> {
            MenuItemData clickedItem = menuItems.get(position);
            if (clickedItem.getText().equals("More Listening")) {
                showSecondCustomPopupMenu(view, bookName);
                customMenuDialog.dismiss();
            } else {
                navigateToMusicListFragment(bookName, clickedItem.getText());
                customMenuDialog.dismiss();
            }
        });

        customMenuDialog.show();
    }

    private void animateDialog(View view) {
        view.setPivotX(0); // Set the pivot for scaling from left
        view.setPivotY(0); // Set the pivot for scaling from top
        view.setScaleX(0); // Initial scale
        view.setScaleY(0); // Initial scale

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1);
        alphaAnimator.setDuration(500); // Set the duration of the fade-in animation in milliseconds
        alphaAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1);
        scaleXAnimator.setDuration(500); // Set the duration of the scaling animation in milliseconds
        scaleXAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1);
        scaleYAnimator.setDuration(500); // Set the duration of the scaling animation in milliseconds
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());

        alphaAnimator.start();
        scaleXAnimator.start();
        scaleYAnimator.start();
    }



    private void showSecondCustomPopupMenu(View view ,String bookName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View customMenuView = getLayoutInflater().inflate(R.layout.custom_menu, null);
        builder.setView(customMenuView);

        RecyclerView recyclerView = customMenuView.findViewById(R.id.custom_menu_recycler_view);

        // Create a list of menu items (MenuItemData should be a custom data class)
        List<MenuItemData> menuItems = new ArrayList<>();
        menuItems.add(new MenuItemData("Starter", R.drawable.listening_icon));
        menuItems.add(new MenuItemData("Intermediate", R.drawable.books));
        menuItems.add(new MenuItemData("Upper Intermediate", R.drawable.startup));
        menuItems.add(new MenuItemData("Advanced", R.drawable.books));


        // Add more menu items as needed

        More_Listening_Custom_Menu_Adapter adapter = new More_Listening_Custom_Menu_Adapter(menuItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        AlertDialog SecondcustomMenuDialog = builder.create();

        adapter.setOnItemClickListener(new More_Listening_Custom_Menu_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle the item click here
                MenuItemData clickedItem = menuItems.get(position);
                // You can perform actions based on the clicked item
                navigateToMusicListFragment(bookName,clickedItem.getText());
                SecondcustomMenuDialog.hide();


            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        SecondcustomMenuDialog.show();


    }

    public void navigateToMusicListFragment(String bookName, String typeName) {
        Music_List_Fragment fragment = new Music_List_Fragment();
        Bundle args = new Bundle();
        args.putString("bookName", bookName);
        args.putString("typeName", typeName);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null); // Add to back stack

        fragmentTransaction.commit();
    }




}