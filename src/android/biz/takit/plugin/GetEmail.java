package biz.takit.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;

import android.accounts.AccountManager;
import android.accounts.Account;
import java.util.List;
import java.util.ArrayList;

import android.util.Log;

public class GetEmail extends CordovaPlugin {

  private static final String LOG_TAG = "getEmailPlugin";

  @Override
  public boolean execute(
    String action, 
    JSONArray args, 
    CallbackContext callbackContext
  ) throws JSONException {

    Log.e(LOG_TAG, "action: "+ action);
     
    if ("getEmail".equals(action)) {
      //echo("test", callbackContext);
      List<Account> accounts = getAccounts(null);
      JSONArray result = formatResult(accounts);
      callbackContext.success(result);
      return true;
    }
    
    return false;
  }

  private void echo(
    String msg, 
    CallbackContext callbackContext
  ) {
    if (msg == null || msg.length() == 0) {
      callbackContext.error("Empty message!");
    } else {
      Toast.makeText(
        webView.getContext(), 
        msg, 
        Toast.LENGTH_LONG
      ).show();
      callbackContext.success(msg);
    }
  }

  //--------------------------------------------------------------------------
  // LOCAL METHODS
  //--------------------------------------------------------------------------
  private List<Account> getAccounts(String type) {
    AccountManager manager = AccountManager.get(cordova.getActivity().getApplicationContext());
    Account[] accounts = manager.getAccounts();
    List<Account> ret = new ArrayList<Account>();
    for(Account account : accounts){
      if(type == null || account.type.equals(type)){
        ret.add(account);
      }
    }
    return ret;
  }

  private JSONArray formatResult(List<Account> accounts) throws JSONException {
    JSONArray jsonAccounts = new JSONArray();
    for (Account a : accounts) {
      JSONObject obj = new JSONObject();
      obj.put("type", a.type);
      obj.put("name", a.name);
      jsonAccounts.put(obj);
    }
    return jsonAccounts;
  }

}

