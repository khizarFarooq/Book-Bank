package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.namaltechnologysolutions.bunny.bookbank.Language_Customization.Language_Helper;
import com.namaltechnologysolutions.bunny.bookbank.R;
import java.util.Locale;

public class View_Setting_Fragment extends DialogFragment implements View.OnClickListener{
    // Objects Declaration
    TextView theme,select_A_Theme,language,select_Language;
    private String selected_Theme;
    private view_Setting_Fragment_Interface mListener;

    public View_Setting_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
//            selected_Theme="Dark";
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_setting, container, false);
        getActivity().setTitle(R.string.view_setting_drawer);
        // Objects Initialization
//        theme=view.findViewById(R.id.theme);
//        theme.setOnClickListener(this);
//        select_A_Theme=view.findViewById(R.id.select_A_Theme);
//        select_A_Theme.setOnClickListener(this);
        language=view.findViewById(R.id.language);
        language.setOnClickListener(this);
        select_Language=view.findViewById(R.id.select_Language);
        select_Language.setOnClickListener(this);
        return view;
    }
// To show language selection dialog
    private void show_Language_Selection_Dialog() {
        final String[] theme=getActivity().getResources().getStringArray(R.array.language);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Language");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(),"Your language has been changed.",Toast.LENGTH_SHORT).show();
                mListener.view_Setting_Fragment_Interface_Method_Language_Change();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setSingleChoiceItems(R.array.language, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which_Theme_User_Selected) {
                selected_Theme=theme[which_Theme_User_Selected];
                if(selected_Theme.equals("English")){
                    Language_Helper.storeUserLanguage(getActivity(), "en");
                    Language_Helper.updateLanguage(getActivity(), "en");
                   // setLocale("en");
                }
                else if(selected_Theme.equals("Urdu")){
                    Language_Helper.storeUserLanguage(getActivity(), "ur");
                    Language_Helper.updateLanguage(getActivity(), "ur");
                    //setLocale("ur");
                }

            }
        });
        builder.show();
    }

//    private void setLocale(String lang) {
//        Locale language=new Locale(lang);
//        Locale.setDefault(language);
//        Configuration configuration=new Configuration();
//        configuration.locale=language;
//        getActivity().getResources().updateConfiguration(configuration,getActivity().getResources().getDisplayMetrics());
//        getFragmentManager().beginTransaction().detach(View_Setting_Fragment.this).attach(View_Setting_Fragment.this).commit();
//    }

//    private void show_Theme_Selection_Dialog() {
//        final String[] theme=getActivity().getResources().getStringArray(R.array.theme);
//        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//        builder.setTitle("Choose Theme");
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getActivity(),"Selected theme "+selected_Theme,Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        builder.setSingleChoiceItems(R.array.theme, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which_Theme_User_Selected) {
//                selected_Theme=theme[which_Theme_User_Selected];
//                if(selected_Theme.equals("Dark")){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    getActivity().setTheme(R.style.darkMode);
//                }
//                else{
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    getActivity().setTheme(R.style.AppTheme);
//                }
//
//            }
//        });
//        builder.show();
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof view_Setting_Fragment_Interface) {
            mListener = (view_Setting_Fragment_Interface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement view_Setting_Fragment_Interface_mListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
// On click listener implementation
    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.theme:
//                show_Theme_Selection_Dialog();
//                break;
//            case R.id.select_A_Theme:
//                show_Theme_Selection_Dialog();
//                break;
            case R.id.language:
                show_Language_Selection_Dialog();
                break;
            case R.id.select_Language:
                show_Language_Selection_Dialog();
                break;
        }
    }

    public interface view_Setting_Fragment_Interface {
        // TODO: Update argument type and name
        void view_Setting_Fragment_Interface_Method_Language_Change();
    }
}
