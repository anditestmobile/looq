package sg.carpark.looq.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import sg.carpark.looq.R;
import sg.carpark.looq.databinding.FragmentProfileBinding;
import sg.carpark.looq.ui.base.BaseFragment;
import sg.carpark.looq.ui.changepassword.ChangePasswordActivity;
import sg.carpark.looq.ui.editprofile.EditProfileActivity;
import sg.carpark.looq.ui.userpoints.UserPointsActivity;
import sg.carpark.looq.utils.helper.Helper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by TED on 23-Nov-20
 */
public class ProfileFragment extends BaseFragment {
    private FragmentProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(createXSharedTransition(true));
        setReenterTransition(createXSharedTransition(false));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
//        ((MainActivity) getActivity()).getBinding().toolbarTitle.setText("PROFILE");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        binding.btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        binding.btnChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        binding.btnUserPoints.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UserPointsActivity.class);
            startActivity(intent);
        });

        binding.btnLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Are you sure want to logout?");
            builder1.setCancelable(true);

            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            logOut();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.tvEmail.setText(user.getEmail());
        binding.tvUsername.setText(user.getName());
        try {
            byte[] imageByte = Helper.generateBarcodeToByte(user.getEmail());

            Glide.with(requireContext())
                    .load(imageByte)
                    .transition(withCrossFade())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                    .into(binding.ivQrcode);

            byte[] image = null;
            if(!user.getImage_1920().equals("false")){
                image = Base64.decode(user.getImage_1920(), Base64.DEFAULT);
            }
            Glide.with(requireContext())
                    .load(image)
                    .transition(withCrossFade())
                    .circleCrop()
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                    .into(binding.ivProfile);

        } catch (Exception e) {
            Log.e("QRCode", "error: " + e.getMessage());
        }
    }
}
