package io.bloco.cardcase.presentation.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.bloco.cardcase.R;
import io.bloco.cardcase.common.di.PerActivity;
import io.bloco.cardcase.data.models.Card;
import io.bloco.cardcase.presentation.home.CardDetailDialog;

@PerActivity
public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static class ViewType {
    private static final int NORMAL = 0;
    private static final int FOOTER = 1;
  }

  private final CardDetailDialog cardDetailDialog;
  private List<Card> cards;
  private boolean showLoader;

  @Inject
  public CardAdapter(CardDetailDialog cardDetailDialog) {
    this.cards = new ArrayList<>();
    this.cardDetailDialog = cardDetailDialog;
    this.showLoader = false;
  }

  public void showLoader() {
    this.showLoader = true;
  }

  @Override
  public int getItemCount() {
    return cards.size() + (showLoader ? 1 : 0);
  }

  @Override
  public int getItemViewType(int position) {
    if (position < cards.size()) {
      return ViewType.NORMAL;
    } else {
      return ViewType.FOOTER;
    }
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view;
    switch (viewType) {
      case ViewType.FOOTER:
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.card_list_loader, parent, false);
        return new FooterViewHolder(view);

      case ViewType.NORMAL:
      default:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view, cardDetailDialog);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof CardViewHolder) {
      Card card = cards.get(position);
      ((CardViewHolder) holder).bind(card);
    }
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  private static class FooterViewHolder extends RecyclerView.ViewHolder {

    FooterViewHolder(View view) {
      super(view);
    }
  }
}
