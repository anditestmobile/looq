package sg.carpark.looq.utils.interfaces;

/**
 * Created by Samuel Gunawan on 12/12/2017.
 */

public interface LocationCallback<T,A> {
    void onFinish(T address, A coordinate);
}
