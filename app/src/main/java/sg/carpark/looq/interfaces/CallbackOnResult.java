package sg.carpark.looq.interfaces;

/**
 * Created by Samuel Gunawan on 10/25/2017.
 */

public interface CallbackOnResult<T>{
    void onFinish(T result);
    void onFailed();
}
