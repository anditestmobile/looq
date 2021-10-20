package sg.carpark.looq.ui.events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applandeo.materialcalendarview.EventDay;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import sg.carpark.looq.R;
import sg.carpark.looq.data.model.Featured;
import sg.carpark.looq.data.model.Visit;
import sg.carpark.looq.data.model.VisitExt;

import sg.carpark.looq.databinding.ActivityEventsDetail2Binding;
import sg.carpark.looq.databinding.DialogListVisitBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.detail.DetailActivity;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.drawable.DrawableUtils;
import sg.carpark.looq.utils.helper.Helper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class EventDetailActivity extends BaseActivity {

    private ActivityEventsDetail2Binding binding;
    String imgUrl = "data:image/jpeg;base64,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";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.backgroundForWhite));
        }*/

        binding = ActivityEventsDetail2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        /* starts before 1 month from now */
//        Calendar startDate = Calendar.getInstance();
//        startDate.add(Calendar.MONTH, -1);
//
//        /* ends after 1 month from now */
//        Calendar endDate = Calendar.getInstance();
//        endDate.add(Calendar.MONTH, 1);
//
//        //setup HorizontalCalendar in your Activity through its Builder
//        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
//                .range(startDate, endDate)
//                .datesNumberOnScreen(5)
//                .build();
//
//        //To listen to date change events
//        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
//            @Override
//            public void onDateSelected(Calendar date, int position) {
//                //do something
//            }
//        });

        binding.btnRemindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventDetailActivity.this, "I'll remind you later!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.layoutRowViewEventBanner.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String dummyId = "00001";
//                String dummyTitle = "Barney & Friends";
//                String dummyDescription = "As the nation gears up for post circuit breaker Phase 2 re-opening";
//
//                Featured selectedObject = new Featured(imgUrl, dummyId, dummyTitle, dummyDescription, "link");
//                Helper.setItemParam(Constants.SELECTED_ITEM, selectedObject);
                Intent intent = new Intent(EventDetailActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        Glide.with(this)
                .load(imgUrl)
                .transition(withCrossFade())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                .into(binding.layoutRowViewEventBanner.ivBanner);

        List<VisitExt> visits = new ArrayList<>();
//        VisitExt ext1 = new VisitExt();
//        ext1.setVisit(new Visit("2021-02-22"));
//
//        VisitExt ext2 = new VisitExt();
//        ext1.setVisit(new Visit("2021-02-10"));
//
//        visits.add(ext1);
//        visits.add(ext2);

        binding.calendarView.setEvents(getListEvent(visits));

        binding.calendarView.setOnDayClickListener(eventDay -> {
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = Helper.dateFormatter(Constants.DATA_DATE_FORMAT).parse("2021-02-22");
                date2 = Helper.dateFormatter(Constants.DATA_DATE_FORMAT).parse("2021-02-10");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date1);
            calendar2.setTime(date2);
            if (eventDay != null) {
                if(isSameDay(eventDay.getCalendar(), calendar) || isSameDay(eventDay.getCalendar(), calendar2)){
//                    String eventDate = Utils.dateFormatter(Constants.DATA_DATE_FORMAT).format(eventDay.getCalendar().getTime());

                    List<VisitExt> result = new ArrayList<>();

                    showDialogResult(eventDay.getCalendar().getTime(), result);
                }else{
                    Toast.makeText(this, "No schedule available", Toast.LENGTH_SHORT).show();
                }
//
            }else{
                Toast.makeText(this, "No schedule available", Toast.LENGTH_SHORT).show();
            }
        });

        //init data

        if(Helper.getItemParam(Constants.SELECTED_ITEM) != null){
            Featured selectedEvent = (Featured) Helper.getItemParam(Constants.SELECTED_ITEM);
            binding.layoutRowViewEventBanner.tvBanner.setText(selectedEvent.getTitle() != null ?
                    selectedEvent.getTitle() : null);
            byte[] image = null;
            if(!selectedEvent.getImgUrl().equals("false")){
                image = Base64.decode(selectedEvent.getImgUrl(), Base64.DEFAULT);
            }
            Glide.with(this)
                    .load(image)
                    .transition(withCrossFade())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                    .into(binding.layoutRowViewEventBanner.ivBanner);
        }
    }

    public boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    private void showDialogResult(Date time, List<VisitExt> result) {
        if (dialog == null || !dialog.isShowing()) {
            DialogListVisitBinding dialogBinding = DialogListVisitBinding.
                    inflate(getLayoutInflater(), binding.getRoot(), false);

            View dialogView = dialogBinding.getRoot();

            dialogBinding.tvDate.setText(Helper.dateFormatter(Constants.DISPLAY_DATE_FORMAT).format(time));
            VisitAdapter visitAdapter = new VisitAdapter(this, result);

            initRecycleView(dialogBinding.rvVisitList, visitAdapter, true);

            dialog = new MaterialAlertDialogBuilder(this)
                    .setView(dialogView)
                    .setCancelable(true)
                    .show();
        }
    }

    private List<EventDay> getListEvent(List<VisitExt> visits) {

        visits = new ArrayList<>();
        VisitExt ext1 = new VisitExt();
        ext1.setVisit(new Visit("2021-02-22"));
        ext1.setTotalVisitPlan(6);

        VisitExt ext2 = new VisitExt();
        ext2.setVisit(new Visit("2021-02-10"));
        ext2.setTotalVisitPlan(6);

        visits.add(ext1);
        visits.add(ext2);

        List<EventDay> events = new ArrayList<>();

        for (VisitExt visit : visits) {
            Calendar calendar = Calendar.getInstance();
            Date date = null;
            try {
                date = Helper.dateFormatter(Constants.DATA_DATE_FORMAT).parse(visit.getVisit().getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date);
            events.add(new EventDay(calendar, DrawableUtils.getCircleDrawableWithText(this, String.valueOf(visit.getTotalVisitPlan()))));
        }

        return events;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
