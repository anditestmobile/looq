package sg.carpark.looq.utils.helper;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import sg.carpark.looq.R;
import sg.carpark.looq.utils.interfaces.DialogCallback;
import sg.carpark.looq.utils.interfaces.MultipleCallback;

/**
 * Created by TED on 28-Jul-20
 */
public class DialogHelper {
    /*Dialogs (belum materialdialog)*/
    public static Dialog showDialog(Activity activity, View view, DialogCallback<Dialog> initView) {
//        View view = LayoutInflater.from(activity).inflate(resId, activity.findViewById(android.R.id.content), false);

        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(view);
        initView.onCallback(dialog);
        dialog.show();


        return dialog;
    }

    public static BottomSheetDialog showBottomSheetDialog(Activity activity, View view, MultipleCallback<View, BottomSheetDialog> initView) {
//        View view = LayoutInflater.from(activity).inflate(resId, activity.findViewById(android.R.id.content), false);
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.setOnShowListener(dialog1 -> {
            FrameLayout bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            if (bottomSheet == null)
                return;
            bottomSheet.setBackgroundResource(0);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        dialog.setContentView(view);
        initView.onCallback(view, dialog);
        dialog.show();
        return dialog;
    }

}
