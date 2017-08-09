package com.example.practice1;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;



public class UsersAdapter extends ArrayAdapter<User>
{
    public UsersAdapter(Context context, int listType)
{
    super(context, 0,listType);
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }
        TextView displayName = (TextView) convertView.findViewById(R.id.displayName);
        displayName.setText(user.getName());
        return convertView;
    }
}