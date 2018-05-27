package com.gnoemes.shikimoriapp.presentation.view.profile.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.BaseProfileItem;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileAction;
import com.gnoemes.shikimoriapp.entity.user.presentation.profile.ProfileHeadItem;
import com.gnoemes.shikimoriapp.utils.imageloader.ImageLoader;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileHeadAdapterDelegate extends AdapterDelegate<List<BaseProfileItem>> {

    private ImageLoader imageLoader;
    private ProfileCallback callback;

    public ProfileHeadAdapterDelegate(ImageLoader imageLoader, ProfileCallback callback) {
        this.imageLoader = imageLoader;
        this.callback = callback;
    }

    @Override
    protected boolean isForViewType(@NonNull List<BaseProfileItem> items, int position) {
        return items.get(position) instanceof ProfileHeadItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_head, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<BaseProfileItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ProfileHeadItem item = (ProfileHeadItem) items.get(position);
        viewHolder.bind(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint)
        ConstraintLayout layout;
        @BindView(R.id.ignore)
        ImageView ignore;
        @BindView(R.id.history)
        TextView history;
        @BindView(R.id.bans)
        ImageView bans;
        @BindView(R.id.add_friend)
        ImageView addFriend;
        @BindView(R.id.message)
        ImageView sendMessage;
        @BindView(R.id.image_avatar)
        CircleImageView avatar;
        @BindView(R.id.nickname)
        TextView nickname;
        @BindView(R.id.last_online)
        TextView lastOnline;
        @BindView(R.id.info_list)
        TextView infoList;
        @BindView(R.id.text_about)
        TextView about;
        @BindView(R.id.progress)
        SpinKitView progress;

        private Drawable banned;
        private Drawable notBanned;

        private Drawable friend;
        private Drawable notFriend;

        private Drawable ignored;
        private Drawable notIgnored;

        private Drawable message;
        private Drawable box;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            Drawable card = DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(R.drawable.card)
                    .withAttributeColor(R.attr.colorBackgroundContent)
                    .tint()
                    .get();

            layout.setBackground(card);
            initDrawables();
        }

        public void bind(ProfileHeadItem item) {
            if (item.getId() != 0) {
                progress.setVisibility(View.GONE);
                history.setVisibility(View.VISIBLE);
                bans.setVisibility(View.VISIBLE);
                sendMessage.setVisibility(View.VISIBLE);

                imageLoader.setImageWithFit(avatar, item.getLargeImage());

                nickname.setText(item.getNickname());
                lastOnline.setText(item.getLastOnline());

                if (!item.isMe()) {
                    ignore.setVisibility(View.VISIBLE);
                    addFriend.setVisibility(View.VISIBLE);
                    sendMessage.setImageDrawable(message);
                    if (item.isIgnored()) {
                        ignore.setImageDrawable(ignored);
                        ignore.setOnClickListener(v -> callback.onAction(ProfileAction.UN_IGNORE, item.getId()));
                    } else {
                        ignore.setImageDrawable(notIgnored);
                        ignore.setOnClickListener(v -> callback.onAction(ProfileAction.IGNORE, item.getId()));
                    }

                    if (item.isFriend()) {
                        addFriend.setImageDrawable(friend);
                        addFriend.setOnClickListener(v -> callback.onAction(ProfileAction.REMOVE_FRIEND, item.getId()));
                    } else {
                        addFriend.setImageDrawable(notFriend);
                        addFriend.setOnClickListener(v -> callback.onAction(ProfileAction.ADD_TO_FRIENDS, item.getId()));
                    }

                    sendMessage.setOnClickListener(v -> callback.onAction(ProfileAction.SEND_MESSAGE, item.getId()));

                } else {
                    ignore.setVisibility(View.GONE);
                    addFriend.setVisibility(View.GONE);
                    sendMessage.setImageDrawable(box);
                    sendMessage.setOnClickListener(v -> callback.onAction(ProfileAction.MAILBOX, item.getId()));
                }


                if (item.getCommmonInfo() != null) {
                    infoList.setMovementMethod(LinkMovementMethod.getInstance());
                    infoList.setText(Html.fromHtml(item.getCommmonInfo()));
                } else {
                    infoList.setText(null);
                }

                if (TextUtils.isEmpty(item.getAbout())) {
                    about.setVisibility(View.GONE);
                } else {
                    about.setText(item.getAbout());
                }

                bans.setOnClickListener(v -> callback.onAction(ProfileAction.BANS, item.getId()));
                history.setOnClickListener(v -> callback.onAction(ProfileAction.HISTORY, item.getId()));

                if (item.isBanned()) {
                    bans.setImageDrawable(banned);
                } else {
                    bans.setImageDrawable(notBanned);
                }
            } else {
                progress.setVisibility(View.VISIBLE);
            }
        }

        private void initDrawables() {
            banned = getDrawable(R.drawable.ic_gavel, R.attr.colorAccent);
            notBanned = getDrawable(R.drawable.ic_gavel, R.attr.colorText);
            friend = getDrawable(R.drawable.ic_account_remove, R.attr.colorAccent);
            notFriend = getDrawable(R.drawable.ic_account_plus, R.attr.colorText);
            ignored = getDrawable(R.drawable.ic_ignore, R.attr.colorAccent);
            notIgnored = getDrawable(R.drawable.ic_ignore, R.attr.colorText);
            message = getDrawable(R.drawable.ic_comment_text, R.attr.colorText);
            box = getDrawable(R.drawable.ic_local_post_office_big, R.attr.colorText);
        }

        private Drawable getDrawable(@DrawableRes int drawableRes, @AttrRes int colorAttr) {
            return DrawableHelper
                    .withContext(itemView.getContext())
                    .withDrawable(drawableRes)
                    .withAttributeColor(colorAttr)
                    .tint()
                    .get();
        }
    }
}
