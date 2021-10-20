package sg.carpark.looq.utils.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import sg.carpark.looq.R;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.interfaces.CallbackBackgroundResult;
import sg.carpark.looq.utils.interfaces.CallbackOnResult;

/**
 * Created by TED on 28-Jul-20
 */
public class BackgroundHelper {
    private static BackgroundTask<?> backgroundTask;

    /*Background Task*/
    static class BackgroundTask<T> extends AsyncTask<Void, Void, T> {
        private ProgressDialog dialog;
        CallbackBackgroundResult<T> callbackOnBackground;
        CallbackOnResult<T> callbackOnResult;
        private Boolean isFailed = false;
        private String failedMessage;

        BackgroundTask(ProgressDialog dialog, CallbackBackgroundResult<T> callbackOnBackground, CallbackOnResult<T> callbackOnResult) {
            this.dialog = dialog;
            this.callbackOnBackground = callbackOnBackground;
            this.callbackOnResult = callbackOnResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog != null) dialog.show();
        }

        @Override
        protected void onPostExecute(T o) {
            super.onPostExecute(o);
            if (dialog != null) dialog.dismiss();
            if (!isFailed) {
                callbackOnResult.onFinish(o);
            } else {
                callbackOnResult.onFailed();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (dialog != null) dialog.dismiss();
        }

        @Override
        protected T doInBackground(Void[] params) {
            try {
                return callbackOnBackground.onBackground();
            } catch (Exception ignore) {
                failedMessage = ignore.getMessage() != null ? ignore.getMessage() : "unknown error";
                Log.d("UtilBackground", failedMessage);
                Helper.removeItemParam(Constants.BACKGROUND_EXCEPTION);
                Helper.setItemParam(Constants.BACKGROUND_EXCEPTION, failedMessage);
                isFailed = true;
            }
            return null;
        }
    }

    public static <T> void backgroundTask(final ProgressDialog dialog, @NonNull final CallbackBackgroundResult<T> callbackOnBackground, @NonNull final CallbackOnResult<T> callbackOnResult) {
        if (backgroundTask != null) {
            backgroundTask.cancel(true);
        }
        backgroundTask = new BackgroundTask<>(dialog, callbackOnBackground, callbackOnResult);
        backgroundTask.execute();
    }

    public static void toastBackgroundException(Context context) {
        try {
            if(Helper.getItemParam(Constants.BACKGROUND_EXCEPTION) != null){
                String failedMessage = String.valueOf(Helper.getItemParam(Constants.BACKGROUND_EXCEPTION));
                Toast.makeText(context, failedMessage, Toast.LENGTH_SHORT).show();
                Helper.removeItemParam(Constants.BACKGROUND_EXCEPTION);
            }
        }catch (Exception e){
            Log.d("Debug", "toastBackgroundException: " + e.getMessage());
        }
    }

    public static ProgressDialog createProgressDialog(final Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException ignore) {

        }
        dialog.setCancelable(false);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.setContentView(R.layout.layout_progress_bar);
        dialog.dismiss();
        // dialog.setMessage(Message);
        return dialog;
    }


}
