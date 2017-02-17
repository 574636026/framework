package com.injedu.supports.push.handle.ym;

import java.util.Arrays;
import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;
import com.injedu.supports.push.exception.PushException;

public abstract class IOSNotification extends UmengNotification {

	// Keys can be set in the aps level
	protected static final HashSet<String> APS_KEYS = new HashSet<String>(
			Arrays.asList(new String[] { "alert", "badge", "sound", "content-available" }));

	@Override
	public boolean setPredefinedKeyValue(String key, Object value) throws PushException {
		if (ROOT_KEYS.contains(key)) {
			// This key should be in the root level
			rootJson.put(key, value);
		} else if (APS_KEYS.contains(key)) {
			// This key should be in the aps level
			JSONObject apsJson = null;
			JSONObject payloadJson = null;
			if (rootJson.containsKey("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}
			if (payloadJson.containsKey("aps")) {
				apsJson = payloadJson.getJSONObject("aps");
			} else {
				apsJson = new JSONObject();
				payloadJson.put("aps", apsJson);
			}
			apsJson.put(key, value);
		} else if (POLICY_KEYS.contains(key)) {
			// This key should be in the body level
			JSONObject policyJson = null;
			if (rootJson.containsKey("policy")) {
				policyJson = rootJson.getJSONObject("policy");
			} else {
				policyJson = new JSONObject();
				rootJson.put("policy", policyJson);
			}
			policyJson.put(key, value);
		} else {
			if (key == "payload" || key == "aps" || key == "policy") {
				throw new PushException(
						"You don't need to set value for " + key + " , just set values for the sub keys in it.");
			} else {
				throw new PushException("Unknownd key: " + key);
			}
		}

		return true;
	}

	// Set customized key/value for IOS notification
	public boolean setCustomizedField(String key, String value) throws PushException {
		// rootJson.put(key, value);
		JSONObject payloadJson = null;
		if (rootJson.containsKey("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}
		payloadJson.put(key, value);
		return true;
	}

	public void setAlert(String token) throws PushException {
		setPredefinedKeyValue("alert", token);
	}

	public void setBadge(Integer badge) throws PushException {
		setPredefinedKeyValue("badge", badge);
	}

	public void setSound(String sound) throws PushException {
		setPredefinedKeyValue("sound", sound);
	}

	public void setContentAvailable(Integer contentAvailable) throws PushException {
		setPredefinedKeyValue("content-available", contentAvailable);
	}
}
