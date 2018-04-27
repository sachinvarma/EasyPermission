package com.sachinvarma.easypermission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EasyPermission extends Activity {
  List<String> permissionsNeeded = new ArrayList<>();
  List<String> askPermission = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getIntent() != null
      && getIntent().getSerializableExtra(EasyPermissionConstants.PERMISSION_LIST) != null) {
      try {
        permissionsNeeded.addAll((Collection<? extends String>) getIntent().getSerializableExtra(
          EasyPermissionConstants.PERMISSION_LIST));
      } catch (Exception e) {
        Toast.makeText(this, "Oops!, something went wrong", Toast.LENGTH_SHORT).show();
        finish();
      }
    }


    if (permissionsNeeded.size() > 0) {
      for (int i = 0; i < permissionsNeeded.size(); i++) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
          || checkSelfPermission(permissionsNeeded.get(i)) != PackageManager.PERMISSION_GRANTED) {
          askPermission.add(permissionsNeeded.get(i));
        }
      }
    } else {
      finish();
    }

    if (askPermission != null && askPermission.size() > 0) {
      ActivityCompat.requestPermissions(this,
        askPermission.toArray(new String[askPermission.size()]),
        EasyPermissionConstants.PERMISSION_REQUEST_CODE);
    } else {
      finish();
    }
  }

  @Override
  public void onRequestPermissionsResult(
    int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case EasyPermissionConstants.PERMISSION_REQUEST_CODE:
        boolean gotAllPermission = true;

        if (permissionsNeeded.size() > 0) {
          List<String> deniedPermissions = new ArrayList<>();

          for (int i = 0; i < permissionsNeeded.size(); i++) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
              || checkSelfPermission(permissionsNeeded.get(i))
              != PackageManager.PERMISSION_GRANTED) {
              gotAllPermission = false;
              deniedPermissions.add(permissionsNeeded.get(i));
            }
          }

          Intent intent = new Intent();
          intent.putExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION, gotAllPermission);
          if (deniedPermissions.size() > 0) {
            intent.putExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST,
              (Serializable) deniedPermissions);
          }
          setResult(EasyPermissionConstants.INTENT_CODE, intent);
          finish();
        }

        break;
    }
  }
}
