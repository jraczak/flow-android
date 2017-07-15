package com.justinraczak.android.flow.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by justin on 7/15/17.
 */

public class UserSession extends RealmObject {

    private static final String LOG_TAG = UserSession.class.getSimpleName();

    @Required
    public Date createdAt
}
