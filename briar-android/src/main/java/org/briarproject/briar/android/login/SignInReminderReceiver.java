package org.briarproject.briar.android.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.briarproject.bramble.api.account.AccountManager;
import org.briarproject.briar.android.AndroidComponent;
import org.briarproject.briar.android.BriarApplication;
import org.briarproject.briar.api.android.AndroidNotificationManager;

import javax.inject.Inject;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.content.Intent.ACTION_MY_PACKAGE_REPLACED;
import static org.briarproject.briar.android.TestingConstants.FEATURE_FLAG_SIGN_IN_REMINDER;
import static org.briarproject.briar.android.settings.SettingsFragment.NOTIFY_SIGN_IN;
import static org.briarproject.briar.api.android.AndroidNotificationManager.ACTION_DISMISS_REMINDER;

public class SignInReminderReceiver extends BroadcastReceiver {

	@Inject
	AccountManager accountManager;
	@Inject
	AndroidNotificationManager notificationManager;

	@Override
	public void onReceive(Context ctx, Intent intent) {
		if (!FEATURE_FLAG_SIGN_IN_REMINDER) return;

		BriarApplication app = (BriarApplication) ctx.getApplicationContext();
		AndroidComponent applicationComponent = app.getApplicationComponent();
		applicationComponent.inject(this);

		String action = intent.getAction();
		if (action == null) return;
		if (action.equals(ACTION_BOOT_COMPLETED) ||
				action.equals(ACTION_MY_PACKAGE_REPLACED)) {
			if (accountManager.accountExists() &&
					!accountManager.hasDatabaseKey()) {
				SharedPreferences prefs = app.getDefaultSharedPreferences();
				if (prefs.getBoolean(NOTIFY_SIGN_IN, true)) {
					notificationManager.showSignInNotification();
				}
			}
		} else if (action.equals(ACTION_DISMISS_REMINDER)) {
			notificationManager.clearSignInNotification();
		}
	}

}
