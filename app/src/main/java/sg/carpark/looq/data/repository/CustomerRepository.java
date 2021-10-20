package sg.carpark.looq.data.repository;

import android.content.Context;

import java.util.ArrayList;

import sg.carpark.looq.data.model.Customer;


/**
 * Created by TED on 07-Sep-20
 */
public class CustomerRepository {
    private static CustomerRepository instance;
    private Context context;
//    private CustomerDao customerDao;

    public CustomerRepository(Context context) {
        this.context = context;
//        customerDao = AppDatabase.getInstance().customerDao();
    }

    /*Singleton
    * jadi ga kebuat berkali kali*/
    public static CustomerRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CustomerRepository(context);
        }

        return instance;
    }

    public ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        for(int i = 0; i<24 ; i++){

            Customer customer = new Customer();
            customer.setId("ID000" + (i+1));
            customer.setName("Customer " + (i+1));
            customers.add(customer);
        }

        /*Contoh Retrofit tarik data
        CustomerService customerService = ApiClient.getClientWithToken(context).create(CustomerService.class);
        Call<MessageResponse> call = customerService.getCustomer();

        try {
            Response<MessageResponse> response = call.execute();
            if (response != null && response.body() != null && response.body().getResult() != null) {
                Customer[] customerArray = Utils.linkedTreeMapToGSON(response.body().getResult(), Customer[].class);
                Collections.addAll(customers, customerArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        return customers;
    }

}
