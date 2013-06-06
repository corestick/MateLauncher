package mobi.intuitit.android.mate.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String msg = "";
		String receiver = "";

		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				receiver = msgs[i].getOriginatingAddress();
				msg = msgs[i].getMessageBody().toString();
			}

			for (int i = 0; i < Launcher.getWorkspace().getChildCount(); i++) {
				MLayout mLayout = (MLayout) Launcher.getWorkspace().getChildAt(i);
				for (int j = 0; j < mLayout.getChildCount(); j++) {
					if (mLayout.getChildAt(j) instanceof MobjectImageView) {
						MobjectImageView mView = (MobjectImageView) mLayout
								.getChildAt(j);

						ItemInfo info = (ItemInfo) mView.getTag();

						if (info.mobjectType == 1) {
							if (info.contacts.equals(receiver)) {
								mLayout.hideMAvatarMenu(mView);
								mLayout.showSpeechBubble(mView);
								mLayout.setSpeechBubbleText(mView, msg);
								// break;
							}
						}
					}
				}
			}
		}
	}

}
