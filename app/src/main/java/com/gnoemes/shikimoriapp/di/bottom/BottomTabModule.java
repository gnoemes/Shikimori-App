package com.gnoemes.shikimoriapp.di.bottom;

import android.support.v4.app.Fragment;

import com.gnoemes.shikimoriapp.di.alternative.AlternativeEpisodesModule;
import com.gnoemes.shikimoriapp.di.alternative.AlternativeTranslationsModule;
import com.gnoemes.shikimoriapp.di.anime.AnimeModule;
import com.gnoemes.shikimoriapp.di.base.modules.BaseFragmentModule;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomChildScope;
import com.gnoemes.shikimoriapp.di.base.scopes.BottomScope;
import com.gnoemes.shikimoriapp.di.calendar.CalendarModule;
import com.gnoemes.shikimoriapp.di.characters.CharacterModule;
import com.gnoemes.shikimoriapp.di.fav.FavoriteModule;
import com.gnoemes.shikimoriapp.di.forums.ForumsModule;
import com.gnoemes.shikimoriapp.di.history.HistoryModule;
import com.gnoemes.shikimoriapp.di.menu.MenuModule;
import com.gnoemes.shikimoriapp.di.person.PersonModule;
import com.gnoemes.shikimoriapp.di.profile.ProfileModule;
import com.gnoemes.shikimoriapp.di.related.RelatedModule;
import com.gnoemes.shikimoriapp.di.search.FilterModule;
import com.gnoemes.shikimoriapp.di.search.SearchModule;
import com.gnoemes.shikimoriapp.di.similar.SimilarModule;
import com.gnoemes.shikimoriapp.di.social.SocialModule;
import com.gnoemes.shikimoriapp.di.topic.TopicListModule;
import com.gnoemes.shikimoriapp.di.translations.TranslationsModule;
import com.gnoemes.shikimoriapp.di.userhistory.UserHistoryModule;
import com.gnoemes.shikimoriapp.presentation.view.alternative.episodes.AlternativeEpisodesFragment;
import com.gnoemes.shikimoriapp.presentation.view.alternative.translations.AlternativeTranslationsFragment;
import com.gnoemes.shikimoriapp.presentation.view.anime.AnimeFragment;
import com.gnoemes.shikimoriapp.presentation.view.bottom.BottomTabContainer;
import com.gnoemes.shikimoriapp.presentation.view.calendar.CalendarFragment;
import com.gnoemes.shikimoriapp.presentation.view.characters.CharacterDetailsFragment;
import com.gnoemes.shikimoriapp.presentation.view.fav.FavoriteFragment;
import com.gnoemes.shikimoriapp.presentation.view.forums.ForumsFragment;
import com.gnoemes.shikimoriapp.presentation.view.history.HistoryFragment;
import com.gnoemes.shikimoriapp.presentation.view.menu.MenuFragment;
import com.gnoemes.shikimoriapp.presentation.view.person.PersonFragment;
import com.gnoemes.shikimoriapp.presentation.view.profile.ProfileFragment;
import com.gnoemes.shikimoriapp.presentation.view.related.RelatedFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.SearchFragment;
import com.gnoemes.shikimoriapp.presentation.view.search.filter.FilterDialogFragment;
import com.gnoemes.shikimoriapp.presentation.view.similar.SimilarFragment;
import com.gnoemes.shikimoriapp.presentation.view.social.SocialFragment;
import com.gnoemes.shikimoriapp.presentation.view.topic.list.TopicListFragment;
import com.gnoemes.shikimoriapp.presentation.view.translations.TranslationsFragment;
import com.gnoemes.shikimoriapp.presentation.view.userhistory.UserHistoryFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {BaseFragmentModule.class})
public interface BottomTabModule {

    @BottomChildScope
    @ContributesAndroidInjector(modules = CalendarModule.class)
    CalendarFragment calendarFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = MenuModule.class)
    MenuFragment fragmentMenuFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = SocialModule.class)
    SocialFragment socialFragmentInejctor();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {FavoriteModule.class})
    FavoriteFragment favoriteFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {AnimeModule.class})
    AnimeFragment animeFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = SearchModule.class)
    SearchFragment searchFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {FilterModule.class})
    FilterDialogFragment filterFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {TranslationsModule.class})
    TranslationsFragment translationsFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {SimilarModule.class})
    SimilarFragment similarFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = {ProfileModule.class})
    ProfileFragment profileFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = UserHistoryModule.class)
    UserHistoryFragment userHistoryFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = RelatedModule.class)
    RelatedFragment relatedFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = CharacterModule.class)
    CharacterDetailsFragment characterDetailsFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = TopicListModule.class)
    TopicListFragment topicListFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = ForumsModule.class)
    ForumsFragment forumsFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = HistoryModule.class)
    HistoryFragment historyFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = PersonModule.class)
    PersonFragment personFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = AlternativeEpisodesModule.class)
    AlternativeEpisodesFragment alternativeEpisodesFragmentInjector();

    @BottomChildScope
    @ContributesAndroidInjector(modules = AlternativeTranslationsModule.class)
    AlternativeTranslationsFragment alternativeTranslationsFragmentInjector();

    @Binds
    @Named(BaseFragmentModule.FRAGMENT)
    @BottomScope
    Fragment bindFragment(BottomTabContainer tabContainer);
}
