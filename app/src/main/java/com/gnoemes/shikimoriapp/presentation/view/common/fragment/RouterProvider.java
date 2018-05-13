package com.gnoemes.shikimoriapp.presentation.view.common.fragment;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;

public interface RouterProvider {
    Router getLocalRouter();

    Navigator getLocalNavigator();
}
