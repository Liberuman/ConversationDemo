package com.zhinanmao.conversationdemo;

import android.app.Activity;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView conversationList;
    private List<MessageBean> listData = new ArrayList<>();

    private final int MSG_TYPE_SEND = 0;
    private final int MSG_TYPE_RECEIVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conversationList = (ListView) findViewById(R.id.converation_list);
        initData();
        conversationList.setAdapter(new ConversationAdapter());
    }

    private void initData() {
        listData.add(new MessageBean(0, "五一去哪玩"));
        listData.add(new MessageBean(1, "还不知道呢"));
        listData.add(new MessageBean(1, "你呢？什么打算"));
        listData.add(new MessageBean(0, "没什么打算"));
        listData.add(new MessageBean(0, "感觉出去人好多"));
        listData.add(new MessageBean(1, "去上海怎么样"));
        listData.add(new MessageBean(0, "上海好玩吗？"));
        listData.add(new MessageBean(1, "不知道，没去过"));
        listData.add(new MessageBean(1, "应该还不错吧"));
        listData.add(new MessageBean(0, "行，那去看看"));
        listData.add(new MessageBean(0, "到时候电话联系"));
        listData.add(new MessageBean(1, "OK"));
    }

    private class ConversationAdapter extends BaseAdapter {
        /**
         * 布局类型的数量
         */
        private final int VIWE_TYPE_COUNT = 2;

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return VIWE_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            return listData.get(position).from;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OtherViewHolder otherHolder = null;
            MyViewHolder myHolder = null;
            int type = listData.get(position).from;
            if (convertView == null) {
                switch (type) {
                    case MSG_TYPE_SEND:
                        myHolder = new MyViewHolder();
                        convertView = getLayoutInflater().from(MainActivity.this).inflate(R.layout.item_conversation_right_layout, parent, false);
                        myHolder.icon = (ImageView) convertView.findViewById(R.id.user_icon);
                        myHolder.contentText = (TextView)convertView.findViewById(R.id.content_text);
                        convertView.setTag(myHolder);
                        break;
                    case MSG_TYPE_RECEIVE:
                        otherHolder = new OtherViewHolder();
                        convertView = getLayoutInflater().from(MainActivity.this).inflate(R.layout.item_conversation_left_layout, parent, false);
                        otherHolder.icon = (ImageView) convertView.findViewById(R.id.user_icon);
                        otherHolder.contentText = (TextView)convertView.findViewById(R.id.content_text);
                        convertView.setTag(otherHolder);
                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {
                    case MSG_TYPE_SEND:
                        myHolder = (MyViewHolder) convertView.getTag();
                        break;
                    case MSG_TYPE_RECEIVE:
                        otherHolder = (OtherViewHolder) convertView.getTag();
                        break;
                    default:
                        break;
                }
            }

            if (type == MSG_TYPE_RECEIVE) {
                setItemValue(otherHolder, position);
            } else {
                setItemValue(myHolder, position);
            }

            return convertView;
        }

        /**
         * 设置子布局的内容
         * @param viewHolder
         * @param position
         */
        private void setItemValue(ViewHolder viewHolder, int position) {
            viewHolder.contentText.setText(listData.get(position).content);
            if (listData.get(position).from == 0) {
                viewHolder.icon.setImageResource(R.drawable.my_icon);
            } else {
                viewHolder.icon.setImageResource(R.drawable.other_icon);
            }
        }
    }

    private class ViewHolder {
        public ImageView icon;
        public TextView contentText;
    }

    private class OtherViewHolder extends ViewHolder {

    }

    private class MyViewHolder extends ViewHolder  {

    }

    private class MessageBean {
        public int icon;
        /**
         *  0表示发送的消息, 1表示收到的消息
         */
        public int from;
        public String content;

        public MessageBean(int from, String content) {
            this.icon = R.mipmap.ic_launcher;
            this.from = from;
            this.content = content;
        }
    }
}
