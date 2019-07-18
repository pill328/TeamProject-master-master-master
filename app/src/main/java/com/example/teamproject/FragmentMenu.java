package com.example.teamproject;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentMenu extends Fragment {
    View view;
    private TextView menu;
    String url = "http://192.168.0.29:8081/";
    ContentValues info = new ContentValues();
    String Storenum="";

    public FragmentMenu() {
    }
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof Scene2){
            Storenum = ((Scene2) getActivity()).getData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_fragment,container,false);
        info.put("hello",Storenum);
        NetworkTask networkTask = new NetworkTask(url,info);
        menu = (TextView) view.findViewById(R.id.menu);
        networkTask.execute();
        return view;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues pageParsed2;

        public NetworkTask(String url, ContentValues pageParsed2) {

            this.url = url;
            this.pageParsed2 = pageParsed2;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String result;          // 요청 결과를 저장할 변수
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request2(url, pageParsed2);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력
            super.onPostExecute(s);

            menu.setText(s);
        }
    }
}
