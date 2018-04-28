package com.sachinvarma.easypermissionsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.sachinvarma.easypermission.EasyPermissionConstants;
import com.sachinvarma.easypermission.EasyPermissionInit;
import com.sachinvarma.easypermission.EasyPermissionList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  List<String> deniedPermissions = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.btPermission).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        List<String> permission = new ArrayList<>();
        permission.add(EasyPermissionList.READ_EXTERNAL_STORAGE);
        permission.add(EasyPermissionList.ACCESS_FINE_LOCATION);

        if (permission.size() > 0) {
          new EasyPermissionInit(MainActivity.this, permission);
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case EasyPermissionConstants.INTENT_CODE:

        if (data != null) {
          boolean isGotAllPermissions =
            data.getBooleanExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION, false);

          if (data.hasExtra(EasyPermissionConstants.IS_GOT_ALL_PERMISSION)) {
            if (isGotAllPermissions) {
              Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(this, "All permission not Granted", Toast.LENGTH_SHORT).show();
            }
          }

          // if you want to know which are the denied permissions.
          if (data.hasExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST)) {
            if (data.getSerializableExtra(EasyPermissionConstants.DENIED_PERMISSION_LIST) != null) {

              deniedPermissions = new ArrayList<>();

              deniedPermissions.addAll((Collection<? extends String>) data.getSerializableExtra(
                EasyPermissionConstants.DENIED_PERMISSION_LIST));

              if (deniedPermissions.size() > 0) {
                for (int i = 0; i < deniedPermissions.size(); i++) {
                  switch (deniedPermissions.get(i)) {

                    case EasyPermissionList.READ_EXTERNAL_STORAGE:

                      Toast.makeText(this, "Storage Permission not granted", Toast.LENGTH_SHORT)
                        .show();

                      break;

                    case EasyPermissionList.ACCESS_FINE_LOCATION:

                      Toast.makeText(this, "Location Permission not granted", Toast.LENGTH_SHORT)
                        .show();

                      break;
                  }
                }
              }
            }
          }
        }
    }
  }
}
