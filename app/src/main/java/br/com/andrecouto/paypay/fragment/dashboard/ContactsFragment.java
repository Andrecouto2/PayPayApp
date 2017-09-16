package br.com.andrecouto.paypay.fragment.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.adapter.ContactsAdapter;
import br.com.andrecouto.paypay.entity.Contacts;
import br.com.andrecouto.paypay.fragment.BaseLoggedFragment;
import br.com.andrecouto.paypay.threads.ContactsAsyncTask;
import br.com.andrecouto.paypay.threads.ContactsAsyncTask.Action;
import br.com.andrecouto.paypay.util.PermissionUtils;
import br.com.andrecouto.paypay.view.custom.HeaderView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ContactsFragment extends BaseLoggedFragment {

    @BindView(R.id.header_view)
    protected HeaderView headerView;
    @BindView(R.id.rcyview_contacts)
    protected RecyclerView recyclerViewContacts;
    @BindView(R.id.search_view_contacts)
    protected SearchView search;
    protected ContactsAdapter contactsAdapter;
    protected List<Contacts> contactsList;
    protected String[] permission = new String[] {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, v);
        registerForContextMenu(recyclerViewContacts);
        setHasOptionsMenu(true);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String stext) {
                contactsAdapter.filter(stext);
                return false;
            }
        });
        // Solicita as permissões
        PermissionUtils.validate(getActivity(), 0, permission);
        setContactList();
        return v;
    }

    private void setContactList() {
        if (PermissionUtils.hasPermission(getActivity(), new String[] {
                Manifest.permission.READ_CONTACTS})) {
            new ContactsAsyncTask(getActivity(), new Action() {
                @Override
                public void getResult(List<Contacts> result) {
                    if (result != null && result.size() > 0) {
                        contactsList = result;
                        contactsAdapter = new ContactsAdapter(getActivity(), result);
                        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerViewContacts.setAdapter(contactsAdapter);
                    }
                }
            }).execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada
                Toast.makeText(getActivity(), "Necessário ativação.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        setContactList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contacts, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemPosition = contactsAdapter.getPosition();
        switch (item.getItemId()) {
            case R.id.menu_call:
                if (PermissionUtils.hasPermission(getActivity(), new String[]{
                        Manifest.permission.CALL_PHONE})) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contactsList.get(itemPosition).getPhone()));
                    startActivity(callIntent);
                }
                break;
            case R.id.menu_share:
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, contactsList.get(itemPosition).getPhone());
                startActivity(whatsappIntent);
                break;
        }
        return super.onContextItemSelected(item);
    }

}
