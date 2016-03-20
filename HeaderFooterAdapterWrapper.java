package org.polaric.music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public abstract class HeaderFooterAdapterWrapper extends 
RecyclerView.Adapter {
    private static final int TYPE_HEADER = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private static final int TYPE_ADAPTEE_OFFSET = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, 
int viewType) {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateBasicItemViewHolder(parent, viewType - 
TYPE_ADAPTEE_OFFSET);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int 
position) {
        if (position == 0 && holder.getItemViewType() == TYPE_HEADER) {
            onBindHeaderView(holder, position);
        } else if (position == getBasicItemCount() && 
holder.getItemViewType() == TYPE_FOOTER) {
            onBindFooterView(holder, position);
        } else {
            onBindBasicItemViewHolder(holder, position - (useHeader() ? 
1 : 0));
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = getBasicItemCount();
        if (useHeader()) {
            itemCount += 1;
        }
        if (useFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && useHeader()) {
            return TYPE_HEADER;
        }
        if (position == getBasicItemCount() && useFooter()) {
            return TYPE_FOOTER;
        }
        if (getBasicItemType(position) >= Integer.MAX_VALUE - 
TYPE_ADAPTEE_OFFSET) {
            new IllegalStateException("Adapter Wrapper ffsets your 
BasicItemType by " + TYPE_ADAPTEE_OFFSET + ".");
        }
        return getBasicItemType(position) + TYPE_ADAPTEE_OFFSET;
    }

    public boolean useHeader() {
        //Override this in your subclass and return true to use a header
        return false;
    }

    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup 
parent, int viewType) {
        //Override this in your subclass and return a view holder 
containing your header to use a header
        return null;
    }

    public void onBindHeaderView(RecyclerView.ViewHolder holder, int 
position) {
        //Override this in your subclass to interact with your header
    }

    public boolean useFooter() {
        //Override this in your subclass and return true to use a footer
        return false;
    }

    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup 
parent, int viewType) {
        //Override this in your subclass and return a view holder 
containing your footer to use a footer
        return null;
    }

    public void onBindFooterView(RecyclerView.ViewHolder holder, int 
position) {
        //Override this in your subclass to interact with your footer
    }

    public abstract RecyclerView.ViewHolder 
onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void 
onBindBasicItemViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract int getBasicItemCount();

    /**
     * make sure you don't use [Integer.MAX_VALUE-1, Integer.MAX_VALUE] 
as BasicItemViewType
     *
     * @param position
     * @return
     */
    public int getBasicItemType(int position) {
        //Override this in your subclass if you use custom view-type 
integers
        return 5;
    }

    public class BasicViewHolder extends RecyclerView.ViewHolder {
        public BasicViewHolder(View v) {
            super(v);
        }
    }

}
