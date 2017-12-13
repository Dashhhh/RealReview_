package com.teamnova.ej.realreview.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.teamnova.ej.realreview.R;
import com.teamnova.ej.realreview.activity.Main;

import java.util.List;

import static com.teamnova.ej.realreview.activity.Main.PLACETYPE_SELECT_COMPLETE_CHECK;

/**
 * Created by ej on 2017-12-13.
 */

public class MainSearch_Placetype_Adapter extends BaseAdapter {


    private Context context;
    private List<String> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public MainSearch_Placetype_Adapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        final int fPosition = position;

        if (convertView == null) {
            convertView = inflate.inflate(R.layout.activity_main__search__placetype_inflate, null);

            viewHolder = new ViewHolder();
            viewHolder.mainSearchListItem = convertView.findViewById(R.id.mainSearchListitem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
/*
        if(convertView.getTag() == 0 ){
            viewHolder.mainSearchListItem.setTextColor(Color.BLUE);
            viewHolder.mainSearchListItem.setMarkdownText("{fa-dot-circle-o} 전체");
        }
*/

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.mainSearchListItem.setText(list.get(position));
        viewHolder.mainSearchListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String userSelectedPlaceType = viewHolder.mainSearchListItem.getText().toString();
                String userSelectedPlaceType = list.get(fPosition);

                Log.d("MainSearchListCheck","viewHolder.mainSearchListItem.getText().toString() : " + viewHolder.mainSearchListItem.getText().toString() );
                Log.d("MainSearchListCheck","list.get(fPosition) : " + list.get(fPosition));
                Log.d("MainSearchListCheck","userSelectedPlaceType : " + userSelectedPlaceType );

                if(userSelectedPlaceType.equals("전체")) {
                    userSelectedPlaceType = "관심 지점";
                }

                Main.USER_SELECT_PLACETYPE = userSelectedPlaceType;
                PLACETYPE_SELECT_COMPLETE_CHECK = true;
                Intent intent = new Intent(context, Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        public com.beardedhen.androidbootstrap.AwesomeTextView mainSearchListItem;
    }

}