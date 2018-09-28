package com.example.vitaly.gb_android_popular_libraries.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitaly.gb_android_popular_libraries.R;
import com.example.vitaly.gb_android_popular_libraries.presenter.GitHubParserPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> implements ReposListView {

    private GitHubParserPresenter.ReposListPresenter listPresenter;

    public ReposAdapter(GitHubParserPresenter.ReposListPresenter listPresenter) {
        this.listPresenter = listPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repos, parent, false));
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder holder, int position) {
        listPresenter.bindViewAt(position, holder);
    }

    @Override
    public int getItemCount() {
        return listPresenter.getReposCount();
    }

    @Override
    public void refreshView() {
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ReposRowView {
        @BindView(R.id.tv_repos) TextView reposTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setReposName(String name) {
            reposTextView.setText(name);
        }
    }
}
