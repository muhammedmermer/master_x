package com.masterofanalysis.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.masterofanalysis.Constants;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.masterofanalysis.api.SliderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_homex extends Fragment implements View.OnClickListener {

    private ViewPager2 viewPager;
    private SliderAdapter sliderAdapter;
    private List<String> imageUrls;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;
    private FirebaseAuth auth;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homex, container, false);

        auth = FirebaseAuth.getInstance();
        apiService = ApiClient.getClient().create(ApiService.class);

        // ViewPager2 ve SliderAdapter ayarları
        viewPager = view.findViewById(R.id.view_pager);
        sliderAdapter = new SliderAdapter(getContext(), imageUrls);
        viewPager.setAdapter(sliderAdapter);

        fetchSliderImages();

        // SliderRunnable tanımlaması
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = currentItem + 1;
                if (nextItem >= sliderAdapter.getItemCount()) {
                    nextItem = 0;
                }
                viewPager.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 3000); // 3 saniye
            }
        };

        // ViewPager2 sayfa değişikliği dinleyicisi
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // 3 saniye
            }
        });

        // Kartları bulma
        View cardUcretsiz = view.findViewById(R.id.card_ucretsiz);
        View cardKredili = view.findViewById(R.id.card_kredili);
        View cardVip = view.findViewById(R.id.card_vip);
        View cardOdeme = view.findViewById(R.id.card_odeme);
        View cardProfil = view.findViewById(R.id.card_profil);

        // Kartlara tıklama olaylarını atama
        cardUcretsiz.setOnClickListener(this);
        cardKredili.setOnClickListener(this);
        cardVip.setOnClickListener(this);
        cardOdeme.setOnClickListener(this);
        cardProfil.setOnClickListener(this);

        return view;
    }

    private void fetchSliderImages() {
        Call<SliderResponse> call = apiService.getSliderImages("get_slider", Constants.API_KEY);
        call.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SliderResponse", "API Response: " + response.body().toString());
                    imageUrls = response.body().getSliderImageUrls();
                    sliderAdapter = new SliderAdapter(getContext(), imageUrls);
                    viewPager.setAdapter(sliderAdapter);
                    sliderHandler.postDelayed(sliderRunnable, 3000);
                } else {
                    Log.d("SliderResponse", "Response not successful: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("SliderResponse", "API Call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_ucretsiz:
                replaceFragment(new fragment_ucretsiz(), "ucretsiz");
                break;
            case R.id.card_kredili:
                replaceFragment(new fragment_kredili(), "kredili");
                break;
            case R.id.card_vip:
                replaceFragment(new fragment_seckin(), "vip");
                break;
            case R.id.card_odeme:
                replaceFragment(new fragment_odeme(), "odeme");
                break;
            case R.id.card_profil:
                replaceFragment(new fragment_Profil(), "profil");
                break;
        }
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}
