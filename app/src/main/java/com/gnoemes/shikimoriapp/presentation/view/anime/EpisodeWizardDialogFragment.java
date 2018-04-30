package com.gnoemes.shikimoriapp.presentation.view.anime;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.PlayerType;
import com.gnoemes.shikimoriapp.entity.anime.series.presentation.TranslationChooseSettings;
import com.gnoemes.shikimoriapp.utils.view.AttributesHelper;
import com.gnoemes.shikimoriapp.utils.view.DrawableHelper;
import com.gnoemes.shikimoriapp.utils.view.WrapContentHeightViewPager;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeWizardDialogFragment extends MvpAppCompatDialogFragment {

    private static final int WIZARD_PAGES = 3;
    private static final String ARGUMENT_TYPE = "ARGUMENT_TYPE";
    private static final String ARGUMENT_DUBBER = "ARGUMENT_DUBBER";
    private static final String ARGUMENT_PLAYER = "ARGUMENT_PLAYER";
    @BindView(R.id.view_pager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.indicator)
    InkPageIndicator indicator;
    private TranslationType selectedType;
    private TranslationChooseSettings selectedDubberSetting;
    private PlayerType selectedPlayer;
    private int backgroundColor;
    private int backgroundCheckedColor;
    private Drawable accept;
    private EpisodeWizardCallback callback;

    public static EpisodeWizardDialogFragment newInstance() {
        EpisodeWizardDialogFragment fragment = new EpisodeWizardDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_episode_wizard, null);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            selectedType = (TranslationType) savedInstanceState.getSerializable(ARGUMENT_TYPE);
            selectedDubberSetting = (TranslationChooseSettings) savedInstanceState.getSerializable(ARGUMENT_DUBBER);
            selectedPlayer = (PlayerType) savedInstanceState.getSerializable(ARGUMENT_PLAYER);
        }

        Drawable icon = DrawableHelper
                .withContext(getContext())
                .withDrawable(R.drawable.ic_settings)
                .withAttributeColor(R.attr.colorText)
                .tint()
                .get();

        accept = DrawableHelper
                .withContext(getContext())
                .withDrawable(R.drawable.ic_check)
                .withColor(R.color.light_teal)
                .tint()
                .get();

        backgroundCheckedColor = AttributesHelper
                .withContext(getContext())
                .getColor(R.attr.colorPrimary);

        backgroundColor = AttributesHelper
                .withContext(getContext())
                .getColor(R.attr.colorBackgroundWindow);


        viewPager.setAdapter(new EpisodeWizardAdapter());
        indicator.setViewPager(viewPager);


        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext())
                .icon(icon)
                .customView(view, false)
                .titleColorAttr(R.attr.colorText)
                .dividerColor(R.attr.colorDivider)
                .backgroundColorAttr(R.attr.colorBackgroundWindow)
                .buttonRippleColorAttr(R.attr.colorAccentTransparent)
                .title(R.string.series_settings)
                .positiveText(R.string.common_next)
                .positiveColorAttr(R.attr.colorAction)
                .negativeText(R.string.common_cancel)
                .negativeColorAttr(R.attr.colorAction)
                .autoDismiss(false)
                .canceledOnTouchOutside(true)
                .onPositive((dialog, which) -> {
                    int nextPage = viewPager.getCurrentItem() + 1;

                    if (nextPage < WIZARD_PAGES) {
                        viewPager.setCurrentItem(nextPage);
                    } else {
                        if (callback != null) {
                            callback.onSaveSettings(selectedType, selectedDubberSetting, selectedPlayer);
                        }
                        dismiss();
                    }
                })
                .onNegative((dialog, which) -> dismiss());

        return builder.build();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ARGUMENT_TYPE, selectedType);
        outState.putSerializable(ARGUMENT_DUBBER, selectedDubberSetting);
        outState.putSerializable(ARGUMENT_PLAYER, selectedPlayer);
    }

    public void setCallback(EpisodeWizardCallback callback) {
        this.callback = callback;
    }

    public interface EpisodeWizardCallback {
        void onSaveSettings(TranslationType type, TranslationChooseSettings chooseSettings, PlayerType playerType);
    }

    //TODO load from settings
    public class EpisodeWizardAdapter extends PagerAdapter {

        private final List<Integer> screens = Arrays.asList(R.layout.page_translation_type,
                R.layout.page_choose_dubber, R.layout.page_choose_player);

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            ViewGroup layout = (ViewGroup) inflater.inflate(screens.get(position), container, false);
            container.addView(layout);
            switch (position) {
                case 0:
                    createTranslationType(layout);
                    break;
                case 1:
                    createChooseDubber(layout);
                    break;
                case 2:
                    createChoosePlayer(layout);
                    break;
            }

            return layout;
        }

        private void createChoosePlayer(ViewGroup layout) {
            RadioGroup radioGroup = layout.findViewById(R.id.radio_group);
            RadioButton embedded = layout.findViewById(R.id.btn_embedded);
            RadioButton external = layout.findViewById(R.id.btn_external);
            RadioButton browser = layout.findViewById(R.id.btn_browser);

            embedded.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    embedded.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    embedded.setBackgroundColor(backgroundCheckedColor);
                    selectedPlayer = PlayerType.EMBEDDED;
                } else {
                    embedded.setBackgroundColor(backgroundColor);
                    embedded.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });


            external.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    external.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    external.setBackgroundColor(backgroundCheckedColor);
                    selectedPlayer = PlayerType.EXTERNAL;
                } else {
                    external.setBackgroundColor(backgroundColor);
                    external.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            browser.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    browser.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    browser.setBackgroundColor(backgroundCheckedColor);
                    selectedPlayer = PlayerType.BROWSER;
                } else {
                    browser.setBackgroundColor(backgroundColor);
                    browser.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            if (selectedPlayer == null) {
                radioGroup.check(R.id.btn_embedded);
                selectedPlayer = PlayerType.EMBEDDED;
            } else {
                switch (selectedPlayer) {
                    case EMBEDDED:
                        radioGroup.check(R.id.btn_embedded);
                        break;
                    case EXTERNAL:
                        radioGroup.check(R.id.btn_external);
                        break;
                    case BROWSER:
                        radioGroup.check(R.id.btn_browser);
                        break;
                }
            }
        }

        private void createChooseDubber(ViewGroup layout) {
            RadioGroup radioGroup = layout.findViewById(R.id.radio_group);
            RadioButton auto = layout.findViewById(R.id.btn_auto);
            RadioButton manual = layout.findViewById(R.id.btn_manual);

            auto.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    auto.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    auto.setBackgroundColor(backgroundCheckedColor);
                    selectedDubberSetting = TranslationChooseSettings.AUTO;
                } else {
                    auto.setBackgroundColor(backgroundColor);
                    auto.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });


            manual.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    manual.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    manual.setBackgroundColor(backgroundCheckedColor);
                    selectedDubberSetting = TranslationChooseSettings.MANUAL;
                } else {
                    manual.setBackgroundColor(backgroundColor);
                    manual.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            if (selectedDubberSetting == null) {
                radioGroup.check(R.id.btn_auto);
                selectedDubberSetting = TranslationChooseSettings.AUTO;
            } else {
                switch (selectedDubberSetting) {
                    case AUTO:
                        radioGroup.check(R.id.btn_auto);
                        break;
                    case MANUAL:
                        radioGroup.check(R.id.btn_manual);
                        break;
                }
            }

        }

        private void createTranslationType(ViewGroup layout) {
            RadioGroup radioGroup = layout.findViewById(R.id.radio_group);
            RadioButton voiceRu = layout.findViewById(R.id.btn_sound_ru);
            RadioButton subRu = layout.findViewById(R.id.btn_sub_ru);
            RadioButton raw = layout.findViewById(R.id.btn_raw);
            RadioButton voiceEn = layout.findViewById(R.id.btn_sound_en);
            RadioButton subEn = layout.findViewById(R.id.btn_sub_en);

            voiceRu.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    voiceRu.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    voiceRu.setBackgroundColor(backgroundCheckedColor);
                    selectedType = TranslationType.VOICE_RU;
                } else {
                    voiceRu.setBackgroundColor(backgroundColor);
                    voiceRu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            subRu.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    subRu.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    subRu.setBackgroundColor(backgroundCheckedColor);
                    selectedType = TranslationType.SUB_RU;
                } else {
                    subRu.setBackgroundColor(backgroundColor);
                    subRu.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            raw.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    raw.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    raw.setBackgroundColor(backgroundCheckedColor);
                    selectedType = TranslationType.RAW;
                } else {
                    raw.setBackgroundColor(backgroundColor);
                    raw.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            voiceEn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    voiceEn.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    voiceEn.setBackgroundColor(backgroundCheckedColor);
                    selectedType = TranslationType.VOICE_EN;
                } else {
                    voiceEn.setBackgroundColor(backgroundColor);
                    voiceEn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            subEn.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    subEn.setCompoundDrawablesWithIntrinsicBounds(null, null, accept, null);
                    subEn.setBackgroundColor(backgroundCheckedColor);
                    selectedType = TranslationType.SUB_EN;
                } else {
                    subEn.setBackgroundColor(backgroundColor);
                    subEn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            });

            if (selectedType == null) {
                radioGroup.check(R.id.btn_sound_ru);
                selectedType = TranslationType.VOICE_RU;
            } else {
                switch (selectedType) {
                    case VOICE_RU:
                        radioGroup.check(R.id.btn_sound_ru);
                        break;
                    case SUB_RU:
                        radioGroup.check(R.id.btn_sub_ru);
                        break;
                    case RAW:
                        radioGroup.check(R.id.btn_raw);
                        break;
                    case VOICE_EN:
                        radioGroup.check(R.id.btn_sound_en);
                        break;
                    case SUB_EN:
                        radioGroup.check(R.id.btn_sub_en);
                        break;
                }
            }

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return WIZARD_PAGES;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
