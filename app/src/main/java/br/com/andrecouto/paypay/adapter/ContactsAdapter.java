package br.com.andrecouto.paypay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.entity.Contacts;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsListViewHolder> {

    private List<Contacts> mainInfo;
    private ArrayList<Contacts> arraylist;
    Context context;


    public ContactsAdapter(Context context, List<Contacts> mainInfo) {
        this.mainInfo = mainInfo;
        this.context = context;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(mainInfo);
    }

    public class ContactsListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewUserImage;
        TextView textViewShowName;
        TextView textViewPhoneNumber;

        public ContactsListViewHolder(View itemView) {
            super(itemView);

            textViewShowName = (TextView) itemView.findViewById(R.id.txt_contact_name);
            textViewPhoneNumber = (TextView) itemView.findViewById(R.id.txt_number);
            imageViewUserImage = (ImageView) itemView.findViewById(R.id.img_pic);

        }
    }

    @Override
    public ContactsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent, false);
        ContactsListViewHolder holder = new ContactsListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactsListViewHolder holder, int position) {
        String imagepath = mainInfo.get(position).getImagepath();
        if (imagepath == null) {
            Picasso.with(context).load(R.drawable.person_contacts).into(holder.imageViewUserImage);
        }else {
            Picasso.with(context).load(imagepath).into(holder.imageViewUserImage);
        }
        holder.textViewShowName.setText(mainInfo.get(position).getName());
        holder.textViewPhoneNumber.setText(mainInfo.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return mainInfo.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mainInfo.clear();
        if (charText.length() == 0) {
            mainInfo.addAll(arraylist);
        } else {
            for (Contacts wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mainInfo.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

