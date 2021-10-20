package sg.carpark.looq.data.repository;

import sg.carpark.looq.data.model.LoginRequest;
import sg.carpark.looq.data.model.LoginResponse;
import sg.carpark.looq.data.model.User;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

/**
 * Created by TED on 29-Nov-20
 */
public class LoginRepository {

    private static LoginRepository instance;

    public LoginRepository() {
    }

    public static LoginRepository getInstance() {
        if(instance == null){
            synchronized (LoginRepository.class){
                if(instance == null){
                    instance = new LoginRepository();
                }
            }
        }
        return instance;
    }

    public Single<LoginResponse> doCallOauthToken(LoginRequest.ServerLoginRequest request){
        //todo call api token

        return null;
    }

    public Single<User> doCallUserInfo(){
        //todo call api user info
        return new Single<User>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super User> observer) {

            }
        };
    }

    public void doSaveUser() {
    }
}
