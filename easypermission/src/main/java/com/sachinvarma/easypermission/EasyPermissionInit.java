package com.sachinvarma.easypermission;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class EasyPermissionInit extends Intent {

  public EasyPermissionInit(@NonNull Activity context, @NonNull List<String> permissions)
  {

    Intent intent = new Intent();
    intent.setClass(context, EasyPermission.class);
    intent.putExtra(EasyPermissionConstants.PERMISSION_LIST, (Serializable) permissions);
    context.startActivityForResult(intent, EasyPermissionConstants.INTENT_CODE);

  }

}
