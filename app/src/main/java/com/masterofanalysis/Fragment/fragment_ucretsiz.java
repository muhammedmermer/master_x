package com.masterofanalysis.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.masterofanalysis.Adapter.MaclarAdapter;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.Datamatches;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_ucretsiz extends Fragment {
    RecyclerView rvm;
    MaclarAdapter adapter;
    private List<Datamatches.Matches> matchesList;
    View view;
    private FirebaseAuth auth;
    private Context mContext;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    public void checkIfFragmentAttached(fragment_kredili.ContextOperation operation) {
        if (isAdded() && getContext() != null) {
            operation.performOperation(requireContext());
        }
    }
    public interface ContextOperation {
        void performOperation(Context context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__ucretsiz , container , false);
        if (mContext != null) {
            Function.showLoading(getActivity(), mContext);
            auth = FirebaseAuth.getInstance();
            rvm = view.findViewById(R.id.rvm);
            rvm.setHasFixedSize(true);
            rvm.setLayoutManager(new LinearLayoutManager(mContext));
            matchesList = new ArrayList<>();

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<Datamatches> call = apiService.GetUcretsiz("ucretsiz_maclar", auth.getUid(), Function.getTimezone(), Constants.API_KEY);
            call.enqueue(new Callback<Datamatches>() {
                @Override
                public void onResponse(Call<Datamatches> call, Response<Datamatches> response) {
                    checkIfFragmentAttached(new fragment_kredili.ContextOperation() {
                        @Override
                        public void performOperation(Context context) {
                            try {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                                        Datamatches model = response.body();
                                        matchesList = model.getData();
                                        setadapter();
                                        Function.dismissLoading(getActivity());
                                    } else {
                                        Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                                    }
                                } else {
                                    Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                                }
                            } catch (Exception e) {
                                Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Call<Datamatches> call, Throwable t) {
                    Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                }
            });
        }
        return view;
    }
    private void setadapter(){
        adapter = new MaclarAdapter(mContext, matchesList);
        rvm.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}