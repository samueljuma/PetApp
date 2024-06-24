package com.samueljuma.petapp.views

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.samueljuma.petapp.data.PermissionAction

fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
    return (ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED)
}

fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {
    val activity = context as? Activity ?: return false
    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        permission
    )
}

@Composable
fun PermissionDialog(
    context: Context,
    permission: String,
    permissionAction: (PermissionAction) -> Unit
){
    val isPermissionGranted = checkIfPermissionGranted(context, permission)

    if(isPermissionGranted){
        permissionAction(PermissionAction.PermissionGranted)
        return
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted){
            permissionAction(PermissionAction.PermissionGranted)
        } else {
            permissionAction(PermissionAction.PermissionDenied)
        }
    }

    val showPermissionRationale = shouldShowPermissionRationale(context, permission)

    var isDialogDismissed by remember { mutableStateOf(false) }
    var isPristine by remember { mutableStateOf(true) }

    if((showPermissionRationale && !isDialogDismissed) || (!isDialogDismissed && !isPristine)){
        isPristine = false
        AlertDialog(
            onDismissRequest = {
                isDialogDismissed = true
                permissionAction(PermissionAction.PermissionDenied)
            },
            title = { Text(text = "Permission Required")},
            text = {Text(text = "This App requires the locaiton permission to be granted")},
            confirmButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionLauncher.launch(permission)
                    }
                ) {
                    Text(text = "Grant Access")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionAction(PermissionAction.PermissionDenied)
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }else{
        if(!isDialogDismissed){
            SideEffect {
                permissionLauncher.launch(permission)
            }
        }
    }

}